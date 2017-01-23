package yyl.example.basic.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 获得字段和方法的泛型信息
 */
public class GenericTest {
	public List<String> list = new LinkedList<String>();
	public List<?> list2 = new LinkedList<Object>();

	public List<String> getList() {
		return list;
	}

	public static void main(String[] args) throws SecurityException, NoSuchFieldException, NoSuchMethodException {
		ParameterizedType pt = (ParameterizedType) GenericTest.class.getField("list").getGenericType();
		printlnTypeInfo(pt.getActualTypeArguments()[0]);

		ParameterizedType pt2 = (ParameterizedType) GenericTest.class.getField("list2").getGenericType();
		printlnTypeInfo(pt2.getActualTypeArguments()[0]);

		System.out.println(GenericTest.class.getMethod("getList").toGenericString());
	}

	private static void printlnTypeInfo(Type type) {
		System.out.println("type.class -> " + type.getClass());
		System.out.println("type.string -> " + type.toString());
	}

}
