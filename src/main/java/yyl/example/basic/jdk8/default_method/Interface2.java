package yyl.example.basic.jdk8.default_method;

public interface Interface2 {
	default void defaultMethod() {
		System.out.println("Interface2:defaultMethod");
	}
}