package yyl.example.basic.reflect;

/**
 * 参数：<br>
 * name - 所需类的完全限定名 ：<br>
 * initialize - 是否必须初始化类 ：<br>
 * loader - 用于加载类的类加载器
 */
public class ClassForNameTest {
	public static void main(String[] args) throws Exception {
		Class<?> clazz = Class.forName("yyl.example.basic.reflect.ClassForNameTest$A", //
				false, //这个参数为false，Class不会初始化，所以A类 print 方法不会被执行
				ClassLoader.getSystemClassLoader());
		System.out.println(clazz.getName());
	}

	static class A {
		static {
			System.out.println("initialize");
		}
	}
}
