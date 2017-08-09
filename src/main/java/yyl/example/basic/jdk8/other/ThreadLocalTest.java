package yyl.example.basic.jdk8.other;

public class ThreadLocalTest {

	public static void main(String[] args) {
		ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "default");
		System.out.println(threadLocal.get());
	}

}
