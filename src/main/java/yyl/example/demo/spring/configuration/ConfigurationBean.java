package yyl.example.demo.spring.configuration;

import java.lang.reflect.Method;

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

		Object bean1 = myBean();
		Object bean2 = myBean();

		System.out.println("myBean() == myBean() -> " + (bean1 == bean2));

		//因为这是一个代理方法
		Method method = this.getClass().getMethod("myBean");
		System.out.println(method);

		return new Object();
	}
}
