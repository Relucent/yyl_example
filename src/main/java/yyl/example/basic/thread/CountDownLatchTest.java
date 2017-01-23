package yyl.example.basic.thread;

import java.util.concurrent.CountDownLatch;

/**
 * 锁存器 <br>
 * CountDownLatch类是一个同步计数器,构造时传入int参数,该参数就是计数器的初始值<br>
 * 每调用一次countDown()方法，计数器减1,计数器大于0 时，await()方法会阻塞线程继续执行<br>
 * 利用这种特性，可以让主线程等待子线程的结束。
 */
public class CountDownLatchTest {

	public static void main(String[] args) {

		System.out.println("主线程 -> 开始");

		int count = 5;

		// 初始化countDown
		CountDownLatch latch = new CountDownLatch(count);

		// 开count个线程
		for (int i = 0; i < count; i++) {
			new Worker(latch, i).start();
		}

		try {
			// 等待所有子线程执行完
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("主线程 -> 结束");
	}

	static class Worker extends Thread {
		private CountDownLatch latch;
		private int index;

		public Worker(CountDownLatch latch, int index) {
			this.latch = latch;
			this.index = index;
		}

		@Override
		public void run() {
			System.out.println("子线程" + index + "开始");
			latch.countDown();// 线程结束时计数器减1
			System.out.println("子线程" + index + "结束. 还有" + latch.getCount() + "个子线程未结束");
		}
	}

}
