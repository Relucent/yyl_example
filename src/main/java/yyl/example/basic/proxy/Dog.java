package yyl.example.basic.proxy;

public class Dog implements Animal {
	public void info() {
		System.out.println("我是一只猎狗");
	}

	public void run() {
		System.out.println("我在奔跑");
	}
}