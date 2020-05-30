package yyl.example.basic.jdk8.stream;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 通过 Collectors.groupingBy工厂方法返回的收集器就可以轻松的完成分组<br>
 */
public class StreamGroupingByDemo {
	public static void main(String[] args) {
		Item[] items = { //
				create("A", 1), create("A", 2), create("A", 3), //
				create("B", 4), create("B", 5), create("B", 6), //
				create("C", 7), create("C", 8), create("C", 9), //
		};
		Map<String, List<Item>> group = Stream.of(items).parallel().collect(Collectors.groupingBy(item -> item.name));
		System.out.println(group);

		Map<String, Long> counts = Stream.of(items).parallel().collect(Collectors.groupingBy(item -> item.name, Collectors.counting()));
		System.out.println(counts);
	}

	private static Item create(String name, Integer value) {
		Item item = new Item();
		item.name = name;
		item.value = value;
		return item;
	}

	private static class Item {
		private String name;
		private Integer value;

		@Override
		public String toString() {
			return "{name=" + name + ", value=" + value + "}";
		}
	}
}
