package yyl.example.basic.syntax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** 方法重载的例子 01 */
public class PolymiorphismTest {

	public static void main(String[] args) {
		Collection<?> collection = new ArrayList<Object>();
		println((Collection<?>) collection);
		println((List<?>) collection);
		println((ArrayList<?>) collection);
	}

	public static void println(Collection<?> c) {
		System.out.println("collection.");
	}

	public static void println(List<?> c) {
		System.out.println("List");
	}

	public static void println(ArrayList<?> c) {
		System.out.println("ArrayList");
	}
}
