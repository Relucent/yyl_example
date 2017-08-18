package yyl.example.exercise.functional;

/**
 * 函子是函数式编程里面最重要的数据类型，也是基本的运算单位和功能单位<br>
 * 函子的map方法接受函数f作为参数，然后返回一个新的函子，里面包含的值是被f处理过的(f(this.value))<br>
 * 一般约定，函子的标志就是容器具有map方法。该方法将容器里面的每一个值，映射到另一个容器。<br>
 */
public class Functor<T> {

	public final T value;

	public Functor(T value) {
		this.value = value;
	}

	public <R> Functor<R> map(Function<T, R> f) {
		return new Functor<R>(f.apply(this.value));
	}

	public static <T> Functor<T> of(T value) {
		return new Functor<T>(value);
	}
}
