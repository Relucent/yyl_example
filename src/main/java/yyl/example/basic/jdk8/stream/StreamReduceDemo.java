package yyl.example.basic.jdk8.stream;

import java.util.stream.IntStream;

public class StreamReduceDemo {
	public static void main(String[] args) {
		int count = IntStream.of(1, 2, 3, 4, 5)//
				.peek(System.out::println)//
				.reduce((left, right) -> left + right).getAsInt();
		System.out.println(count);
	}
}
