package yyl.example.demo.spring.dynamic;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.AnnotationScopeMetadataResolver;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.annotation.ScopeMetadataResolver;

/**
 * 动态注册Bean
 */
public class RegistryComponent implements BeanDefinitionRegistryPostProcessor {

	private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();
	private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		registerBean(registry, "sampleBean", InnerSampleBean.class);
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		//此处可以修改或者设置Bean的属性
		InnerSampleBean bean = beanFactory.getBean(InnerSampleBean.class);
		bean.value = "dynamic registered sample";
	}

	private void registerBean(BeanDefinitionRegistry registry, String name, Class<?> beanClass) {
		//Bean的定义
		AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(beanClass);

		//设置SCOPE
		ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(abd);
		abd.setScope(scopeMetadata.getScopeName());

		//获得Bean名称
		String beanName = (name != null ? name : this.beanNameGenerator.generateBeanName(abd, registry));

		//处理公共定义注释
		AnnotationConfigUtils.processCommonDefinitionAnnotations(abd);

		//注册Bean的定义
		BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, beanName);
		BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, registry);

	}

	public static class InnerSampleBean {
		public String value;

		@Override
		public String toString() {
			return super.toString() + "[value=" + value + "]";
		}
	}
}
