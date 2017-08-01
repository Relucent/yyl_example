package yyl.example.basic.jdk8.default_method;

/**
 * 如果一个类实现了多个接口，且这些接口有相同的默认方法，那么会出现冲突。<br>
 * 这时候可以创建自己的默认方法，来覆盖重写接口的默认方法，也可以可以使用 super 来调用指定接口的默认方法
 */
public class Implement2 implements Interface1, Interface2 {
	@Override
	public void defaultMethod() {
		Interface2.super.defaultMethod();
	}
}
