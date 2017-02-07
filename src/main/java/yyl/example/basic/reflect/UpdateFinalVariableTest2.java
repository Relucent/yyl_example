package yyl.example.basic.reflect;

import java.lang.reflect.Field;

/**
 * 通过反射修改常量池中的String(测试JDK1.7)<br>
 * 这只是个测试，可能会引起不可预测的后果，应用中应避免这种用法<br>
 */
public class UpdateFinalVariableTest2 {

	public static void main(String[] args) throws Exception {

		Field field = String.class.getDeclaredField("value");
		field.setAccessible(true);
		field.set("hello", "hello world".toCharArray());

		System.out.println("hello");//此处会打印 hello world (常量池中的字符串被修改了)

		field.set("hello", "hello".toCharArray());
		System.out.println("hello");//此处会打印 hello world (因为常量hello已经是hello world了)

		field.set("hello", new char[] { 'h', 'e', 'l', 'l', 'o' });
		System.out.println("hello");//此处会打印 hello(常量池中的字符串终于被改回来了)
	}

}
