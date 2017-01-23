package yyl.example.basic.environment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获得电脑网卡的MAC地址
 * @author YYL
 */
public class GetMacAddresJdk4 {

	public static final Collection<String> getMacAddress() {
		String os = System.getProperty("os.name").toLowerCase();
		String output = null;
		String cmd = "ipconfig /all";
		try {
			if (os.indexOf("windows") < 0) {
				cmd = "ifconfig";
			}
			output = exec(cmd);
		} catch (Exception ex) {
			if (os.indexOf("windows") < 0) {
				cmd = "/sbin/ifconfig";
			}
			try {
				output = exec(cmd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		output = output.toUpperCase();
		Pattern p = Pattern.compile("([0-9A-F]{1,2}[\\:\\-]){5}[0-9A-F]{2}", Pattern.DOTALL);
		Matcher m = p.matcher(output);
		int lastIndex = 0;
		Set<String> mSet = new LinkedHashSet<String>();
		while (m.find(lastIndex)) {
			if (m.end() < output.length() - 1) {
				String next = output.substring(m.end(), m.end() + 1);
				if ("-".equals(next) || ":".equals(next)) {
					lastIndex = m.end();
					continue;
				}
			}
			mSet.add(m.group(0));
			lastIndex = m.end();
		}
		return mSet;
	}

	private static String exec(String cmd) throws IOException {
		InputStream is = null;
		try {
			Process proc = Runtime.getRuntime().exec(cmd);
			is = proc.getInputStream();
			return readText(is);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				is.close();
			} catch (Exception e) {
			}
		}
	}

	private static String readText(InputStream is) throws IOException {
		StringBuilder buffer = new StringBuilder();
		for (Object text : readLines(is, getFileEncode())) {
			buffer.append(text.toString());
			buffer.append("\n");
		}
		return buffer.toString();
	}

	private static List<String> readLines(InputStream input, String encoding) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, encoding));
		List<String> list = new ArrayList<String>();
		for (String line = reader.readLine(); line != null; line = reader.readLine())
			list.add(line);

		return list;
	}
	private static String getFileEncode() {
		return System.getProperty("file.encoding");
	}
	public static void main(String[] args) {
		System.out.println(getMacAddress().toString());
	}

}
