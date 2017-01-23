package yyl.example.demo.guice;

import com.google.inject.Binder;
import com.google.inject.Module;

//控制模块
public class MyModule implements Module {
	public void configure(Binder binder) {
		binder.bind(Hello.class);
		binder.bind(HelloSingleton.class);
	}
}