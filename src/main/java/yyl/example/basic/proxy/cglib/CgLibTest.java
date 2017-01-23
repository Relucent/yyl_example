package yyl.example.basic.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

public class CgLibTest {
	public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(HelloWorld.class);
		//设置回调方法实现类  
		enhancer.setCallback(new MethodInterceptorImpl());
		//实例化已经添加回调实现的HELLOWORLD实例  
		HelloWorld my = (HelloWorld) enhancer.create();
		int result = my.print();
		System.out.println(result);
	}
}
//cglib:cglib-nodep:jar:3.2.4