package yyl.example.demo.jsqlparser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.ValuesList;
import net.sf.jsqlparser.statement.select.WithItem;

/**
 * SQL解析类，提供更智能的COUNT查询SQL
 */
public class CountSqlParser {

    public static final String KEEP_ORDERBY = "/*keep orderby*/";
    public static final String COUNT_STAR = "*";

    private static final Alias TABLE_ALIAS;
    static {
        TABLE_ALIAS = new Alias("TABLE_COUNT_ALIAS");
        TABLE_ALIAS.setUseAs(false);
    }

    // <editor-fold desc="聚合函数">
    private final Set<String> skipFunctions = Collections.synchronizedSet(new HashSet<String>());
    private final Set<String> falseFunctions = Collections.synchronizedSet(new HashSet<String>());

    /**
     * 聚合函数，以下列函数开头的都认为是聚合函数
     */
    private static final Set<String> AGGREGATE_FUNCTIONS = new HashSet<>();
    static {
        AGGREGATE_FUNCTIONS.add("APPROX_COUNT_DISTINCT");
        AGGREGATE_FUNCTIONS.add("ARRAY_AGG");
        AGGREGATE_FUNCTIONS.add("AVG");
        AGGREGATE_FUNCTIONS.add("BIT_");
        AGGREGATE_FUNCTIONS.add("BOOL_");
        AGGREGATE_FUNCTIONS.add("CHECKSUM_AGG");
        AGGREGATE_FUNCTIONS.add("COLLECT");
        AGGREGATE_FUNCTIONS.add("CORR");
        AGGREGATE_FUNCTIONS.add("COUNT");
        AGGREGATE_FUNCTIONS.add("COVAR");
        AGGREGATE_FUNCTIONS.add("CUME_DIST");
        AGGREGATE_FUNCTIONS.add("DENSE_RANK");
        AGGREGATE_FUNCTIONS.add("EVERY");
        AGGREGATE_FUNCTIONS.add("FIRST");
        AGGREGATE_FUNCTIONS.add("GROUP");
        AGGREGATE_FUNCTIONS.add("JSON_");
        AGGREGATE_FUNCTIONS.add("LAST");
        AGGREGATE_FUNCTIONS.add("LISTAGG");
        AGGREGATE_FUNCTIONS.add("MAX");
        AGGREGATE_FUNCTIONS.add("MEDIAN");
        AGGREGATE_FUNCTIONS.add("MIN");
        AGGREGATE_FUNCTIONS.add("PERCENT_");
        AGGREGATE_FUNCTIONS.add("RANK");
        AGGREGATE_FUNCTIONS.add("REGR_");
        AGGREGATE_FUNCTIONS.add("SELECTIVITY");
        AGGREGATE_FUNCTIONS.add("STATS_");
        AGGREGATE_FUNCTIONS.add("STD");
        AGGREGATE_FUNCTIONS.add("STRING_AGG");
        AGGREGATE_FUNCTIONS.add("SUM");
        AGGREGATE_FUNCTIONS.add("SYS_OP_ZONE_ID");
        AGGREGATE_FUNCTIONS.add("SYS_XMLAGG");
        AGGREGATE_FUNCTIONS.add("VAR");
        AGGREGATE_FUNCTIONS.add("XMLAGG");
    }

    /**
     * 添加到聚合函数，可以是逗号隔开的多个函数前缀
     * @param functions
     */
    public static void addAggregateFunctions(String functions) {
        if (StringUtils.isNotEmpty(functions)) {
            String[] funs = functions.split(",");
            for (int i = 0; i < funs.length; i++) {
                AGGREGATE_FUNCTIONS.add(funs[i].toUpperCase());
            }
        }
    }

    /**
     * 获取 Count SQL
     * @param sql 原始SQL
     * @return Count SQL
     */
    public String getSmartCountSql(String sql) {
        // 解析SQL
        Statement stmt = null;
        // 特殊sql不需要去掉order by时，使用注释前缀
        if (sql.indexOf(KEEP_ORDERBY) >= 0) {
            return getSimpleCountSql(sql);
        }
        try {
            stmt = CCJSqlParserUtil.parse(sql);
        } catch (Throwable e) {
            // 无法解析的用一般方法返回count语句
            return getSimpleCountSql(sql);
        }
        Select select = (Select) stmt;
        SelectBody selectBody = select.getSelectBody();
        try {
            // 处理body-去order by
            removeOrderBy(selectBody);
        } catch (Exception e) {
            // 当 sql 包含 group by 时，不去除 order by
            return getSimpleCountSql(sql);
        }
        // 处理with-去order by
        removeOrderBy(select.getWithItemsList());
        // 处理为count查询
        sqlToCount(select);
        return select.toString();
    }

    /**
     * 获取 COUNT SQL
     * @param sql 原始SQL
     * @return COUNT SQL
     */
    public String getSimpleCountSql(String sql) {
        StringBuilder builder = new StringBuilder(sql.length() + 50);
        builder.append("SELECT COUNT(*) FROM (");
        builder.append(sql);
        builder.append(") TABLE_COUNT_ALIAS");
        return builder.toString();
    }

    /**
     * 将 SQL 转换为 COUNT 查询
     */
    public void sqlToCount(Select select) {
        SelectBody selectBody = select.getSelectBody();

        List<SelectItem> countItem = new ArrayList<SelectItem>();
        countItem.add(new SelectExpressionItem(new Column("count(*)")));
        if (selectBody instanceof PlainSelect && isSimpleCount((PlainSelect) selectBody)) {
            ((PlainSelect) selectBody).setSelectItems(countItem);
        } else {
            SubSelect subSelect = new SubSelect();
            subSelect.setSelectBody(selectBody);
            subSelect.setAlias(TABLE_ALIAS);
            PlainSelect plainSelect = new PlainSelect();
            plainSelect.setFromItem(subSelect);
            plainSelect.setSelectItems(countItem);
            select.setSelectBody(plainSelect);
        }
    }

    /**
     * 去除 ORDER BY
     * @param selectBody 查询主体
     */
    public void removeOrderBy(SelectBody selectBody) {
        if (selectBody instanceof PlainSelect) {
            removeOrderByFromPlainSelect((PlainSelect) selectBody);
        } else if (selectBody instanceof WithItem) {
            WithItem withItem = (WithItem) selectBody;
            if (withItem.getSelectBody() != null) {
                removeOrderBy(withItem.getSelectBody());
            }
        } else {
            SetOperationList operationList = (SetOperationList) selectBody;
            if (operationList.getSelects() != null && operationList.getSelects().size() > 0) {
                List<SelectBody> plainSelects = operationList.getSelects();
                for (SelectBody plainSelect : plainSelects) {
                    removeOrderBy(plainSelect);
                }
            }
            if (!hasParameters(operationList.getOrderByElements())) {
                operationList.setOrderByElements(null);
            }
        }
    }

    /**
     * 去除 ORDER BY
     * @param plainSelect 简单的查询语句
     */
    public void removeOrderByFromPlainSelect(PlainSelect plainSelect) {
        if (!hasParameters(plainSelect.getOrderByElements())) {
            plainSelect.setOrderByElements(null);
        }
        if (plainSelect.getFromItem() != null) {
            processFromItem(plainSelect.getFromItem());
        }
        if (plainSelect.getJoins() != null && plainSelect.getJoins().size() > 0) {
            List<Join> joins = plainSelect.getJoins();
            for (Join join : joins) {
                if (join.getRightItem() != null) {
                    processFromItem(join.getRightItem());
                }
            }
        }
    }

    /**
     * 去除 ORDER BY
     * @param withItemsList WithItem
     */
    public void removeOrderBy(List<WithItem> withItemsList) {
        if (withItemsList != null && withItemsList.size() > 0) {
            for (WithItem item : withItemsList) {
                removeOrderBy(item.getSelectBody());
            }
        }
    }

    /**
     * 处理子查询
     * @param fromItem
     */
    public void processFromItem(FromItem fromItem) {
        if (fromItem instanceof SubJoin) {
            SubJoin subJoin = (SubJoin) fromItem;
            if (subJoin.getJoinList() != null && subJoin.getJoinList().size() > 0) {
                for (Join join : subJoin.getJoinList()) {
                    if (join.getRightItem() != null) {
                        processFromItem(join.getRightItem());
                    }
                }
            }
            if (subJoin.getLeft() != null) {
                processFromItem(subJoin.getLeft());
            }
        } else if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            if (subSelect.getSelectBody() != null) {
                removeOrderBy(subSelect.getSelectBody());
            }
        } else if (fromItem instanceof ValuesList) {

        } else if (fromItem instanceof LateralSubSelect) {
            LateralSubSelect lateralSubSelect = (LateralSubSelect) fromItem;
            if (lateralSubSelect.getSubSelect() != null) {
                SubSelect subSelect = lateralSubSelect.getSubSelect();
                if (subSelect.getSelectBody() != null) {
                    removeOrderBy(subSelect.getSelectBody());
                }
            }
        }
    }

    /**
     * 是否可以简化 COUNT 查询
     * @param select 查询语句
     * @return 如果可以简化返回true，否则返回false
     */
    public boolean isSimpleCount(PlainSelect select) {
        // 包含group by的时候不可以
        if (select.getGroupBy() != null) {
            return false;
        }
        // 包含distinct的时候不可以
        if (select.getDistinct() != null) {
            return false;
        }
        for (SelectItem item : select.getSelectItems()) {
            // select列中包含参数的时候不可以，否则会引起参数个数错误
            if (item.toString().contains("?")) {
                return false;
            }
            // 如果查询列中包含函数，也不可以，函数可能会聚合列
            if (item instanceof SelectExpressionItem) {
                Expression expression = ((SelectExpressionItem) item).getExpression();
                if (expression instanceof Function) {
                    String name = ((Function) expression).getName();
                    if (name != null) {
                        String NAME = name.toUpperCase();
                        if (skipFunctions.contains(NAME)) {
                            // go on
                        } else if (falseFunctions.contains(NAME)) {
                            return false;
                        } else {
                            for (String aggregateFunction : AGGREGATE_FUNCTIONS) {
                                if (NAME.startsWith(aggregateFunction)) {
                                    falseFunctions.add(NAME);
                                    return false;
                                }
                            }
                            skipFunctions.add(NAME);
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * 判断 OrderBy 是否包含参数，有参数的不能去
     * @param orderByElements ORDER BY 元素
     * @return OrderBy 包含参数返回true，否则返回false
     */
    public boolean hasParameters(List<OrderByElement> orderByElements) {
        if (orderByElements == null) {
            return false;
        }
        for (OrderByElement orderByElement : orderByElements) {
            if (orderByElement.toString().contains("?")) {
                return true;
            }
        }
        return false;
    }
}
