package yyl.example.basic.runtime;

/**
 * 通过堆栈里获取的方式，判断main函数，找到原始启动的main函数
 */
public class DeduceMain {

	public static void main(String[] args) {
		System.out.println(deduceMainApplicationClass());
	}

	private static Class<?> deduceMainApplicationClass() {
		try {
			StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
			for (StackTraceElement stackTraceElement : stackTrace) {
				System.out.println(stackTraceElement);
				if ("main".equals(stackTraceElement.getMethodName())) {
					return Class.forName(stackTraceElement.getClassName());
				}
			}
		} catch (ClassNotFoundException ex) {
			// Swallow and continue
		}
		return null;
	}
}
