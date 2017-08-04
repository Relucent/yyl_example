package yyl.example.basic.algorithm.exercises;

/**
 * 题目:<br>
 * 将一个字符串逆序，不要使用反转函数<br>
 */
public class StringReverse {
	public static void main(String[] args) {
		String value = "abcefg";
		char[] chars = value.toCharArray();
		for (int i = 0, j = chars.length - 1; i < j; i++, j--) {
			char c = chars[i];
			chars[i] = chars[j];
			chars[j] = c;
		}
		System.out.println(new String(chars));
	}
}
