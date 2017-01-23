package yyl.example.basic.algorithm.test;

/**
 * EXCEL的列头编号的转换
 */
public class AlphaIntegerTest {

	/**
	 * 将数值转换成A-Z的字母表示
	 * 
	 * @param value 数值
	 * @return 字母表示
	 */
	public static String toAlphaString(int value) {
		if (value <= 0) {
			throw new IllegalArgumentException();
		}
		StringBuilder result = new StringBuilder();
		do {
			value--;
			int n = value % 26;
			result.append(((char) (n + (int) 'a')));
			value = (int) ((value - n) / 26);
		} while (value > 0);
		return result.reverse().toString();
	}

	/**
	 * 将编号转换成数值
	 * 
	 * @param value 编号
	 * @return 编号代表的数值
	 */
	public static int parseIntByCode(String value) {
		if (value == null || !value.matches("[a-zA-Z]+")) {
			throw new IllegalArgumentException();
		}
		char[] chars = value.toLowerCase().toCharArray();
		int index = 0;
		for (int i = 0; i < chars.length; i++) {
			index += ((int) chars[i] - (int) 'a' + 1) * (int) Math.pow(26, chars.length - i - 1);
		}
		return index;
	}

	// 测试
	public static void main(String[] args) {
		System.out.println(toAlphaString(1));
		System.out.println(toAlphaString(1 + 26));
		System.out.println(toAlphaString(1 + 26 + 26 * 26));
		System.out.println(toAlphaString(1 + 26 + 26 * 26 + 26 * 26 * 26));
		System.out.println(parseIntByCode("a"));
		System.out.println(parseIntByCode("aa"));
		System.out.println(parseIntByCode("aaa"));
		System.out.println(parseIntByCode("aaaa"));
	}

}
