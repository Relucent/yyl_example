package yyl.example.basic.classloader;

public class Clover extends Foliage {

	static {
		System.out.println("static");
	}
	public void hello() {
		System.out.println("hello world! (version one)");
	}
}
