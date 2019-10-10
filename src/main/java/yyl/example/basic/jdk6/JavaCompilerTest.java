package yyl.example.basic.jdk6;

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
 * 在非常多Java应用中需要在程式中调用Java编译器来编译和运行。<br>
 * 在早期的版本中(Java SE5及以前版本)中只能通过tools.jar中的com.sun.tools.javac包来调用Java编译器，但由于tools.jar不是标准的Java库，在使用时必须要设置这个jar的路径。<br>
 * 在Java SE6中为我们提供了标准的包来操作Java编译器，这就是javax.tools包。<br>
 * JavaCompiler接口的默认实现可以通过 ToolProvider.getSystemJavaCompiler()方法来获得 <br>
 */
public class JavaCompilerTest {

    // 格式化输出
    private static Formatter formatter = new Formatter(System.out);

    // 放置class的临时文件夹
    private static final String tempdir = "./target/temp-classes";
    static {
        new File(tempdir).mkdirs();
    }

    // 创建一个 HelloWorld 类
    public static void main(String[] args) throws Exception {

        // 类名
        String className = "HelloWorld";

        // 类的源码
        String source = ""//
                + "public class " + className + " {"//
                + " public String toString() {"//
                + "  return \"HelloWorld\";"//
                + " }"//
                + "}"//
                + "";

        boolean success = compile(className, source);

        if (success) {
            URLClassLoader classLoader = new URLClassLoader(new URL[] {new File(tempdir).toURI().toURL()}) {
                // 加上这个方法是为了在ClassLoader被GC的时候够输出信息
                protected void finalize() throws Throwable {
                    System.out.println("URLClassLoader -> Finalize!");
                }
            };
            Class<?> clazz = classLoader.loadClass(className);
            System.out.println(clazz.newInstance());
            classLoader.close();
        }

        // 没有对HelloWorld的引用，所以URLClassLoader能够被回收
        System.gc();
        Thread.sleep(1000);// 等待回收
    }

    private static boolean compile(String className, String source) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        // DiagnosticListener 编译的诊断信息
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

        CompilationTask task = compiler.getTask(null, null, diagnostics, Arrays.asList("-d", tempdir), null,
                Arrays.asList(new JavaSourceFromString(className, source)));

        boolean success = task.call();

        System.out.println("Success: " + success);
        print(diagnostics);

        return success;
    }

    private static void print(DiagnosticCollector<JavaFileObject> diagnostics) {
        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
            formatter.format("" //
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
