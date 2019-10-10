package yyl.example.basic.compiler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;


/**
 * JAVA Class文件对象
 */
public class JavaClassFileObject extends SimpleJavaFileObject {

    /** 类名称 */
    private final String name;
    /** 字节输出流 */
    private final ByteArrayOutputStream bos;

    /**
     * 构造函数
     * @param name 类名称
     */
    public JavaClassFileObject(String name) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.CLASS.extension), Kind.CLASS);
        this.name = name;
        this.bos = new ByteArrayOutputStream();
    }

    /**
     * 获得源文件的类名称
     * @return 源文件的类名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获得类文件内容的字节数组
     * @return 类文件内容的字节数组
     */
    public byte[] getContentByteArray() {
        return bos.toByteArray();
    }

    /**
     * 获取此文件对象的 {@code OutputStream}
     * @return {@code OutputStream}
     */
    @Override
    public OutputStream openOutputStream() throws IOException {
        return bos;
    }
}
