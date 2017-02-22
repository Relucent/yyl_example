package yyl.example.demo.spring.component;

import org.springframework.stereotype.Component;

import yyl.example.demo.spring.aspectj.annotation.BeforeSgin;

@Component
public class Component5 {

	//如果需要使用Aspectj_AOP，组件的构造函数必须是可见的(不能是private)
	protected Component5() {
		System.out.println("Component5 -> constructor");
	}

	@BeforeSgin
	public int method() {
		System.out.println("Component5 -> invoke method");
		return 0;
	}
}
