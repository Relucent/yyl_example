package yyl.example.basic.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示wait/notify的用法
 */
public class WaitNotifyTest1 {

	private static final Object LOCK = new Object();
	private static final AtomicInteger COUNT = new AtomicInteger(0);

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					synchronized (LOCK) {
						int i = COUNT.getAndIncrement();
						System.out.println(i + "\t" + "进入！");
						try {
							LOCK.wait();
							System.out.println(i + "\t" + "唤醒并执行完毕!");
							LOCK.notify();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
		Thread.sleep(1000);
		synchronized (LOCK) {
			LOCK.notify();
		}
	}
}
