package yyl.example.demo.spring.dynamic;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Spring框架有3个主要的Hook类，分别是：<br>
 * 1. org.springframework.context.ApplicationContextAware<br>
 * 它的setApplicationContext 方法将在Spring启动之前第一个被调用<br>
 * 2. org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor<br>
 * 它的postProcessBeanDefinitionRegistry 和 postProcessBeanFactory
 * 它们在Bean初始化创建之前启动，如果Spring的bean需要的其他第三方中的组件，我们在这里将其注入给Spring<br>
 * 3. org.springframework.context.ApplicationListener<br>
 * 用于在初始化完成后做一些事情，当Spring所有XML或元注解的Bean都启动被创建成功了，这时会调用它的唯一方法onApplicationEvent。<br>
 */

public class HookComponent implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor, ApplicationListener<ApplicationEvent> {

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println(":setApplicationContext");
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		System.out.println(":postProcessBeanDefinitionRegistry");
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println(":postProcessBeanFactory");
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		System.out.println(":onApplicationEvent " + event.getClass());
	}
}
