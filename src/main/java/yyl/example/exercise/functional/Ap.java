package yyl.example.exercise.functional;

public class Ap<T extends Function<U, R>, U, R> extends Functor<T> {

	public Ap(T value) {
		super(value);
	}

	//applicative
	public Functor<R> ap(Functor<U> functor) {
		return Functor.of(this.value.apply(functor.value));
	}

	public static <T extends Function<U, R>, U, R> Ap<T, U, R> of(T value) {
		return new Ap<T, U, R>(value);
	}
}
