package yyl.example.basic.jdk8.stream;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * 通过实现 Supplier 接口，你可以自己来控制流的生成。这种情形通常用于随机数、常量的 Stream，或者需要前后元素间维持着某种状态信息的 Stream。<br>
 * 把 Supplier 实例传递给 Stream.generate() 生成的 Stream，默认是串行（相对 parallel 而言）但无序的（相对 ordered 而言）。<br>
 * 由于它是无限的，在管道中，必须利用 limit 之类的操作限制 Stream 大小。<br>
 */
public class StreamGenerateDemo {
	public static void main(String[] args) {
		Supplier<Integer> random = new Random()::nextInt;
		Stream.generate(random)//
				.limit(10)//如果不限制大小，那么流将是无限的
				.forEach(System.out::println);
	}
}
