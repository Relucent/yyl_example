package yyl.example.basic.jdk7;

/**
 * 当使用一个不可具体化的参数（Non-Reifiable Formal Parameters）调用一个可变参数方法（Varargs Methods ）编辑器会生成一个“非安全操作”的警告。
 */
public class VarargsTest {
	public static void main(String[] args) {

		test(1, 2, 3, 4, 5);

		test("hello!");

	}

	//Type safety: Potential heap pollution via varargs parameter values
	private static <T> void test(@SuppressWarnings("unchecked") T... values) {
		for (Object value : values) {
			System.out.println(value);
		}
	}

}
