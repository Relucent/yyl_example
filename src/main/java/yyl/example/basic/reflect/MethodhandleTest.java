package yyl.example.basic.reflect;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

/**
 * 方法句柄（method handle）是JSR 292中引入的一个重要概念，它是对Java中方法、构造方法和域的一个强类型的可执行的引用。<br>
 * 以下测试 MethodHandle,Method 的性能差异<br>
 * JDK版本1.7<br>
 */
public class MethodhandleTest {

	private static int COUNT = 100000;

	//目前测试结果是 Method性能高于MethodHandle
	public static void main(String[] args) throws Throwable {
		test1();
		test2();
		test3();
	}

	//使用MethodHandle调用静态方法
	public static void test1() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findStatic(MethodhandleTest.class, "method", MethodType.methodType(int.class, int.class));
		long l = System.currentTimeMillis();
		for (int i = 0; i < COUNT; i++) {
			mh.invoke(i);
		}
		System.out.println((System.currentTimeMillis() - l));
	}

	//使用常规的反射调用静态方法
	public static void test2() throws Throwable {
		Method method = MethodhandleTest.class.getMethod("method", int.class);
		long l = System.currentTimeMillis();
		for (int i = 0; i < COUNT; i++) {
			method.invoke(null, i);
		}
		System.out.println((System.currentTimeMillis() - l));
	}

	//直接调用静态方法
	public static void test3() throws Throwable {
		long l = System.currentTimeMillis();
		for (int i = 0; i < COUNT; i++) {
			method(i);
		}
		System.out.println((System.currentTimeMillis() - l));
	}

	//静态方法
	public static int method(int val) {
		return val;
	}

}
