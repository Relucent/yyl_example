package yyl.example.demo.guice;

import com.google.inject.Singleton;

@Singleton
public class HelloSingleton {

	/**
	 * Guice 目前不能处理 private 构造
	 */
	HelloSingleton() {
		System.out.println("HelloSingleton -> constructor");
	}

	public void method() {
		System.out.println("HelloSingleton -> invoke method");
	}
}
