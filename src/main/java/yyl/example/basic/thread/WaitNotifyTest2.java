package yyl.example.basic.thread;

/**
 * 生产者消费者模式<br>
 * 自定义的的阻塞队列(这里是为了演示wait/notify的用法，实际上有更好的选择是直接用java.util.concurrent.BlockingQueue)
 */
public class WaitNotifyTest2 {

	public static void main(String[] args) throws InterruptedException {
		final Object lock = new Object();
		new Thread() {
			public void run() {
				try {
					while (true) {
						synchronized (lock) {
							lock.notify();
							System.out.println("1");
							lock.wait();
						}
					}
				} catch (InterruptedException e) {
				}
			};
		}.start();
		new Thread() {
			public void run() {
				try {
					while (true) {
						synchronized (lock) {
							lock.notify();
							System.out.println("2");
							lock.wait();
						}
					}
				} catch (InterruptedException e) {
				}
			};
		}.start();
	}
}
