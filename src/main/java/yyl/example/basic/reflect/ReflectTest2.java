package yyl.example.basic.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * 一个有趣的现象是，非静态内部类和非静态方法中的匿名类会隐含有一个指向所在外部类实例的this$0属性<br>
 * 更有趣的是，可以用反射去修改(反射不是万能的反射是无所不能的 —_—! ) <br>
 * 目测如果匿名类作为返回值，这个this$0引用是隐含的并被泄漏了出去的，也就是使用不当可能会导致原先的外部类实例无法被垃圾回收。<br>
 * 前端帝们颤抖吧，你们研究的所谓闭包啥的JAVA早就有了。<br>
 * 测试自JDK1.6<br>
 */
public class ReflectTest2 {

	public static void main(String[] args) {

		Class<ReflectTest2.A> aClass = ReflectTest2.A.class;

		for (Constructor<?> constructor : aClass.getDeclaredConstructors()) {
			System.out.println(constructor);
		}

		for (Field field : aClass.getDeclaredFields()) {
			System.out.println(field);
		}

		ReflectTest2.A a = new ReflectTest2("0").new A();
		System.out.println(a);

		try {
			Field field = a.getClass().getDeclaredField("this$0");
			field.setAccessible(true);
			field.set(a, new ReflectTest2("1"));
		} catch (Exception e) {
		}

		System.out.println(a);
	}

	private final String name;

	public ReflectTest2(String name) {
		this.name = name;
	}

	public class A {
		@Override
		public String toString() {
			return name + " -> A";
		}
	}
}