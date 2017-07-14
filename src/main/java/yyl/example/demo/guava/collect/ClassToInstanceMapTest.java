package yyl.example.demo.guava.collect;

import java.io.Serializable;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;

/**
 * ClassToInstanceMap是一种特殊的Map：它的键是类型，而值是符合键所指类型的对象。
 */
public class ClassToInstanceMapTest {
	public static void main(String[] args) {
		ClassToInstanceMap<Serializable> map = MutableClassToInstanceMap.create();
		map.putInstance(String.class, "String");
		map.putInstance(Integer.class, 1);
		map.putInstance(Boolean.class, Boolean.TRUE);

		String value1 = map.getInstance(String.class);
		System.out.println(value1);

		Integer value2 = map.getInstance(Integer.class);
		System.out.println(value2);

		Boolean value3 = map.getInstance(Boolean.class);
		System.out.println(value3);
	}
}
