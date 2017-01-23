package yyl.example.demo.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Google_Guice 是一个轻量级的DI(Dependency Injection依赖注入)框架
 */
public class Test {
	public static void main(String[] args) {
		HelloService service = new HelloService();
		Injector in = Guice.createInjector(new MyModule()); // 控制注入
		in.injectMembers(service); // 注入的成员对象
		service.method();

		// 默认不是单例
		Hello hello1 = in.getInstance(Hello.class);
		Hello hello2 = in.getInstance(Hello.class);
		System.out.println(hello1 == hello2);

		//加上@Singleton注解，单例的Bean
		HelloSingleton singleton1 = in.getInstance(HelloSingleton.class);
		HelloSingleton singleton2 = in.getInstance(HelloSingleton.class);
		System.out.println(singleton1 == singleton2);
	}
}
