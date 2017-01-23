package yyl.example.basic.throwable;

public class StackTraceElementTest {
	public static void main(String[] args) {
		method_0();
	}

	public static void method_0() {
		method_1();
	}

	public static void method_1() {
		method_2();
	}

	public static void method_2() {
		printStackTrace();
	}

	private static void printStackTrace() {
		StackTraceElement[] array = new Throwable().getStackTrace();
		System.out.println(array.length);
		for (StackTraceElement e : array) {
			System.out.println("----------------------------------------");
			System.out.println("class->" + e.getClassName());
			System.out.println("file->" + e.getFileName());
			System.out.println("method->" + e.getMethodName());
			System.out.println("line->" + e.getLineNumber());
		}
	}
}
