package yyl.example.basic.codec;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Properties;

public class Native2Ascii {
	static String java_bin_path = "C:\\jdk1.5.0\\bin";

	public Native2Ascii() {

	}

	public Properties getProperties(String filename) throws IOException {
		Properties p = new Properties();
		FileInputStream input = null;
		try {
			input = new FileInputStream(filename);
			p.load(input);
		} finally {
			try {
				input.close();
			} catch (Exception e) {
			}
		}
		return p;

	}

	public String getUnicodeString(String value) {

		StringBuffer tempSb = new StringBuffer();
		try {
			Process pro = Runtime.getRuntime().exec("native2ascii");
			OutputStream out = pro.getOutputStream();
			out.write(value.getBytes());
			out.flush();
			out.close();
			InputStreamReader child_in = new InputStreamReader(pro.getInputStream());

			int c;
			while ((c = child_in.read()) != -1) {
				tempSb.append((char) c);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return tempSb.toString();
	}

	/** */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sourceFile = "Language_zh_CN.properties";
		String targetFile = "target.properties";
		if (args.length != 3) {
			System.out.println("Usage: java Native2Ascii <source properties filename> <target filename>"
					+ " Author:Smilingleo" + " Blog:blog.csdn.net/smilingleo");
			// System.exit(0);
		} else {
			java_bin_path = args[0];
			sourceFile = args[1];
			targetFile = args[2];
		}
		Native2Ascii parser = new Native2Ascii();
		StringBuffer sb = new StringBuffer();
		try {
			// Convert the source file into unicode first.
			Properties p = parser.getProperties(sourceFile);
			Iterator<?> iterator = p.keySet().iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				String value = p.get(key).toString();
				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
				value = parser.getUnicodeString(value);
				// System.out.println(key + ":" + value);
				p.setProperty(key.toString(), value);
				sb.append(key.toString() + "=" + value);
			}
			// write the target file.
			FileWriter out = new FileWriter(targetFile);
			out.write(sb.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}