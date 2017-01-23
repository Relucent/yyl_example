package yyl.example.basic.proxy.noclass;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class InterfaceProxyHandler implements InvocationHandler {
	private Map<String, Object> target = new HashMap<String, Object>();

	public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
		String methodName = method.getName();
		if (isSetter(method)) {
			target.put(m2f(methodName), args[0]);
		} else if (isGetter(method)) {
			return target.get(m2f(methodName));
		}
		return null;
	}
	public static final int SET_START = "set".length();
	public static final int SET_END = SET_START + 1;
	public static final int IS_START = "is".length();
	public static final int IS_END = IS_START + 1;

	public static Object getBeanProxy(Class<?> proxyInterface) {
		return Proxy.newProxyInstance(proxyInterface.getClassLoader(), new Class[] { proxyInterface },
				new InterfaceProxyHandler());
	}

	public static String m2f(String methodName) {
		if (methodName.startsWith("set") || methodName.startsWith("get")) {
			return methodName.substring(SET_START, SET_END).toLowerCase(Locale.CHINA) + methodName.substring(SET_END);
		} else if (methodName.startsWith("is")) {
			return methodName.substring(IS_START, IS_END).toLowerCase(Locale.CHINA) + methodName.substring(IS_END);
		} else {
			return null;
		}
	}
	public static boolean isSetter(Method method) {
		String name = method.getName();
		boolean hasOneParam = method.getParameterTypes().length == 1;
		boolean startsWithGet = (name.length() > SET_START) && name.startsWith("set");

		return startsWithGet && hasOneParam;
	}

	public static boolean isGetter(Method method) {
		String name = method.getName();
		boolean hasNoParam = method.getParameterTypes().length == 0;
		boolean startsWithGet = (name.length() > SET_START) && name.startsWith("get");
		boolean startsWithIs = (name.length() > IS_START) && name.startsWith("is");
		return hasNoParam && (startsWithGet || startsWithIs);
	}
}
