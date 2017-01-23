package yyl.example.basic.unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;

public abstract class UnsafeAllocatorTest {
	public static void main(String[] args) {

		System.out.println("正常的反射机制创建类实例");
		try {
			A a = A.class.getConstructor(String.class).newInstance("A");
			System.out.println(a.value);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("UnsafeAllocator创建类实例，不需要调用构造函数");
		try {
			A a = AccessController.doPrivileged(new PrivilegedAction<A>() {
				@Override
				public A run() {
					try {
						A a = UnsafeAllocator.create().newInstance(A.class);
						Field field = A.class.getDeclaredField("value");
						field.setAccessible(true);
						field.set(a, "A");
						field.setAccessible(false);
						return a;
					} catch (Exception ignored) {
						return null;
					}
				}
			});

			System.out.println(a.value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class A {
		public final String value;

		public A(String value) {
			System.out.println(">>" + value);
			this.value = value;
		}
	}
}