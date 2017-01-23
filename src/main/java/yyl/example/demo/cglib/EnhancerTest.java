package yyl.example.demo.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class EnhancerTest {
	public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(null);
		enhancer.setInterfaces(new Class[] { I.class });
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

				if (Object.class.equals(method.getDeclaringClass())) {
					return methodProxy.invokeSuper(o, args);
				}

				System.out.println("->" + method.getName());

				return null;
			}
		});
		I a = I.class.cast(enhancer.create());
		a.method();
	}

	static interface I {
		void method();
	}

}
