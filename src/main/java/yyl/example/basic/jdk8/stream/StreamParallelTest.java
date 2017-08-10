package yyl.example.basic.jdk8.stream;

import java.util.stream.Stream;

/**
 * parallelStream其实就是一个并行执行的流，它通过默认的ForkJoinPool,可能提高多线程任务的速度<br>
 * 因为是并发执行，所以默认的情况是无序的<br>
 */
public class StreamParallelTest {
	public static void main(String[] args) {
		Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).parallel().forEach(System.out::println);
	}
}
