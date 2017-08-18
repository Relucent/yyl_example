package yyl.example.exercise.functional;

/**
 * Monad 函子的作用是，总是返回一个单层的函子。<br>
 */
public class Monad<T> extends Functor<T> {

	public Monad(T value) {
		super(value);
	}

	public <R extends Functor<I>, I> R flatMapMap(Function<T, R> f) {
		return this.map(f).value;
	}

	public static <T> Monad<T> of(T value) {
		return new Monad<T>(value);
	}
}
