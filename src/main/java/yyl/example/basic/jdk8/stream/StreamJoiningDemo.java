package yyl.example.basic.jdk8.stream;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamJoiningDemo {
	public static void main(String[] args) {
		String[] input = { "A", "B", "C", "D", "E" };
		String result = Stream.of(input).collect(Collectors.joining(", ", "[", "]"));//
		System.out.println(result);
	}
}
