package yyl.example.exercise.functional.test;

import yyl.example.exercise.functional.Functor;

public class FunctorTest {

	public static void main(String[] args) {
		// 1 + 1
		Functor.of(1).map(v -> v + 1);

		// string.toUpperCase();
		Functor.of("functional programming").map(v -> v.toUpperCase());
	}
}
