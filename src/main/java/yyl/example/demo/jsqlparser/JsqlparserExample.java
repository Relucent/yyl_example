package yyl.example.demo.jsqlparser;

import java.io.StringReader;

import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.update.Update;

public class JsqlparserExample {
	public static void main(String[] args) throws Exception {
		String sql = "update demo_table t1 set "//
				+ "  column1 = '1' "//
				+ " ,column2 = '1' "//
				+ " ,column3 = '2' "//
				+ " ,column4 = '3' "//
				+ " where id=0";
		CCJSqlParserManager pm = new CCJSqlParserManager();
		Statement statement = pm.parse(new StringReader(sql));
		if (statement instanceof Update) {
			Update updateStatement = (Update) statement;
			System.out.println(updateStatement.getTable());
			Expression where = updateStatement.getWhere();
			if (where instanceof BinaryExpression) {
				BinaryExpression expression = (BinaryExpression) where;
				System.out.println(expression.getLeftExpression());
				System.out.println(expression.getStringExpression());
				System.out.println(expression.getRightExpression());
			}
		}
	}
}
