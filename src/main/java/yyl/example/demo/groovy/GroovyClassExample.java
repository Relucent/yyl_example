package yyl.example.demo.groovy;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

public class GroovyClassExample {

	public static void main(String[] args) throws Exception {
		try (GroovyClassLoader loader = new GroovyClassLoader()) {
			String code = ""//
					+ "def cal(int a, int b){"//
					+ " return a+b"//
					+ "}";//
			@SuppressWarnings("unchecked")
			Class<GroovyObject> clazz = loader.parseClass(code);
			GroovyObject object = clazz.newInstance();
			Object[] params = { 1, 2 };
			int result = (int) object.invokeMethod("cal", params);
			System.out.println(result);
		}
	}
}