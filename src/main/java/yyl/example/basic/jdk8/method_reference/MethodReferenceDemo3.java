package yyl.example.basic.jdk8.method_reference;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/**
 * 引用构造函数 <br>
 * 不使用通常的手动实现工厂类，通过使用构造函数将所有的工作联合在一起 <br>
 * 通过Person::new创建一个指向Person构造函数的引用。 <br>
 * java编译器自动的选择正确的构造函数来匹配PersonFactory.create的函数签名<br>
 */
public class MethodReferenceDemo3 {

	public static void main(String[] args) {
		IntFunction<Object[]> function = Object[]::new;
		Object[] array = function.apply(0);
		System.out.println(Arrays.toString(array));

		Supplier<Person> constructor1 = Person::new;
		Function<String, Person> constructor2 = Person::new;

		System.out.println(constructor1.get().name);
		System.out.println(constructor2.apply("reference").name);
	}

	static class Person {

		final String name;

		Person() {
			this("anonymous");
		}

		Person(String name) {
			this.name = name;
		}
	}

}
