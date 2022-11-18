package yyl.example.demo.jol;

import org.openjdk.jol.vm.VM;

/**
 * 要在 JVM 中查找特定对象的内存地址，可以使用 addressOf() 方法
 */
public class JolMemoryExample {
	public static void main(String[] args) {
		String answer = "hello world";
		System.out.println("The memory address is " + VM.current().addressOf(answer));
		System.out.println("The memory address is " + VM.current().addressOf("hello world"));
	}
}
