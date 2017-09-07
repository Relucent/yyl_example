package yyl.example.basic.reflect;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.security.ProtectionDomain;

/**
 * 从一个类找到它的加载的位置
 */
public class CodeSourceTest {

	//获得当前运行的程序路径 
	public static void main(String[] args) throws Exception {

		ProtectionDomain protectionDomain = CodeSourceTest.class.getProtectionDomain();
		CodeSource codeSource = protectionDomain.getCodeSource();
		URL url = codeSource.getLocation();

		System.out.println(protectionDomain);
		System.out.println(codeSource);
		System.out.println(url);
		System.out.println(getJarPath());
	}

	public static String getJarPath() throws Exception {
		ProtectionDomain protectionDomain = CodeSourceTest.class.getProtectionDomain();
		CodeSource codeSource = protectionDomain.getCodeSource();
		URI location = (codeSource == null ? null : codeSource.getLocation().toURI());
		String path = (location == null ? null : location.getSchemeSpecificPart());

		if (path == null) {
			throw new IllegalStateException("Unable to determine code source archive");
		}

		File root = new File(decodeURL(path)).getAbsoluteFile();
		if (!root.exists()) {
			throw new IllegalStateException("Unable to determine code source archive from " + root);
		}
		return root.getAbsolutePath();
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
