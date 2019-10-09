package yyl.example.basic.jdk5;

import java.lang.reflect.Constructor;

/**
 * Java泛型(JavaGenerics)是J2SE1.5中引入的一个新特性，其本质是参数化类型，也就是说所操作的数据类型被指定为一个参数（type parameter）这种参数类型可以用在类、接口和方法的创建中，分别称为泛型类、泛型接口、泛型方法。<br>
 * 泛型特点和作用：<br>
 * 泛化。可以用泛型T代表任意类型。<br>
 * 类型安全。泛型的一个主要目标就是提高java程序的类型安全，使用泛型可以使编译器知道变量的类型限制，进而可以在更高程度上验证类型假设。如果不用泛型，则必须使用强制类型转换，而强制类型转换不安全，在运行期可能发生ClassCastException异常，如果使用泛型，则会在编译期就能发现该错误。<br>
 * 消除强制类型转换。泛型可以消除源代码中的许多强制类型转换，这样可以使代码更加可读，并减少出错的机会。<br>
 * 向后兼容。支持泛型的Java编译器（例如JDK1.5中的Javac）可以用来编译经过泛型扩充的Java程序（Generics Java程序），但是现有的没有使用泛型扩充的Java程序仍然可以用这些编译器来编译。 <br>
 */
public class GenericsTest {

    public static void main(String[] args) throws Exception {
        Wraper<Integer> wraper = new Wraper<Integer>();
        wraper.value = Integer.valueOf(0);
        System.out.println(wraper.value);

        Hello hello = GenericsTest.<Hello>newInstance(Hello.class);
        hello.print();
    }

    // 泛型方法
    private static <T> T newInstance(Class<T> clazz) throws Exception {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        T instance = constructor.newInstance();
        return instance;
    }

    // 泛型类
    private static class Wraper<T> {
        public T value;
    }

    private static class Hello {
        public void print() {
            System.out.println("hello");
        };
    }
}
