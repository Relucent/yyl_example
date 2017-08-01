package yyl.example.basic.jdk8.optional;

import java.util.Optional;

public class OptionalTest {
	public static void main(String[] args) {
		Optional<String> optional1 = Optional.of("hello");
		Optional<String> optional2 = Optional.ofNullable(null);

		try {
			Optional.of(null);
		} catch (NullPointerException e) {
			System.out.println("Optional.of(null)->" + e);
		}

		System.out.println("optional1.isPresent()->" + optional1.isPresent());
		System.out.println("optional1.isPresent()->" + optional2.isPresent());

		System.out.println(optional1.get());
		System.out.println(optional2.orElse("@Default"));

	}
}
