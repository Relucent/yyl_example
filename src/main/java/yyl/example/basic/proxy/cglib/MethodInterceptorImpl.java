package yyl.example.basic.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class MethodInterceptorImpl implements MethodInterceptor {
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println(method);
		Object o = proxy.invokeSuper(obj, args);
		return o;
	}
}
