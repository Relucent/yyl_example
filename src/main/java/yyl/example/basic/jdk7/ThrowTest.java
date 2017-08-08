package yyl.example.basic.jdk7;

//重抛异常
public class ThrowTest {

	public static void main(String[] args) {
		rethrowException("error!");
	}

	public static void rethrowException(String msg) throws RuntimeException {
		try {
			throw new RuntimeException(msg);
		} catch (Exception e) {
			throw e;
		}
	}
}
