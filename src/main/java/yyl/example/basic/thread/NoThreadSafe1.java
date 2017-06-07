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

		// (flag == true || flag == false) 不是原子操作
		// 这个语句中，其实是取了两次flag，分别判断其取值
		while (flag == true || flag == false) {
		}
		System.out.println(flag);
		System.out.println("Exit!");
	}
}
