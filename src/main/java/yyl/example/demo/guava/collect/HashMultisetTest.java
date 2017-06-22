package yyl.example.demo.guava.collect;

import java.util.List;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;

public class HashMultisetTest {

	public static void main(String[] args) {

		List<String> wordList = Lists.newArrayList("1", "2", "1", "1", "2");

		HashMultiset<String> multiSet = HashMultiset.create();
		multiSet.addAll(wordList);

		System.out.println(multiSet.count("1"));
		System.out.println(multiSet.count("2"));
		System.out.println(multiSet.count("3"));
	}

}
