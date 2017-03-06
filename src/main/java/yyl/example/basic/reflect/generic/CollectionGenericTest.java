package yyl.example.basic.reflect.generic;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 集合泛型的获取
 */
public class CollectionGenericTest {

	public static void main(String[] args) throws Exception {

		Class<?> clazz = Demo.class;

		printlnFieldGeneric(clazz.getDeclaredField("list"));
		printlnFieldGeneric(clazz.getDeclaredField("map"));
	}

	private static void printlnFieldGeneric(Field field) {
		ParameterizedType pt = (ParameterizedType) field.getGenericType();
		System.out.println(pt);
		Type[] types = pt.getActualTypeArguments();
		for (int i = 0; i < types.length; i++) {
			System.out.println(types[i]);
		}
		System.out.println();
	}

	@SuppressWarnings("unused")
	private static class Demo {

		List<String> list;

		Map<Integer, Object> map;
	}
}
