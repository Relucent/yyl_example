package yyl.example.basic.reflect;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 扫描包下的类<br>
 * 测试于 JDK1.6 , JDK1.7<br>
 */
public class ClassPackageScanner {

	// ===================================Fields==============================================
	private static final String CLASS_SUFFIX = ".class";
	private static final String JAR_FILE_SUFFIX = ".jar";
	private static final String BLANK = "";

	private static final char UNIX_SEPARATOR = '/';
	private static final char OTHER_SEPARATOR = '\\';
	private static final char DOT = '.';

	// ===================================Methods=============================================
	/**
	 * 获得包路径下的类名称列表 </br>
	 * @param packageName 包路径
	 * @return 类名称列表
	 */
	public static List<String> scanPackage(String packageName) {
		String pattern = packageName.replace(DOT, UNIX_SEPARATOR) + UNIX_SEPARATOR;
		List<String> result = new ArrayList<String>();
		for (File root : getAllClassPathRootFiles()) {
			if (root.isDirectory()) {
				doFindClassDirectory(root, pattern, result);
			} else if (root.isFile() && isJarSuffix(root.getName())) {
				doFindJarFile(root, pattern, result);
			}
		}
		return result;
	}

	/**
	 * 处理为jar文件的情况
	 */
	protected static void doFindClassDirectory(File root, String pattern, List<String> result) {
		String rootPath = root.getAbsolutePath();
		LinkedList<File> stack = new LinkedList<File>();
		stack.push(root);

		while (!stack.isEmpty()) {
			File file = stack.pop();
			String path = normalize(relative(rootPath, file.getAbsolutePath()));

			if (!match(pattern, path)) {
				continue;
			}

			if (file.isDirectory()) {
				for (File fs : file.listFiles()) {
					stack.push(fs);
				}
			} else if (file.isFile() && isClassSuffix(path)) {
				String className = getCanonicalClassName(path);
				if (checkClassName(className)) {
					result.add(className);
				}
			}
		}
	}

	/**
	 * 处理为jar文件的情况
	 */
	protected static void doFindJarFile(File root, String pattern, List<String> result) {
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(root);
			for (@SuppressWarnings("unchecked")
			Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zipFile.entries(); en.hasMoreElements();) {
				ZipEntry zipEntry = en.nextElement();
				String path = normalize(zipEntry.getName());
				if (isClassSuffix(path) && match(pattern, path)) {
					String className = getCanonicalClassName(path);
					if (checkClassName(className)) {
						result.add(className);
					}
				}
			}
		} catch (Exception ex) {
			// ignore 
		} finally {
			try {
				if (zipFile != null) {
					zipFile.close();
				}
			} catch (IOException e) {
				// ignore 
			}
		}
	}

	// ===================================ToolMethods=========================================
	private static boolean checkClassName(String className) {
		return true;
	}

	private static boolean isClassSuffix(String path) {
		return path.endsWith(CLASS_SUFFIX);
	}

	private static boolean isJarSuffix(String path) {
		return path.endsWith(JAR_FILE_SUFFIX);
	}

	/**
	 * 获得文件相对路径
	 * @param root 根目录
	 * @param path 文件路径
	 * @return 拆分后的相对路径
	 */
	private static String relative(String root, String path) {
		if (path.indexOf(root) == 0) {
			return new String(path.substring(root.length()));
		}
		return path;
	}

	/**
	 * 规范化文件路径[文件分割符使用正斜杠(/)]
	 * @param path 规范化文件路径
	 * @return 符合规范的文件路径
	 */
	private static String normalize(String path) {
		if ((path == null) || (path.indexOf(OTHER_SEPARATOR) == -1)) {
			return path;
		}
		path = path.replace(OTHER_SEPARATOR, UNIX_SEPARATOR);
		if (path.indexOf(UNIX_SEPARATOR) == 0) {
			path = path.substring(1);
		}
		return path;
	}

	/**
	 * 获得类名的规范形式
	 * @param path 类路径
	 * @return 类名的规范形式
	 */
	private static String getCanonicalClassName(String path) {
		return path.replace(UNIX_SEPARATOR, DOT).replace(CLASS_SUFFIX, BLANK);
	}

	/**
	 * 路径是否匹配
	 * @param pattern 类路径
	 * @param path 对比的路径
	 * @return 如果可以匹配返回true，否则返回false
	 */
	private static boolean match(String pattern, String path) {
		return pattern.indexOf(path) == 0 || path.indexOf(pattern) == 0;
	}

	/**
	 * 获得可加载类的文件列表(classes和jar)
	 * @return 加载类的文件列表
	 */
	private static List<File> getAllClassPathRootFiles() {
		List<File> files = new ArrayList<File>();
		addAllClassPathRootFiles(files);
		addJavaHomeJarFile(files);
		return files;
	}

	/**
	 * 向列表中添加ClassPath(classes和jar)
	 * @param files 文件列表
	 */
	private static void addAllClassPathRootFiles(List<File> files) {
		ClassLoader classLoader = getDefaultClassLoader();
		while (classLoader != null) {
			if (classLoader instanceof URLClassLoader) {
				for (URL url : ((URLClassLoader) classLoader).getURLs()) {
					try {
						files.add(new File(url.toURI()));
					} catch (URISyntaxException e) {
					}
				}
			}
			classLoader = classLoader.getParent();
		}
	}

	/**
	 * 向列表中添加JDK内置的JAR文件
	 * @param files 文件列表
	 */
	private static void addJavaHomeJarFile(List<File> files) {
		LinkedList<File> stack = new LinkedList<File>();
		stack.push(new File(System.getProperty("java.home")));
		while (!stack.isEmpty()) {
			File file = stack.pop();
			if (file.isDirectory()) {
				for (File fs : file.listFiles()) {
					stack.push(fs);
				}
			} else if (file.isFile() && isJarSuffix(file.getName()) && file.canRead()) {
				files.add(file);
			}
		}
	}

	/**
	 * 获得默认类加载器
	 * @return 默认类加载器
	 */
	private static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {
			// Cannot access thread context ClassLoader 
		}
		if (cl == null) {
			// getClassLoader() returning null indicates the bootstrap ClassLoader
			try {
				cl = ClassLoader.getSystemClassLoader();
			} catch (Throwable ex) {
				// Cannot access system ClassLoader 
			}
		}
		return cl;
	}

	// ===================================TestMethods=========================================
	public static void main(String[] args) {
		try {

			for (String name : ClassPackageScanner.scanPackage("yyl.example.basic.reflect")) {
				System.out.println(name);
			}

			for (String name : ClassPackageScanner.scanPackage("java.util.regex")) {
				System.out.println(name);
			}

			for (String name : ClassPackageScanner.scanPackage("org.objectweb.asm")) {
				System.out.println(name);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
