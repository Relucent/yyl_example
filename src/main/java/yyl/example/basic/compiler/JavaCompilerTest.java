package yyl.example.basic.compiler;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Formatter;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

/**
 * JavaCompiler JAVA编译器<br>
 * JavaCompiler接口的默认实现可以通过 ToolProvider.getSystemJavaCompiler()方法来获得 <br>
 */
public class JavaCompilerTest {

	//格式化输出
	private static Formatter formatter = new Formatter(System.out);

	//放置class的临时文件夹
	private static final String tempdir = "./target/temp-classes";
	static {
		new File(tempdir).mkdirs();
	}

	//创建一个 HelloWorld 类
	public static void main(String[] args) throws Exception {

		//类名
		String className = "HelloWorld";

		//类的源码
		String source = ""//
				+ "public class " + className + " {"//
				+ " public String toString() {"//
				+ "  return \"HelloWorld\";"//
				+ " }"//
				+ "}"//
				+ "";

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		//DiagnosticListener 编译的诊断信息
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

		CompilationTask task = compiler.getTask(null, null, diagnostics, Arrays.asList("-d", tempdir), null,
				Arrays.asList(new JavaSourceFromString(className, source)));

		boolean success = task.call();

		System.out.println("Success: " + success);
		print(diagnostics);

		if (success) {
			URLClassLoader classLoader = new URLClassLoader(new URL[] { new File(tempdir).toURI().toURL() });
			Class<?> clazz = classLoader.loadClass("HelloWorld");
			System.out.println(clazz.newInstance());
			classLoader.close();
		}
	}

	private static void print(DiagnosticCollector<JavaFileObject> diagnostics) {
		for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
			formatter.format(
					"" //
							+ "Code: %s%n" //
							+ "Kind: %s%n" //
							+ "Position: %s%n" //
							+ "Start Position: %s%n"//
							+ "End Position: %s%n" //
							+ "Source: %s%n" //
							+ "Message: %s%n", //
					diagnostic.getCode(), //
					diagnostic.getKind(), //
					diagnostic.getPosition(), //
					diagnostic.getStartPosition(), //
					diagnostic.getEndPosition(), //
					diagnostic.getSource(), //
					diagnostic.getMessage(null)).flush();
		}
	}

	private static class JavaSourceFromString extends SimpleJavaFileObject {
		private String content = null;

		public JavaSourceFromString(String name, String content) {
			super(URI.create(name + Kind.SOURCE.extension), Kind.SOURCE);
			this.content = content;
		}

		public CharSequence getCharContent(boolean ignoreEncodingErrors) {
			return content;
		}
	}

}
