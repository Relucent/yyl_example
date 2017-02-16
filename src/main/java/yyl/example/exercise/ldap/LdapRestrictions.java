package yyl.example.exercise.ldap;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * LDAP过滤条件工具类
 * @author YYL
 * @version 2012-10-13 1.0
 */
public class LdapRestrictions {

	/**
	 * 等于 =
	 * @param attribute LDAP属性
	 * @param value 匹配的值
	 * @return 条件对象
	 */
	public static LFilterEq eq(String attribute, String value) {
		return new LFilterEq(attribute, value);
	}

	/**
	 * 不等于 != <>
	 * @param attribute LDAP属性
	 * @param value 匹配的值
	 * @return 条件对象
	 */
	public static LFilterNe ne(String attribute, String value) {
		return new LFilterNe(attribute, value);
	}

	/**
	 * 大于 >
	 * @param attribute LDAP属性
	 * @param value 匹配的值
	 * @return 条件对象
	 */
	public static LFilterGt gt(String attribute, String value) {
		return new LFilterGt(attribute, value);
	}

	/**
	 * 大于等于 >=
	 * @param attribute LDAP属性
	 * @param value 匹配的值
	 * @return 条件对象
	 */
	public static LFilterGe ge(String attribute, String value) {
		return new LFilterGe(attribute, value);
	}

	/**
	 * 小于 <
	 * @param attribute LDAP属性
	 * @param value 匹配的值
	 * @return 条件对象
	 */
	public static LFilterLt lt(String attribute, String value) {
		return new LFilterLt(attribute, value);
	}

	/**
	 * 小于等于 <=
	 * @param attribute LDAP属性
	 * @param value 匹配的值
	 * @return 条件对象
	 */
	public static LFilterLe le(String attribute, String value) {
		return new LFilterLe(attribute, value);
	}

	/**
	 * 头尾相似 LIKE
	 * @param attribute LDAP属性
	 * @param value 匹配的值
	 * @return 条件对象
	 */
	public static LFilterLike like(String attribute, String value) {
		return new LFilterLike(attribute, value);
	}

	/**
	 * 头相似 LIKE
	 * @param attribute LDAP属性
	 * @param value 匹配的值
	 * @return 条件对象
	 */
	public static LFilterLikeStart likeStart(String attribute, String value) {
		return new LFilterLikeStart(attribute, value);
	}

	/**
	 * 尾相似 LIKE
	 * @param attribute LDAP属性
	 * @param value 匹配的值
	 * @return 条件对象
	 */
	public static LFilterLikeEnd likeEnd(String attribute, String value) {
		return new LFilterLikeEnd(attribute, value);
	}

	/**
	 * 精确匹配相似 LIKE 允许匹配值使用*模糊匹配
	 * @param attribute LDAP属性
	 * @param value 匹配的值
	 * @return 条件对象
	 */
	public static LFilterLikeExact likeExact(String attribute, String value) {
		return new LFilterLikeExact(attribute, value);
	}

	/**
	 * 自定义条件
	 * @param expression 表达式
	 * @return 条件对象
	 */
	public static LFilterExpression expression(String expression) {
		return new LFilterExpression(expression);
	}

	/**
	 * 非 !
	 * @param filter 过滤条件
	 * @return 条件对象
	 */
	public static LFilterNot not(LFilter filter) {
		return new LFilterNot(filter);
	}

	/**
	 * 与 AND & [criteria]
	 * @return AND条件对象
	 */
	public static LFilterAnd and() {
		return new LFilterAnd();
	}

	/**
	 * 或 OR |
	 * @return OR条件对象
	 */
	public static LFilterOr or() {
		return new LFilterOr();
	}

	/** 工具类私有构造 */
	private LdapRestrictions() {
	}

	/** 查询条件类 */
	public static interface LFilter {

		String encode();

		StringBuffer encode(StringBuffer buffer);
	}

	protected static abstract class AbstractBaseFilter implements LFilter {

		public abstract StringBuffer encode(StringBuffer buffer);

		public String encode() {
			StringBuffer buf = new StringBuffer(256);
			buf = encode(buf);
			return buf.toString();
		}

		public String toString() {
			return encode();
		}
	}

	protected static abstract class AbstractBinaryLogicalFilter extends AbstractBaseFilter {

		protected List<LFilter> queryList = new LinkedList<LFilter>();

		public StringBuffer encode(StringBuffer buff) {
			if (this.queryList.size() <= 0) {
				return buff;
			}
			if (this.queryList.size() == 1) {
				LFilter query = (LFilter) this.queryList.get(0);
				return query.encode(buff);
			}
			buff.append("(" + getLogicalOperator());
			for (Iterator<LFilter> i = this.queryList.iterator(); i.hasNext();) {
				LFilter query = (LFilter) i.next();
				buff = query.encode(buff);
			}
			buff.append(")");
			return buff;
		}

		protected abstract String getLogicalOperator();

		protected final AbstractBinaryLogicalFilter append(LFilter query) {
			this.queryList.add(query);
			return this;
		}
	}

	static abstract class AbstractCompareFilter extends AbstractBaseFilter {

		private final String attribute;
		private final String value;
		private final String encodedValue;

		public AbstractCompareFilter(String attribute, String value) {
			this.attribute = attribute;
			this.value = value;
			this.encodedValue = encodeValue(value);
		}

		String getEncodedValue() {
			return this.encodedValue;
		}

		protected String encodeValue(String value) {
			return LdapEncoder.filterEncode(value);
		}

		public AbstractCompareFilter(String attribute, int value) {
			this.attribute = attribute;
			this.value = String.valueOf(value);
			this.encodedValue = LdapEncoder.filterEncode(this.value);
		}

		public StringBuffer encode(StringBuffer buff) {
			buff.append('(');
			buff.append(this.attribute).append(getCompareString()).append(this.encodedValue);
			buff.append(')');

			return buff;
		}

		protected abstract String getCompareString();
	}

	public static class LFilterAnd extends AbstractBinaryLogicalFilter {
		private static final String AMPERSAND = "&";

		protected String getLogicalOperator() {
			return AMPERSAND;
		}

		public LFilterAnd and(LFilter query) {
			append(query);
			return this;
		}
	}

	public static class LFilterOr extends AbstractBinaryLogicalFilter {
		private static final String PIPE_SIGN = "|";

		public LFilterOr or(LFilter query) {
			append(query);
			return this;
		}

		protected String getLogicalOperator() {
			return PIPE_SIGN;
		}
	}

	public static class LFilterEq extends AbstractCompareFilter {
		private static final String EQUALS_SIGN = "=";

		public LFilterEq(String attribute, String value) {
			super(attribute, value);
		}

		public LFilterEq(String attribute, int value) {
			super(attribute, value);
		}

		protected String getCompareString() {
			return EQUALS_SIGN;
		}
	}

	public static class LFilterEnd extends LFilterEq {
		public LFilterEnd(String attribute, String value) {
			super(attribute, value);
		}

		protected String encodeValue(String value) {
			if (value == null) {
				return "";
			}

			String[] substrings = value.split("\\*", -2);

			if (substrings.length == 1) {
				return LdapEncoder.filterEncode(substrings[0]);
			}

			StringBuffer buff = new StringBuffer();
			for (int i = 0; i < substrings.length; i++) {
				buff.append(LdapEncoder.filterEncode(substrings[i]));
				if (i < substrings.length - 1) {
					buff.append("*");
				} else if (!substrings[i].equals(""))
					;
			}

			return buff.toString();
		}
	}

	public static class LFilterExpression extends AbstractBaseFilter {
		private final String expression;

		public LFilterExpression(String expression) {
			this.expression = expression;
		}

		@Override
		public StringBuffer encode(StringBuffer buffer) {
			buffer.append(expression);
			return buffer;
		}

	}

	public static class LFilterGe extends AbstractCompareFilter {
		private static final String GREATER_THAN_OR_EQUALS = ">=";

		public LFilterGe(String attribute, String value) {
			super(attribute, value);
		}

		public LFilterGe(String attribute, int value) {
			super(attribute, value);
		}

		protected String getCompareString() {
			return GREATER_THAN_OR_EQUALS;
		}
	}

	public static class LFilterLike extends LFilterEq {
		public LFilterLike(String attribute, String value) {
			super(attribute, value);
		}

		protected String encodeValue(String value) {
			if (value == null) {
				return "";
			}
			return "*" + LdapEncoder.filterEncode(value) + "*";
		}
	}

	public static class LFilterNot extends AbstractBaseFilter {
		private final LFilter filter;

		public LFilterNot(LFilter filter) {
			this.filter = filter;
		}

		public StringBuffer encode(StringBuffer buff) {
			buff.append("(!");
			this.filter.encode(buff);
			buff.append(')');
			return buff;
		}
	}

	/**
	 * 小于
	 */
	public static class LFilterLt extends AbstractBinaryLogicalFilter {

		private static final String AND = "&";

		public LFilterLt(String attribute, String value) {
			append(new LFilterNot(new LFilterEq(attribute, value)));//(!=)
			append(new LFilterLe(attribute, value));//(<=)

		}

		public LFilterLt(String attribute, int value) {
			append(new LFilterNot(new LFilterEq(attribute, value)));//(!=)
			append(new LFilterLe(attribute, value));//(<=)
		}

		@Override
		protected String getLogicalOperator() {
			return AND;
		}
	}

	/**
	 * 大于 >
	 */
	public static class LFilterGt extends AbstractBinaryLogicalFilter {

		private static final String AND = "&";

		public LFilterGt(String attribute, String value) {
			append(new LFilterNot(new LFilterEq(attribute, value)));//(!=)
			append(new LFilterGe(attribute, value));//(>=)

		}

		public LFilterGt(String attribute, int value) {
			append(new LFilterNot(new LFilterEq(attribute, value)));//(!=)
			append(new LFilterGe(attribute, value));//(>=)
		}

		@Override
		protected String getLogicalOperator() {
			return AND;
		}
	}

	/**
	 * 小于
	 */
	public static class LFilterLe extends AbstractCompareFilter {
		private static final String LESS_THAN_OR_EQUALS = "<=";

		public LFilterLe(String attribute, String value) {
			super(attribute, value);
		}

		public LFilterLe(String attribute, int value) {
			super(attribute, value);
		}

		protected String getCompareString() {
			return LESS_THAN_OR_EQUALS;
		}
	}

	public static class LFilterLikeStart extends LFilterEq {
		public LFilterLikeStart(String attribute, String value) {
			super(attribute, value);
		}

		protected String encodeValue(String value) {
			if (value == null) {
				return "";
			}
			return LdapEncoder.filterEncode(value) + "*";
		}
	}

	public static class LFilterLikeEnd extends LFilterEq {
		public LFilterLikeEnd(String attribute, String value) {
			super(attribute, value);
		}

		protected String encodeValue(String value) {
			if (value == null) {
				return "";
			}
			return "*" + LdapEncoder.filterEncode(value);
		}
	}

	public static class LFilterLikeExact extends LFilterEq {
		public LFilterLikeExact(String attribute, String value) {
			super(attribute, value);
		}

		protected String encodeValue(String value) {
			if (value == null) {
				return "";
			}
			String[] substrings = value.split("\\*", -2);
			if (substrings.length == 1) {
				return LdapEncoder.filterEncode(substrings[0]);
			}
			StringBuffer buff = new StringBuffer();
			for (int i = 0; i < substrings.length; i++) {
				buff.append(LdapEncoder.filterEncode(substrings[i]));
				if (i < substrings.length - 1) {
					buff.append("*");
				} else if (!substrings[i].equals(""))
					;
			}
			return buff.toString();
		}
	}

	public static class LFilterNe extends AbstractBaseFilter {
		private final LFilterNot filter;

		public LFilterNe(String attribute, String value) {
			filter = new LFilterNot(new LFilterEq(attribute, value));
		}

		public LFilterNe(String attribute, int value) {
			filter = new LFilterNot(new LFilterEq(attribute, value));
		}

		@Override
		public StringBuffer encode(StringBuffer buffer) {
			return filter.encode(buffer);
		}
	}
}
