package yyl.example.basic.proxy.target;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

/**
 * java.lang.reflect.Proxy 类有一个字段 h, 存储了代理类的调用处理类(InvocationHandler)<br>
 * 我们可以用这个特点，找到代理类的原始代理类<br>
 */
public class ProxyTargetTest {

	public static void main(String[] args) {
		BeanImpl target = new BeanImpl();
		Bean proxy1 = wrap(Bean.class, target);//代理1次
		Bean proxy2 = wrap(Bean.class, proxy1);//代理2次

		Invocation invocation2 = getFieldValue(proxy2, "h");
		Invocation invocation1 = getFieldValue(proxy1, "h");

		System.out.println(invocation2.target == proxy1);
		System.out.println(invocation1.target == target);
	}

	@SuppressWarnings("unchecked")
	public static <T> T wrap(Class<T> proxyInterface, Object target) {
		return (T) Proxy.newProxyInstance(proxyInterface.getClassLoader(), new Class[] { proxyInterface }, new Invocation(target));
	}

	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Object o, String fieldName) {
		try {
			Field field = getDeclaredField(o.getClass(), "h");
			field.setAccessible(true);
			return (T) field.get(o);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Field getDeclaredField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException ex) {
				// Field不在当前类定义,继续向上转型
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + fieldName);
	}

	public static void printlnFieldValue(Bean bean) {
		try {
			for (Class<?> type : getAllInterfaces(bean.getClass())) {
				System.out.println(type);
				for (Field field : type.getDeclaredFields()) {
					field.setAccessible(true);
					System.out.println(field + ">" + field.get(bean));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Class<?>[] getAllInterfaces(Class<?> type) {
		Set<Class<?>> interfaces = new HashSet<Class<?>>();
		while (type != null) {
			//			for (Class<?> c : type.getInterfaces()) {
			interfaces.add(type);
			//			}
			type = type.getSuperclass();
		}
		return interfaces.toArray(new Class<?>[interfaces.size()]);
	}

	public static class Invocation implements InvocationHandler {
		private Object target;

		public Invocation(Object target) {
			this.target = target;
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
			return method.invoke(target, args);
		}
	}

	public static interface Bean {
		public String getValue();

		public void setValue(String id);
	}

	public static class BeanImpl implements Bean {

		private String value;

		@Override
		public String getValue() {
			return value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}

	}
}
