package yyl.example.exercise.functional;

/**
 * Maybe 函子就是为了解决了容器内部的值为NULL的问题，它的map方法里面设置了空值检查。
 */
public class Maybe<T> extends Functor<T> {

	public Maybe(T value) {
		super(value);
	}

	public <R> Maybe<R> map(Function<T, R> f) {
		return this.value == null ? Maybe.of(null) : Maybe.of(f.apply(this.value));
	}

	public static <T> Maybe<T> of(T value) {
		return new Maybe<T>(value);
	}
}
