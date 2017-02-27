package yyl.example.basic.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

public class EnumReflectTest {

	public static void main(String[] args) throws Exception {
		Method method = E.class.getMethod("values");
		Object array = method.invoke(null);
		for (int index = 0, length = Array.getLength(array); index < length; index++) {
			System.out.println(Array.get(array, index));
		}
	}

	enum E {
		A, B, C, D, E
	}

}
