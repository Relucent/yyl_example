package yyl.example.basic.reflect;

import java.lang.reflect.Field;

/**
 * 通过反射修改Final修饰的字段(测试JDK1.7)<br>
 * 表面现象:<br>
 * 构造中赋值的final对象，可以通过反射来修改其值，但是直接赋值的final对象，反射来修改其值没有作用。<br>
 * 本质原因:<br>
 * System.out.println(a.value2);被编译器优化编译成了a.getClass();System.out.println(0); <br>
 * 适用范围:<br>
 * 反射可以修改对象中非static的final字段，而static final关键字修饰的是常量，常量是不可以被修改的。
 */
public class UpdateFinalVariableTest {

	static class A {
		final int value1;
		final int value2 = 0;

		A() {
			value1 = 0;
		}
	}

	public static void main(String[] args)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		A a = new A();

		setFieldValue(a, "value1", 1);
		setFieldValue(a, "value2", 1);
		// 打印1，证明反射可以修改final字段
		System.out.println(a.value1);

		// 此处可能会打印0，因为编译成字节码的时候做了优化(编译器认为final不可变)，所以代码会编译成 a.getClass(); System.out.println(0);
		System.out.println(a.value2);

		// 实际上这个value2还是被修改了
		System.out.println(getFieldValue(a, "value2"));
	}

	private static void setFieldValue(Object o, String filedName, Object value)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = o.getClass().getDeclaredField(filedName);
		field.setAccessible(true);
		field.set(o, value);
	}

	private static Object getFieldValue(Object o, String filedName)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = o.getClass().getDeclaredField(filedName);
		field.setAccessible(true);
		return field.get(o);
	}

}
