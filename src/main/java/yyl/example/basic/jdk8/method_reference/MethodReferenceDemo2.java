package yyl.example.basic.jdk8.method_reference;

/**
 * 方法引用提供了一个很有用的语义来直接访问类或者实例的已经存在的方法或者构造方法。<br>
 * 结合Lambda表达式，方法引用使语法结构紧凑简明。<br>
 */
public class MethodReferenceDemo2 {

	public static void main(String[] args) {
		Reference<Long> currentTimeMillis = System::currentTimeMillis;
		System.out.println(currentTimeMillis);
		System.out.println(currentTimeMillis.method());

		PrintlnReference println = System.out::println;
		println.method("Hello Method Reference");
	}

	static interface Reference<T> {
		T method();
	}

	static interface PrintlnReference {
		void method(Object value);
	}
}
