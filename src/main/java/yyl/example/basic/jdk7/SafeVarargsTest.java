package yyl.example.basic.jdk7;

/**
 * 当使用一个不可具体化的参数（Non-Reifiable Formal Parameters）调用一个可变参数方法（Varargs Methods ）编辑器会生成一个“非安全操作”的警告。<br>
 * jdk1.7在变长参数和范型结合使用的时候，增加了一个@SafeVarargs。通过该注解来告诉编译器参数类型的安全性，以此来解决每次调用都出现编译警告的问题。
 */
public class SafeVarargsTest {
    public static void main(String[] args) {

        test(1, 2, 3, 4, 5);

        test("hello!");

    }

    // Type safety: Potential heap pollution via varargs parameter values
    @SafeVarargs
    private static <T> void test(T... values) {
        for (Object value : values) {
            System.out.println(value);
        }
    }

}
