package yyl.example.demo.spring.component;

public class Component1 {

	private Component1() {
		System.out.println("Component1 -> constructor");
	}

	public void method() {
		System.out.println("Component1 -> invoke method");
	}
}
