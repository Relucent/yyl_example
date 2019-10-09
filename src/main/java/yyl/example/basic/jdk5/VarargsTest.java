package yyl.example.basic.jdk5;

/**
 * jdk5.0出现的新特性。将同一个类中，多个方法名相同、参数类型相同、返回类型相同，仅仅是参数个数不同的方法抽取成一个方法，这种方法称为可变参数的方法。<br>
 * 可变长度参数必须作为方法参数列表中的的最后一个参数且方法参数列表中只能有一个可变长度参数。<br>
 * 可变长度参数本质是一个语法糖，它的真正的实现方式是的数组封装。<br>
 */
public class VarargsTest {

    public static void main(String[] args) {

        print("hello", " ", "world", "\n");

        // 可变长度参数本质是一个语法糖，它的实现其实是封装的数组。
        print(new String[] {"hello", " ", "world", "\n"});
    }

    private static void print(String... values) {
        for (int i = 0; i < values.length; i++) {
            System.out.print(values[i]);
        }
    }
}
