package yyl.example.demo.guava.collect;

import com.google.common.collect.HashMultimap;

public class HashMultimapTest {
	public static void main(String[] args) {
		HashMultimap<String, String> multimap = HashMultimap.create();
		multimap.put("key", "1");
		multimap.put("key", "2");
		multimap.put("key", "2");
		multimap.put("key", "3");
		multimap.remove("key", "3");
		System.out.println(multimap.asMap());
	}
}
