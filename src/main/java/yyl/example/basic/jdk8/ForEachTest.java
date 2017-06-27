package yyl.example.basic.jdk8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ForEachTest {
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

		list.forEach(new Consumer<Integer>() {
			@Override
			public void accept(Integer i) {
				System.out.print(i);
			}
		});
		System.out.println();

		list.forEach(i -> System.out.print(i));
		System.out.println();

		list.forEach(System.out::print);
		System.out.println();
	}
}
