package yyl.example.basic.environment;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

public class SystemProperties2 {
	public static void main(String[] args) {
		System.out.println("App.ContextRealPath" + " -> " + getContextRealPath());
		System.out.println("System.JavaVersion" + " -> " + System.getProperty("java.version"));
		System.out.println("System.JavaVendor" + " -> " + System.getProperty("java.vendor"));
		System.out.println("System.JavaHome" + " -> " + System.getProperty("java.home"));
		System.out.println("System.OSPatchLevel" + " -> " + System.getProperty("sun.os.patch.level"));
		System.out.println("System.OSArch" + " -> " + System.getProperty("os.arch"));
		System.out.println("System.OSVersion" + " -> " + System.getProperty("os.version"));
		System.out.println("System.OSName" + " -> " + System.getProperty("os.name"));
		if ((System.getProperty("os.name").toLowerCase().indexOf("windows") > 0)
				&& (System.getProperty("os.name").equals("6.1"))) {
			System.out.println("System.OSName" + " -> " + "Windows 7");
		}
		System.out.println("System.OSUserLanguage" + " -> " + System.getProperty("user.language"));
		System.out.println("System.OSUserName" + " -> " + System.getProperty("user.name"));
		System.out.println("System.LineSeparator" + " -> " + System.getProperty("line.separator"));
		System.out.println("System.FileSeparator" + " -> " + System.getProperty("file.separator"));
		System.out.println("System.FileEncoding" + " -> " + System.getProperty("file.encoding"));
	}

	public static String getContextRealPath() {
		String path = getClassesPath();
		int index = path.indexOf("WEB-INF");
		if (index > 0) {
			path = path.substring(0, index);
		}
		return path;
	}

	public static String getClassesPath() {
		String classPath = null;
		if (classPath == null) {
			URL url = SystemProperties2.class.getClassLoader().getResource("yyl/example/basic/environment/SystemProperties2.class");
			if (url == null) {
				System.err.println("Config.getClassesPath() failed!");
				return "";
			}
			try {
				String path = URLDecoder.decode(url.getPath(), getFileEncode());
				if ((System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) && (path.startsWith("/"))) {
					path = path.substring(1);
				}

				if (path.startsWith("file:/"))
					path = path.substring(6);
				else if (path.startsWith("jar:file:/")) {
					path = path.substring(10);
				}
				if (path.indexOf(".jar!") > 0) {
					path = path.substring(0, path.indexOf(".jar!"));
				}
				path = path.replace('\\', '/');
				path = path.substring(0, path.lastIndexOf("/") + 1);
				if (path.indexOf("WEB-INF") >= 0) {
					path = path.substring(0, path.lastIndexOf("WEB-INF") + 7) + "/plugins/classes/";
				}
				if ((System.getProperty("os.name").toLowerCase().indexOf("windows") < 0) && (!path.startsWith("/"))) {
					path = "/" + path;
				}
				classPath = path;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return classPath;
	}

	public static String getFileEncode() {
		return System.getProperty("file.encoding");
	}
}
