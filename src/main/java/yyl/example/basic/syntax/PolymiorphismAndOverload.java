package yyl.example.basic.syntax;

/**
 * 这个例子展示了多态与重载<br>
 * 多态(Polymorphism)按字面的意思就是“多种状态”,在面向对象语言中，接口的多种不同的实现方式即为多态。<br>
 * 多态性是允许你将父对象设置成为一个或更多的他的子对象相等的技术，赋值之后，父对象就可以根据当前赋值给它的子对象的特性以不同的方式运作。<br>
 * 简单的说：多态===晚绑定 | 多态≈Override<br>
 * (在C++中多态性是通过虚函数实现的，而JAVA的函数可以认为都是虚的)。 <br>
 * 重载(overload)，就是函数或者方法有相同的名称，但是参数列表不相同的情形。<br>
 * 同名不同参数的函数或者方法之间，互相称之为重载函数或者方法。<br>
 */
public class PolymiorphismAndOverload {

	public static void main(String[] args) {
		C c;
		B b;
		A a;
		a = b = c = new C();
		a(a);
		a(b);
		a(c);
	}

	//重载
	public static void a(A a) {
		a.p();
		System.out.println("-A");
	}

	//重载
	public static void a(B b) {
		b.p();
		System.out.println("-B");
	}

	//重载
	public static void a(C c) {
		c.p();
		System.out.println("-C");
	}
}

class A {
	//原方法
	public void p() {
		System.out.println("A");
	}
}

class B extends A {
	//覆盖
	public void p() {
		System.out.println("B");
	}
}

class C extends B {
	//覆盖
	public void p() {
		System.out.println("C");
	}
}
