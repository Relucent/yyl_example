package yyl.example.demo.spring.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 使用 @annotation(注解类型) 匹配当前执行方法持有指定注解的方法(注解类型必须是全限定类型名)<br>
 * 例如定义切入点表达式 execution(* com.sample.service.impl..*.*(..)) <br>
 */
@Aspect
@Component
public class Component5Aspect {

	public Component5Aspect() {
		System.out.println("Component5Aspect -> constructor");
	}

	@Before("@annotation(yyl.example.demo.spring.aspectj.annotation.BeforeSgin)")
	public void before(JoinPoint joinPoint) {
		System.out.println("@Before :" + joinPoint.getSignature());
	}

}
