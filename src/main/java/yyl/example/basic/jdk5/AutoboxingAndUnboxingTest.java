package yyl.example.basic.jdk5;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动装箱与拆箱<br>
 * Java中包含了8种基本数据类型：<br>
 * 整数类型：byte、short、int、long<br>
 * 字符类型：char<br>
 * 浮点类型：float、double<br>
 * 布尔类型：boolean<br>
 * 这8种基本数据类型的变量不需要使用new来创建，它们不会在堆上创建，而是直接在栈内存中存储，因此会比使用对象更加高效。<br>
 * 在某些时候，基本数据类型会有一些制约，例如当有个方法需要Object类型的参数。所有引用类型的变量都继承了Object类，都可当成Object类型变量使用，但基本数据类型的变量就不可以了。<br>
 * 为了解决某些场景的问题，Java为这8种基本数据类型分别定义了相应的引用类型，并称之为基本数据类型的包装类（Wrapper Class） <br>
 * 在Java SE5之前，把基本数据类型变量变成包装类实例需要通过对应包装类的构造器来实现，把包装器类型转换为基本数据类型需要通过包装类的方法实现，例如<br>
 * Integer i = new Integer(10); <br>
 * int a = i.intValue();<br>
 * 从Java SE5开始，为了简化开发，Java提供了自动装箱（Autoboxing）和自动拆箱（AutoUnboxing）功能<br>
 * 装箱就是自动将基本数据类型转换为包装器类型；拆箱就是自动将包装器类型转换为基本数据类型。<br>
 */
public class AutoboxingAndUnboxingTest {

    public static void main(String[] args) {

        // 自动装箱，是通过包装类的valueOf()方法来实现的
        Integer intWrapped = 1;// Integer.valueOf(1);
        System.out.println("i = " + intWrapped);

        // 包装类型和基本类型的大小比较(自动拆箱)
        System.out.println("i > 0 == " + (intWrapped > 0));

        // 包装类型的运算(自动拆箱)
        System.out.println("i - 1 == " + (intWrapped - 1));

        // 自动拆箱，是通过包装类对象的xxxValue()来实现的
        long longPrimitive = intWrapped;// intWrapped.longValue();

        // 将基本数据类型放入集合类(自动装箱)
        List<Long> list = new ArrayList<>();
        list.add(longPrimitive);// list.add(Long.valueOf(longPrimitive));
        Long longPrimitive2 = list.get(0);// list.get(0).longValue()
        System.out.println(longPrimitive2);
    }
}
