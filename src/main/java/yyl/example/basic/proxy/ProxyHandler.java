package yyl.example.basic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {
	private Object target;

	public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
		Object result = null;
		if (method.getName().equals("info")) {
			System.out.println("======开始事务...");
			result = method.invoke(target, args);
			System.out.println("======提交事务...");
		} else {
			result = method.invoke(target, args);
		}
		return result;
	}

	//通过该方法，设置目标对象
	public void setTarget(Object target) {
		this.target = target;
	}
	//通过该方法，设置目标对象
	public Object getTarget() {
		return target ;
	}
}
