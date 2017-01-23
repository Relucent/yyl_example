package yyl.example.basic.proxy;

import java.lang.reflect.Proxy;

public class MyProxyFactory {
	public static Object getProxy(Object object) {
		ProxyHandler handler = new ProxyHandler();
		handler.setTarget(object);
		return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), handler);

	}

	public static void main(String[] args) {

		Dog dog = new Dog();

		Animal animal = (Animal) getProxy(dog);

		animal.info();
		animal.run();

		Proxy proxy = (Proxy) animal;
		Class<?> proxyClass = proxy.getClass();
		System.out.println(proxyClass.getName());
		//getInterfaces() 
		for (Class<?> clz : proxyClass.getInterfaces()) {
			System.out.println("+" + clz.getName());
		}

		System.out.println("isProxyClass:" + Proxy.isProxyClass(proxyClass));
		System.out.println("isProxyClass:" + Proxy.isProxyClass(dog.getClass()));

		ProxyHandler handler = (ProxyHandler) Proxy.getInvocationHandler(animal);
		System.out.println(handler.getTarget());

	}
}
