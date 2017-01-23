package yyl.example.demo.guice;

//@Singleton
public class Hello {

	/**
	 * Guice 目前不能处理 private 构造
	 */
	Hello() {
		System.out.println("Hello -> constructor");
	}

	public void method() {
		System.out.println("Hello -> invoke method");
	}
}
