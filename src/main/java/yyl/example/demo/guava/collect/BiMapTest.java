package yyl.example.demo.guava.collect;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/** 双向 Map */
public class BiMapTest {

	public static void main(String[] args) {
		BiMap<String, String> map1 = HashBiMap.create();
		map1.put("k1", "v1");
		map1.put("k2", "v2");
		map1.put("k3", "v3");

		BiMap<String, String> map2 = map1.inverse();//key-value对调

		System.out.println(map1.get("k1"));
		System.out.println(map2.get("v1"));

		System.out.println(map2.inverse() == map1);

		map1.put("k0", "v1");//throw Exception
	}
}
