package yyl.example.basic.thread;

/**
 * thread.Join把指定的线程加入到当前线程，可以将两个交替执行的线程合并为顺序执行的线程。<br>
 * 比如在线程B中调用了线程A的Join()方法，直到线程A执行完毕后，才会继续执行线程B。<br>
 */
public class JoinTest1 {
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(() -> {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
				}
			});
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
			}
			System.out.println(i + "->" + System.currentTimeMillis());
		}
	}
}
