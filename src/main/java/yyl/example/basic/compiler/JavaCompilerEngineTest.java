package yyl.example.basic.compiler;

import java.lang.reflect.Method;

/**
 * 在非常多Java应用中需要在程式中调用Java编译器来编译和运行。<br>
 * 在早期的版本中(Java SE5及以前版本)中只能通过tools.jar中的com.sun.tools.javac包来调用Java编译器，但由于tools.jar不是标准的Java库，在使用时必须要设置这个jar的路径。<br>
 * 在Java SE6中为我们提供了标准的包来操作Java编译器，这就是javax.tools包。<br>
 */
public class JavaCompilerEngineTest {

    public static void main(String[] args) throws Exception {

        JavaCompilerEngine compileEngine = new JavaCompilerEngine();

        // 自定义类名
        String name = "yyl.example.basic.jdk6.Hello";
        // 源码
        String source = ""//
                + "package yyl.example.basic.jdk6;\n"//
                + "public class Hello{\n"//
                + "    public static void main(String[] args) {\n"//
                + "        System.out.println(\"Hello, World\");\n"//
                + "    }\n"//
                + "}\n";

        Class<?> clazz = compileEngine.compile(name, source);
        System.out.println(clazz);

        Method method = clazz.getMethod("main", String[].class);

        method.invoke(null, (Object) new String[0]);
    }

}
