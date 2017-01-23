package yyl.example.demo.spring.component;

public class Component2 {

	private Component1 component1;

	private Component2() {
		System.out.println("Component2 -> constructor");
	}

	public void method() {
		System.out.println("Component2 -> invoke method");
		component1.method();
	}

	// Spring-IoC
	public void setComponent1(Component1 component1) {
		System.out.println("Component2 -> inject Component1");
		this.component1 = component1;
	}
}
