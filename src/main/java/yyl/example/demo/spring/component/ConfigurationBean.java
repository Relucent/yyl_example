package yyl.example.demo.spring.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationBean {

	@Bean(name = "myBean")
	public Object myBean() {
		System.out.println("create-bean1");
		return new Object();
	}

	@Bean(name = "myBeanTest")
	public Object myBeanTest() throws Exception {

		//执行多次，返回的对象是相同的
		System.out.println("myBean1() == myBean1() -> " + (myBean() == myBean()));

		//因为这是一个代理方法
		System.out.println(getClass().getMethod("myBean"));

		return new Object();
	}
}
