package yyl.example.demo.spring.env;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringEnvironment {

	// @Fields:
	private static volatile SpringEnvironment INSTANCE;
	private final ConfigurableApplicationContext applicationcontext;

	// @Constructors:
	public SpringEnvironment() {
		applicationcontext = new ClassPathXmlApplicationContext("classpath:yyl/example/demo/spring/config/applicationContext.xml");
		applicationcontext.start();
	}

	public static SpringEnvironment getInstance() {
		if (INSTANCE == null) {
			synchronized (SpringEnvironment.class) {
				if (INSTANCE == null) {
					INSTANCE = new SpringEnvironment();
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

	public void registerSingleton(String beanName, Object singletonObject) {
		applicationcontext.getBeanFactory().registerSingleton(beanName, singletonObject);
	}
}
