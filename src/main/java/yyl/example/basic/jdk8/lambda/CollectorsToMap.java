package yyl.example.basic.jdk8.lambda;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 使用lambda将list转为map
 */
public class CollectorsToMap {
	public static void main(String[] args) {
		A[] array = { new A("A", new String[] { "a1", "a2", "a3" }), //
				new A("B", new String[] { "b1", "b2" }), //
				new A("A", new String[] { "a4", "a5" }), //
				new A("B", new String[] { "b1", "b3" }), //
				new A("C", new String[] { "c1", null }) };

		Map<String, HashSet<String>> result = Arrays.stream(array).collect(Collectors.groupingBy(a -> a.name, //
				Collector.of(HashSet::new, (set, item) -> {
					set.addAll(Arrays.stream(item.value).filter(Objects::nonNull).collect(Collectors.toSet()));
				}, (left, right) -> left)));
		System.out.println(result);
	}

	private static class A {
		public String name;
		public String[] value;

		public A(String name, String[] value) {
			this.name = name;
			this.value = value;
		}
	}

	// public static<T, R> Collector<T, R, R> of(Supplier<R> supplier,
	// BiConsumer<R, T> accumulator,
	// BinaryOperator<R> combiner,
	// Characteristics... characteristics) {
	// Objects.requireNonNull(supplier);
}
