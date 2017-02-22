package yyl.example.demo.spring.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 基于Annotation方式使用AOP的例子<br>
 * <br>
 * # execution 表达式说明：<br>
 * 例如定义切入点表达式 execution(* com.sample.service.impl..*.*(..)) <br>
 * 1、execution(): 表达式主体<br>
 * 2、第一个*号：表示返回类型，*号表示所有的类型<br>
 * 3、包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，com.sample.service.impl包、子孙包下所有类的方法<br>
 * 4、第二个*号：表示类名，*号表示所有的类<br>
 * 5、*(..):最后这个星号表示方法名，*号表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数<br>
 * <br>
 * # 多个表达式的组合：<br>
 * AspectJ使用 且 && 、或 || 、非! 来组合切入点表达式<br>
 * 在Schema风格下，由于在XML中使用“&&”需要使用转义字符“&amp;&amp;”来代替，因此Spring ASP 提供了and、or、not来代替&&、||、! <br>
 * <br>
 * # AspectJ类型匹配的通配符：<br>
 * 1、星号 * 匹配任何数量字符<br>
 * 2、两点 .. 匹配任何数量字符的重复，如在类型模式中匹配任何数量子包；而在方法参数模式中匹配任何数量参数<br>
 * 3、加号 + 匹配指定类型的子类型；仅能作为后缀放在类型模式后边<br>
 */
@Aspect
@Component
public class Component4Aspect {

	public Component4Aspect() {
		System.out.println("Component4Aspect -> constructor");
	}

	/**
	 * 切入点配置 (一个带@Pointcut注解的空方法,一般不会被直接调用)<br>
	 * 备注：pointcut方法不是必须的, 只是为了减少配置(多个方法使用一个切入点表达式的时候)<br>
	 */
	@Pointcut("execution(* yyl.example.demo.spring.component.Component4.method(..))")
	void pointcut() {
	}

	//表达式，可以写切入点的名称
	@Before("pointcut()")
	//@Before("execution(* yyl.example.demo.spring.component.Component4.method(..))")
	public void before(JoinPoint joinPoint) {
		System.out.println("@Before :" + joinPoint.getSignature());
	}

	@Around("pointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("@Around#Before");
		Object result = joinPoint.proceed();
		System.out.println("@Around#After");
		return result;
	}

	@After("pointcut()")
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
