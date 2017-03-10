package yyl.example.basic.reflect.generic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 获得字段和方法的泛型信息
 */
public class GenericTest {

	public List<String> list1 = new LinkedList<String>();
	public List<?> list2 = new LinkedList<Object>();

	public List<String> method1() {
		return null;
	}

	public static void main(String[] args) throws SecurityException, NoSuchFieldException, NoSuchMethodException {

		Class<?> clazz = GenericTest.class;

		for (Field filed : clazz.getDeclaredFields()) {
			System.out.println(filed.toGenericString());
			printlnGenericActualType(filed.getGenericType());
			System.out.println();
		}

		for (Method method : clazz.getDeclaredMethods()) {
			System.out.println(method.toGenericString());
			printlnGenericActualType(method.getGenericReturnType());
			System.out.println();
		}
	}

	private static void printlnGenericActualType(Type type) {
		if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) type;
			for (Type genericType : pt.getActualTypeArguments()) {
				System.out.println("genericType.class -> " + genericType.getClass());
				System.out.println("genericType.string -> " + genericType.toString());
			}
		}
	}

}
