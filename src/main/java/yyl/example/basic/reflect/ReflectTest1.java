package yyl.example.basic.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 反射获得类信息<br>
 * 测试自JDK1.6 <br>
 */
public class ReflectTest1 {

	public static void main(String[] args) {
		Class<A> aClass = A.class;

		for (Constructor<?> constructor : aClass.getDeclaredConstructors()) {
			System.out.println(constructor.getName());
			System.out.println("ParameterTypes -> " + Arrays.asList(constructor.getParameterTypes()));
		}

		for (Field field : aClass.getDeclaredFields()) {
			System.out.println(field.getName());
			System.out.println("type -> " + field.getType());
		}
		System.out.println();

		for (Method method : aClass.getDeclaredMethods()) {
			System.out.println(method.getName());
			System.out.println("ParameterTypes -> " + Arrays.asList(method.getParameterTypes()));
			System.out.println("ReturnType -> " + method.getReturnType());
		}

	}

	static class A {
		String value;
		protected int b;

		protected A() {
			System.out.println("A -> constructor A()");
		}

		public A(int value) {
			System.out.println("A -> constructor  A(int)");
		}

		public int method1() {
			return 0;
		}

		public void method2(String p1, int p2, Object p3) {
		}
	}
}