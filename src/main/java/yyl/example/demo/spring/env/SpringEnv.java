package yyl.example.demo.spring.env;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringEnv {

	// @Fields:
	private static volatile SpringEnv INSTANCE;
	private final ConfigurableApplicationContext applicationcontext;

	// @Constructors:
	public SpringEnv() {
		applicationcontext = new ClassPathXmlApplicationContext("classpath:yyl/example/demo/spring/config/applicationContext.xml");
		applicationcontext.start();
	}

	public static SpringEnv getInstance() {
		if (INSTANCE == null) {
			synchronized (SpringEnv.class) {
				if (INSTANCE == null) {
					INSTANCE = new SpringEnv();
				}
			}
		}
		return INSTANCE;
	}

	// @Methods:
	public void stop() {
		applicationcontext.stop();
	}

	public <T> T getBean(Class<T> type) {
		return applicationcontext.getBean(type);
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(String beanName) {
		return (T) applicationcontext.getBean(beanName);
	}

}
