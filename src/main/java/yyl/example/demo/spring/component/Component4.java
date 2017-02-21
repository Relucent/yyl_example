package yyl.example.demo.spring.component;

import org.springframework.stereotype.Component;

@Component
public class Component4 {

	//如果需要使用Aspectj_AOP，组件的构造函数必须是可见的(不能是private)
	protected Component4() {
		System.out.println("Component4 -> constructor");
	}

	public int method() {
		System.out.println("Component4 -> invoke method");
		return 0;
	}
}
