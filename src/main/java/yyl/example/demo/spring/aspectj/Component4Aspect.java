package yyl.example.demo.spring.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 基于Annotation方式使用AOP的例子
 */
@Aspect
@Component
public class Component4Aspect {

	public Component4Aspect() {
		System.out.println("Component1Aspect -> constructor");
	}

	@Before("execution(* yyl.example.demo.spring.component.Component4.method(..))")
	public void before(JoinPoint joinPoint) {
		System.out.println("@Before :" + joinPoint.getSignature());
	}

	@Around("execution(* yyl.example.demo.spring.component.Component4.method(..))")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("@Around#Before");
		Object result = joinPoint.proceed();
		System.out.println("@Around#After");
		return result;
	}

	@After("execution(* yyl.example.demo.spring.component.Component4.method(..))")
	public void after(JoinPoint joinPoint) {
		System.out.println("@After:" + joinPoint.getSignature());
	}

	@AfterThrowing(value = "execution(* yyl.example.demo.spring.component.Component4.method(..))", throwing = "error")
	public void afterThrowing(Exception error) {
		System.out.println("@AfterThrowing: " + error.getMessage());
	}

	@AfterReturning(value = "execution(* yyl.example.demo.spring.component.Component4.method(..))", returning = "result")
	public void afterReturning(Object result) {
		System.out.println("@AfterReturning: result->" + result);
	}
}
