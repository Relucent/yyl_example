package yyl.example.demo.mybatis;

import org.apache.ibatis.reflection.Reflector;

/**
 * MyBatis 反射的核心类Reflector
 */
public class ReflectorExample {
	public static void main(String[] args) {
		Reflector reflector = new Reflector(PrivateBean.class);
		// 获得对应属性，大小写不敏感
		String property1 = reflector.findPropertyName("value");
		String property2 = reflector.findPropertyName("VALUE");
		String property3 = reflector.findPropertyName("Value");
		System.out.println(property1);
		System.out.println(property2);
		System.out.println(property3);
	}
}
