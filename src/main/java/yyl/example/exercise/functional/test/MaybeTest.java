package yyl.example.exercise.functional.test;

import yyl.example.exercise.functional.Functor;
import yyl.example.exercise.functional.Maybe;

public class MaybeTest {

	public static void main(String[] args) {

		try {
			Maybe.<String> of(null).map(v -> v.toUpperCase());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Functor.<String> of(null).map(v -> v.toUpperCase());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
