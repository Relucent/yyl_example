package yyl.example.demo.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ConfigurationBean {

	@Bean(name = "singletonBean")
	/* @Scope("singleton") */
	public Object singletonBean() {
		System.out.println("create-singleton");
		return new Object();
	}

	@Bean(name = "multitonBean")
	@Scope("prototype")
	public Object multitonBean() {
		System.out.println("create-multiton");
		return new Object();
	}

	@Bean(name = "afterCreateBeanTest")
	public Object afterCreateBeanTest() throws Exception {

		//执行多次，返回的对象是相同的
		System.out.println("singletonBean() == singletonBean() -> " + (singletonBean() == singletonBean()));

		//执行多次，返回的对象是不同的
		System.out.println("multitonBean() == multitonBean() -> " + (multitonBean() == multitonBean()));

		//因为这是一个代理方法
		System.out.println(this.getClass().getMethod("multitonBean"));

		return new Object();
	}
}
