package yyl.example.exercise.functional;

import java.util.function.Function;

/**
 * Either 函子内部有两个值：左值（Left）和右值（Right）。右值是正常情况下使用的值，左值是右值不存在时使用的默认值。<br>
 * Either 旨在保存一个 left 或 right 值（但从来都不会同时保存这两个值）。该数据结构被称为不相交并集。<br>
 */
public class Either<T> extends Functor<T> {

	public T left;
	public T right;

	public Either(T left, T right) {
		super(right == null ? left : right);
		this.left = left;
		this.right = right;
	}

	public <R> Either<R> map(Function<T, R> f) {
		return this.right != null ? //
				Either.<R> of((R) null, f.apply(this.right)) : //
				Either.<R> of(f.apply(this.left), (R) null);
	}

	public static <T> Either<T> of(T left, T right) {
		return new Either<T>(left, right);
	}
}