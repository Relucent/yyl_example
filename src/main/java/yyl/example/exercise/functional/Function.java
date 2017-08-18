package yyl.example.exercise.functional;

@FunctionalInterface
public interface Function<T, R> {
	R apply(T t);
}
