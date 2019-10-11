package yyl.example.basic.compiler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * 动态类编译器程序
 */
public class JavaCompilerEngine {

    /** Java 编程语言编译器 */
    private final JavaCompiler javac;
    /** 编译的参数 */
    private Iterable<String> options;
    /** 是否忽略编译警告 */
    private boolean ignoreWarnings = false;

    /**
     * 构造函数
     */
    public JavaCompilerEngine() {
        this.javac = ToolProvider.getSystemJavaCompiler();
    }

    /**
     * 设置是否忽略编译警告
     * @param ignoreWarnings 是否忽略编译警告
     */
    public void setIgnoreWarnings(boolean ignoreWarnings) {
        this.ignoreWarnings = ignoreWarnings;
    }

    /**
     * 设置编译器选项
     * @param options 编译器使用的选项
     */
    public void setOptions(String... options) {
        this.options = Arrays.asList(options);
    }

    /**
     * 批量编译源码
     * @param compilationUnits 源文件对象列表
     * @return 类列表，包含所有编译类的实例
     * @throws CompilationException 编译中发生错误
     */
    public Map<String, Class<?>> compile(List<JavaSourceFileObject> compilationUnits) throws CompilationException {
        if (compilationUnits.size() == 0) {
            throw new CompilationException("No source code to compile");
        }

        StandardJavaFileManager standardJavaFileManager = javac.getStandardFileManager(null, null, null);
        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
        ForwardingStandardJavaFileManager fileManager = new ForwardingStandardJavaFileManager(standardJavaFileManager);

        JavaCompiler.CompilationTask task = javac.getTask(null, fileManager, collector, options, null, compilationUnits);

        boolean result = task.call();
        if (!result || collector.getDiagnostics().size() > 0) {
            StringBuffer exceptionMsg = new StringBuffer();
            exceptionMsg.append("Unable to compile the source");
            boolean hasWarnings = false;
            boolean hasErrors = false;
            for (Diagnostic<? extends JavaFileObject> diagnostic : collector.getDiagnostics()) {
                switch (diagnostic.getKind()) {
                    case NOTE:
                    case MANDATORY_WARNING:
                    case WARNING:
                        hasWarnings = true;
                        break;
                    case OTHER:
                    case ERROR:
                    default:
                        hasErrors = true;
                        break;
                }
                JavaFileObject source = diagnostic.getSource();
                exceptionMsg.append("\n").append(source);
                exceptionMsg.append("\n").append("[kind=").append(diagnostic.getKind());
                exceptionMsg.append(", ").append("line=").append(diagnostic.getLineNumber());
                exceptionMsg.append(", ").append("message=").append(diagnostic.getMessage(Locale.US)).append("]");
            }
            if ((hasWarnings && !ignoreWarnings) || hasErrors) {
                throw new CompilationException(exceptionMsg.toString());
            }
        }
        Map<String, Class<?>> classes = new HashMap<String, Class<?>>();

        for (String className : fileManager.getCompiledClassNames()) {
            JavaClassFileObject jcfo = fileManager.getJavaClassFileObject(className);
            String name = jcfo.getName();
            byte[] code = jcfo.getContentByteArray();
            try {
                Class<?> clazz = ClassLoaderHelper.defineClass(name, code);
                classes.put(name, clazz);
            } catch (Error e) {
                throw new CompilationException("defineClass->" + name, e);
            }
        }
        return classes;
    }

    /**
     * 编译单个源文件
     * @param name 类名
     * @param source 源码
     * @return 编译的类文件
     * @throws Exception 编译中发生异常
     */
    public Class<?> compile(String name, String source) throws Exception {
        JavaSourceFileObject jsfo = makeJavaSourceFileObject(name, source);
        List<JavaSourceFileObject> compilationUnits = Arrays.asList(jsfo);
        Map<String, Class<?>> classes = compile(compilationUnits);
        return classes.get(name);
    }

    /**
     * 创建JAVA源文件对象
     * @param name 类名
     * @param source 类源码
     * @return JAVA源文件对象
     */
    public JavaSourceFileObject makeJavaSourceFileObject(String name, String source) {
        return new JavaSourceFileObject(name, source);
    }
}
