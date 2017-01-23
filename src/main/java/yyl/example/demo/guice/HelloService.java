package yyl.example.demo.guice;

import com.google.inject.Inject;

public class HelloService {
	@Inject
	private Hello hello;

	public void method() {
		System.out.println(hello);//这只是一般的注入
		hello.method();
	}
}
