package yyl.example.basic.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程不安全测试 (Worker1可能不会停止)
 */
public class NoThreadSafe2 {

	public static void main(String[] args) throws InterruptedException {
		Worker1 worker1 = new Worker1();
		Worker2 worker2 = new Worker2();
		Worker3 worker3 = new Worker3();
		Worker4 worker4 = new Worker4();
		Worker5 worker5 = new Worker5();

		worker1.start();
		worker2.start();
		worker3.start();
		worker4.start();
		worker5.start();

		Thread.sleep(1000);

		worker1.flag = false;
		worker2.flag = false;
		worker3.flag = false;
		worker4.flag = false;
		worker5.flag = false;

		Thread.sleep(5000);

		System.out.println("1 flag:" + worker1.flag);
		System.out.println("2 flag:" + worker2.flag);
		System.out.println("3 flag:" + worker3.flag);
		System.out.println("4 flag:" + worker4.flag);
		System.out.println("5 flag:" + worker5.flag);

		System.exit(0);
	}

	static class Worker1 extends Thread {
		boolean flag = true;

		public void run() {
			while (flag) {
				//这个操作不会造成内存屏障
				Thread.currentThread();
			}
			System.out.println("exit worker1");
		};
	}

	static class Worker2 extends Thread {
		volatile boolean flag = true;

		public void run() {
			//volatile 保证了可见性
			while (flag) {
			}
			System.out.println("exit worker2");
		};
	}

	static class Worker3 extends Thread {
		volatile boolean flag = true;

		public void run() {
			while (flag) {
				//内存屏障
				Thread.yield();
			}
			System.out.println("exit worker3");
		};
	}

	static class Worker4 extends Thread {
		volatile boolean flag = true;

		public void run() {
			while (flag) {
				//内存屏障
				synchronized (new byte[0]) {
				}
			}
			System.out.println("exit worker4");
		};
	}

	static class Worker5 extends Thread {
		volatile boolean flag = true;
		final Lock lock = new ReentrantLock();

		public void run() {
			while (flag) {
				//内存屏障
				lock.lock();
				try {
				} finally {
					lock.unlock();
				}
			}
			System.out.println("exit worker5");
		};
	}
}
