package yyl.example.basic.reflect;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.security.ProtectionDomain;

public class CodeSourceTest {

	//获得当前运行的程序路径 
	public static void main(String[] args) {

		ProtectionDomain protectionDomain = CodeSourceTest.class.getProtectionDomain();
		CodeSource codeSource = protectionDomain.getCodeSource();
		URL url = codeSource.getLocation();
		String path = url.getPath();
		File file = new File(decodeURL(path)).getAbsoluteFile();

		System.out.println(protectionDomain);
		System.out.println(codeSource);
		System.out.println(url);
		System.out.println(file);
	}

	public static String decodeURL(String value) {
		try {
			return URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
