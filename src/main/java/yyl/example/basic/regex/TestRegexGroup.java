package yyl.example.basic.regex;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegexGroup {
	public static void main(String[] args) {

		String[] result = new String[3];
		Pattern p = Pattern
				.compile("^([ \\S\\s]*)[\\〔|\\[]{1}([0-9]*)[|\\]\\〕]{1}[第]?([ \\S\\s]*)号$");
		Matcher m = p.matcher("机关代字〔2009〕12号");
		if (m.groupCount() == 3 && m.find()) {
			result[0] = m.group(1);
			result[1] = m.group(2);
			result[2] = m.group(3);
		}

		System.out.println(result[0]);
		System.out.println(result[1]);
		System.out.println(result[2]);
	}
}
