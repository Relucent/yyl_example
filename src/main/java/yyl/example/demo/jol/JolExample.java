package yyl.example.demo.jol;

import org.openjdk.jol.info.ClassLayout;

/**
 * JOL全称为Java Object Layout，是分析JVM中对象布局的工具.
 */
public class JolExample {
	public static void main(String[] args) {
		A a = new A();

		ClassLayout aLayout = ClassLayout.parseInstance(a);

		System.out.println("**** Fresh object");
		System.out.println(aLayout.toPrintable());

		synchronized (a) {
			System.out.println("**** With the lock");
			System.out.println(aLayout.toPrintable());
		}

		B b = new B();
		System.out.println(ClassLayout.parseInstance(b).toPrintable());

		Object o = new Object();
		System.out.println(ClassLayout.parseInstance(o).toPrintable());

		int[] ints = new int[0];
		System.out.println(ClassLayout.parseInstance(ints).toPrintable());

		Object[] objects = { true, 0.0D, new Object(), null };
		System.out.println(ClassLayout.parseInstance(objects).toPrintable());
	}

	static class A {
		String name = "hello";
		Integer value = 100;
	}

	static class B {
	}
}
