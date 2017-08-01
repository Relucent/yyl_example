package yyl.example.basic.jdk8.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 几种写法的对比，在语法层面对Java8的 lambda做一个初步了解
 */
public class LambdaStreamDemo {

	public static void main(String[] args) {

		List<String> values = Arrays.asList("A", "B", "C", "D", "E", "F", "G");

		//0
		for (int i = 0; i < values.size(); i++) {
			String value = values.get(i);
			value = value.toLowerCase();
			System.out.println(value);
		}

		//1
		for (String value : values) {
			System.out.println(value.toLowerCase());
		}

		//2
		values.stream().map(new Function<String, String>() {
			@Override
			public String apply(String value) {
				return value.toLowerCase();
			}
		}).forEach(new Consumer<String>() {
			@Override
			public void accept(String value) {
				System.out.print(value);
			}
		});

		//3
		values.stream().map((value) -> {
			return value.toLowerCase();
		}).forEach((value) -> {
			System.out.print(value);
		});

		//4
		values.stream().map((value) -> value.toLowerCase()).forEach((value) -> System.out.print(value));

		//5
		values.stream().map(value -> value.toLowerCase()).forEach(value -> System.out.print(value));

		//6
		values.stream().map(String::toLowerCase).forEach(System.out::print);
	}
}
