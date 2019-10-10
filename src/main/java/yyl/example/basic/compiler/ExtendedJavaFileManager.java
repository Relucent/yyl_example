package yyl.example.basic.compiler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

/**
 * 源文件和类文件之上进行操作的工具的文件管理器实现类
 */
public class ExtendedJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    /** 编译的类文件对象列表 */
    private final Map<String, JavaClassFileObject> compiledClassFileStore = new ConcurrentHashMap<String, JavaClassFileObject>();

    /**
     * 构造函数
     * @param javaFileManager 委托转发的文件管理器
     */
    protected ExtendedJavaFileManager(JavaFileManager javaFileManager) {
        super(javaFileManager);
    }

    /**
     * 获得JAVA输出文件对象
     * @param location 对象位置信息
     * @param className 类名
     * @param kind 文件的种类
     * @param sibling 提示的文件对象(可为空)
     * @return 输出文件对象
     */
    @Override
    public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className, JavaFileObject.Kind kind, FileObject sibling)
            throws IOException {
        if (kind == JavaFileObject.Kind.CLASS) {
            JavaClassFileObject jcfo = new JavaClassFileObject(className);
            compiledClassFileStore.put(jcfo.getName(), jcfo);
            return jcfo;
        }
        return super.getJavaFileForOutput(location, className, kind, sibling);
    }

    /**
     * 获得类文件对象
     * @param className 类名
     * @return 获得类文件对象
     */
    public JavaClassFileObject getJavaClassFileObject(String className) {
        return compiledClassFileStore.get(className);
    }
}
