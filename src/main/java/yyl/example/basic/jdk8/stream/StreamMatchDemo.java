package yyl.example.basic.jdk8.stream;

import java.util.stream.Stream;

/**
 * Stream 有三个 match 方法，从语义上说：<br>
 * allMatch：Stream 中全部元素符合传入的 predicate，返回 true<br>
 * anyMatch：Stream 中只要有一个元素符合传入的 predicate，返回 true<br>
 * noneMatch：Stream 中没有一个元素符合传入的 predicate，返回 true<br>
 * 它们都不是要遍历全部元素才能返回结果。例如 allMatch 只要一个元素不满足条件，就 skip 剩下的所有元素，返回 false。<br>
 */
public class StreamMatchDemo {
	public static void main(String[] args) {
		Stream.generate(Math::random).allMatch(v -> {
			System.out.println(v);
			return v > 0.1;
		});
	}
}
