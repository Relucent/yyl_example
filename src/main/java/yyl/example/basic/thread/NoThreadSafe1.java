package yyl.example.basic.thread;

/**
 * 线程不安全测试
 */
public class NoThreadSafe1 {

	private static boolean flag = true;

	public static void main(String[] args) {
		Thread daemon = new Thread() {
			public void run() {
				while (true) {
					flag = !flag;
				}
			};
		};
		daemon.setDaemon(true);
		daemon.start();

		while (flag == true || flag == false) {
		}
		System.out.println(flag);
		System.out.println("Exit!");
	}
}
