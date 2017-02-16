package yyl.example.exercise.ldap;

import javax.naming.NamingException;

/**
 * LDAP字符编码工具类
 * @version  2012-10-13 1.0
 */
class LdapEncoder {
	private static final String[] nameEscapeTable = new String[96];
	static {
		for (char c = '\000'; c < ' '; c = (char) (c + '\001')) {
			nameEscapeTable[c] = ("\\" + toTwoCharHex(c));
		}
		nameEscapeTable[35] = "\\#";
		nameEscapeTable[44] = "\\,";
		nameEscapeTable[59] = "\\;";
		nameEscapeTable[61] = "\\=";
		nameEscapeTable[43] = "\\+";
		nameEscapeTable[60] = "\\<";
		nameEscapeTable[62] = "\\>";
		nameEscapeTable[34] = "\\\"";
		nameEscapeTable[92] = "\\\\";
	}
	private static final String[] filterEscapeTable = new String[93];
	static {
		for (char c = '\000'; c < filterEscapeTable.length; c = (char) (c + '\001')) {
			filterEscapeTable[c] = String.valueOf(c);
		}
		filterEscapeTable[42] = "\\2a";
		filterEscapeTable[40] = "\\28";
		filterEscapeTable[41] = "\\29";
		filterEscapeTable[92] = "\\5c";
		filterEscapeTable[0] = "\\00";
	}
	/**
	 * 编码过滤条件
	 * @param value 过滤条件字符串
	 * @return 编码后的过滤条件字符串
	 */
	public static String filterEncode(String value) {
		if (value == null) {
			return null;
		}
		StringBuffer encodedValue = new StringBuffer(value.length() * 2);
		int length = value.length();
		for (int i = 0; i < length; i++) {
			char c = value.charAt(i);
			if (c < filterEscapeTable.length) {
				encodedValue.append(filterEscapeTable[c]);
			} else {
				encodedValue.append(c);
			}
		}
		return encodedValue.toString();
	}
	/**
	 * 编码属性名称
	 * @param value 属性名称
	 * @return 编码后的名称字符串
	 */
	public static String nameEncode(String value) {
		if (value == null) {
			return null;
		}
		StringBuffer encodedValue = new StringBuffer(value.length() * 2);
		int length = value.length();
		int last = length - 1;
		for (int i = 0; i < length; i++) {
			char c = value.charAt(i);
			if ((c == ' ') && ((i == 0) || (i == last))) {
				encodedValue.append("\\ ");
			} else {
				if (c < nameEscapeTable.length) {
					String esc = nameEscapeTable[c];
					if (esc != null) {
						encodedValue.append(esc);
						continue;
					}
				}
				encodedValue.append(c);
			}
		}
		return encodedValue.toString();
	}

	/**
	 * 解码属性名称
	 * @param value 属性名称
	 * @return 解码后的名称字符串
	 */
	public static String nameDecode(String value) throws NamingException {
		if (value == null) {
			return null;
		}
		StringBuffer decoded = new StringBuffer(value.length());
		int i = 0;
		while (i < value.length()) {
			char currentChar = value.charAt(i);
			if (currentChar == '\\') {
				if (value.length() <= i + 1) {
					throw new NamingException("BadLdapGrammar:Unexpected end of value unterminated '\\'");
				}
				char nextChar = value.charAt(i + 1);
				if ((nextChar == ',') || (nextChar == '=') || (nextChar == '+') || (nextChar == '<') || (nextChar == '>') || (nextChar == '#') || (nextChar == ';') || (nextChar == '\\') || (nextChar == '"') || (nextChar == ' ')) {
					decoded.append(nextChar);
					i += 2;
				} else {
					if (value.length() <= i + 2) {
						throw new NamingException("BadLdapGrammar:Unexpected end of value expected special or hex, found '" + nextChar + "'");
					}

					String hexString = "" + nextChar + value.charAt(i + 2);

					decoded.append((char) Integer.parseInt(hexString, 16));

					i += 3;
				}
			} else {
				decoded.append(currentChar);
				i++;
			}
		}
		return decoded.toString();
	}
	/**
	 * 将字符转换为两位16进制字符表达式
	 * @param c 字符
	 * @return 16进制字符表达式
	 */
	private static String toTwoCharHex(char c) {
		String raw = Integer.toHexString(c).toUpperCase();
		if (raw.length() > 1) {
			return raw;
		}
		return "0" + raw;
	}
}
