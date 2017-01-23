package yyl.example.basic.regex;

/**
 * 有一组用-分割的数字，格式为 (XXXXXXX-XXXXXXX-XXXXXXX)<br>
 * 要求正则匹配： 每一组数字是0-9组成，长度最长为10，组成每一组数字的数字不重复.
 */
public class NonCapturingTest {
	public static void main(String[] args) {
		String regex = "(?!.*(\\d)\\d*\\1\\d*)[\\d]{1,10}\\-[\\d]{1,10}\\-[\\d]{1,10}";
		System.out.println("012-012-012".matches(regex));
		System.out.println("010-012-012".matches(regex));
		System.out.println("012-012-010".matches(regex));
	}
}
