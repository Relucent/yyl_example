package yyl.example.basic.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest02 {

	public static List<String> split(String source, String pattern) {
		List<String> result = new ArrayList<String>();
		Pattern thePattern = Pattern.compile(pattern);

		Matcher matcher = thePattern.matcher(source);
		while (matcher.find()) {
			result.add(matcher.group());
		}
		return result;

	}

	// 0x4e00-0x9fff（汉字）
	// 0080-00FF拉丁文1字符 0400-04FF西里尔字母
	// 0100-017F欧洲拉丁文 0530-058F亚美尼亚文
	// 0180-01FF扩充拉丁文 0590-05FF西伯莱文
	// 0250-02AF标准拼音 0600-06FF阿拉伯文
	// 02B0-02FF修改型字母0900-097F梵文

	private static int begin = 0x4e00;// 0x4e00;//20320;//0x9fff
	private static int end = 0x9fff;// 0x9fff;//22909;//0x4e00

	private static String randomStr() {
		StringBuilder sbr = new StringBuilder();
		for (int i = 0; i < Math.random() * 10; i++) {
			sbr.append((char) ((int) (Math.random() * (end - begin)) + (begin)));
		}
		return sbr.toString();
	}

	private static String randomArrStr() {
		StringBuilder sbr = new StringBuilder();
		for (int i = 0; i < Math.random() * 5; i++) {
			if (i != 0)
				sbr.append(",");
			sbr.append(randomStr());
		}
		return sbr.toString();
	}

	public static void main(String[] args) {
		test2();
		while (true) {
			if (!test()) {
				System.out.println("error");
				break;
			}
		}
	}

	public static void test2() {
		// String source = "\"asda , \\\\\\\\\\<,?\",\"asd\"";
		String source = "\"ab,cd\",\"how are you\",\"oh my \\\",god\",\"aa,\\\"bb\"";
		System.out.println("source:	" + source);
		String reg = "(\"{1}.*?(?<!\\\\)\"{1})";
		List<String> list = RegexTest02.split(source, reg);
		System.out.print("result:	");

		for (int i = 0; i < list.size(); i++) {
			String str = list.get(i);
			if (i != 0) {
				System.out.print("=");

			}
			System.out.print(str);

		}
	}

	public static boolean test() {
		StringBuilder sbr = new StringBuilder();

		for (int i = 0; i < 10; i++) {
			if (i != 0)
				sbr.append(",");
			sbr.append("\"");

			sbr.append(randomArrStr());

			sbr.append("\"");
		}

		String source = sbr.toString();
		// System.out.println("source: " + source);

		String reg = "(\"{1}.*?(?<!\\\\)\"{1})";
		List<String> list = RegexTest02.split(source, reg);
		// System.out.print("source: ");

		sbr = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			String str = list.get(i);
			if (i != 0) {
				// System.out.print(",");
				sbr.append(",");
			}
			// System.out.print(str);
			sbr.append(str);
		}
		String result = sbr.toString();
		if (!source.equals(result)) {

			System.out.print("source:	");
			System.out.println(source);
			System.out.print("result:	");
			System.out.println(result);
			return false;
		}
		return true;

	}
}
