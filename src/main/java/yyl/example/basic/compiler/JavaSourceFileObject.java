package yyl.example.basic.compiler;

import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * JAVA源文件文件对象
 */
public class JavaSourceFileObject extends SimpleJavaFileObject {

    /** 类名称 */
    private final String name;
    /** 源码内容 */
    private final String content;

    /**
     * 构造函数
     * @param name 类名称
     * @param source 类源码内容
     */
    public JavaSourceFileObject(String name, String content) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.name = name;
        this.content = content;
    }

    /**
     * 获得源文件的类名称
     * @return 源文件的类名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获得源码字符串
     * @param ignoreEncodingErrors 是否忽略编码错误
     */
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return content;
    }
}
