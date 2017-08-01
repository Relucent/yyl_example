package yyl.example.basic.jdk8.default_method;

/**
 * 如果一个类实现了多个接口，且这些接口有相同的默认方法，那么会出现冲突。这时候可以创建自己的默认方法，来覆盖重写接口的默认方法
 */
public class Implement1 implements Interface1, Interface2 {

	@Override
	public void defaultMethod() {
		System.out.println("Impl1");
	}

}
