package yyl.example.basic.jdk8.default_method;

public interface Interface1 {
	default void defaultMethod() {
		System.out.println("Interface1:defaultMethod");
	}

	//静态默认方法
	static void staticMethod() {
		System.out.println("Interface1:staticMethod");
	}
}