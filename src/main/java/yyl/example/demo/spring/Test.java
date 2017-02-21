package yyl.example.demo.spring;

import yyl.example.demo.spring.component.Component2;
import yyl.example.demo.spring.component.Component3;
import yyl.example.demo.spring.component.Component4;
import yyl.example.demo.spring.env.SpringEnv;

public class Test {

	// @Main:
	public static void main(String[] args) {
		SpringEnv env = SpringEnv.getInstance();

		Component2 component2 = env.getBean("component2");
		component2.method();

		Component3 component3 = env.getBean(Component3.class);
		System.out.println(component3.getFromCache("1"));
		System.out.println(component3.getFromCache("1"));

		Component4 component4 = env.getBean(Component4.class);
		component4.method();
	}
}
