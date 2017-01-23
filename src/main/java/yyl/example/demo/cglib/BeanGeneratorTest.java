package yyl.example.demo.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.beans.BeanGenerator;

/**
 * 使用CgLib创建一个JavaBean
 */
public class BeanGeneratorTest {
	public static void main(String[] args) {

		BeanGenerator generator = new BeanGenerator();

		// 设置一个字段value 类型为Integer
		generator.addProperty("name", String.class);
		generator.addProperty("value", Integer.class);

		Object bean = generator.create();

		// 通过反射查看所有方法
		Class<?> clazz = bean.getClass();
		for (Method method : clazz.getDeclaredMethods()) {
			System.out.println(method);
		}

	}

	static class A {
		protected A() {
			System.out.println("A -> 私有构造");
		}
	}

}
