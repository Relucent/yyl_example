package yyl.example.basic.annotation;

import java.lang.reflect.Method;

public class ExtractAnno {

	public static void main(String[] arg) {
		Method[] methods = Methods.class.getDeclaredMethods();
		for (Method method : methods) {
			Anno an = method.getAnnotation(Anno.class);
			if (an != null) {
				System.out.println("id=" + an.id() + ";name=" + an.name());
			} else {
				System.out.println("an is NULL");
			}
		}
	}
}
