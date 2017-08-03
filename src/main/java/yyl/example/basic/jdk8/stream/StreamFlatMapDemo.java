package yyl.example.basic.jdk8.stream;

import java.util.Arrays;
import java.util.List;

/**
 * Stream.flatMap 用于将 Stream 中的层级结构扁平化(就是将最底层元素抽出来放到一起)
 */
public class StreamFlatMapDemo {
	public static void main(String[] args) {

		List<List<Integer>> list = Arrays.asList(//
				Arrays.asList(1), //
				Arrays.asList(2, 3), //
				Arrays.asList(4, 5, 6));

		list.stream().forEach(System.out::println);

		list.stream().flatMap(children -> children.stream()).forEach(System.out::println);
	}
}
