package yyl.example.basic.jdk8.method_reference;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * 方法引用提供了一个很有用的语义来直接访问类或者实例的已经存在的方法或者构造方法。<br>
 * 结合Lambda表达式，方法引用使语法结构紧凑简明。<br>
 */
public class MethodReferenceDemo1 {

	public static void main(String[] args) {

		//构造方法引用，语法是：Class::new 造方法没有参数
		T o = T.create(T::new);

		List<T> list = Arrays.asList(o);

		//静态方法引，语法是：Class::static_method 方法只有T类型的一个参数
		list.forEach(T::method0);

		//类实例的方法引用，语法是：Class::method 方法没有参数
		list.forEach(T::method1);

		//引用类的方法，语法是：instance::method 方法只有T类型的一个参数
		list.forEach(o::method2);
	}

	private static class T {

		public static T create(final Supplier<T> supplier) {
			return supplier.get();
		}

		public static void method0(final T another) {
			System.out.println("T.method0");
		}

		public void method1() {
			System.out.println("T.method1");
		}

		public void method2(final T another) {
			System.out.println("T.method2");
		}
	}
}
