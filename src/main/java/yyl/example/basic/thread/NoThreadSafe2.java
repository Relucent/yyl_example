package yyl.example.basic.thread;

import java.util.concurrent.atomic.AtomicInteger;
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
		Worker6 worker6 = new Worker6();

		worker1.start();
		worker2.start();
		worker3.start();
		worker4.start();
		worker5.start();
		worker6.start();

		Thread.sleep(1000);

		worker1.flag = false;
		worker2.flag = false;
		worker3.flag = false;
		worker4.flag = false;
		worker5.flag = false;
		worker6.flag = false;

		Thread.sleep(5000);

		System.out.println("1 flag:" + worker1.flag);
		System.out.println("2 flag:" + worker2.flag);
		System.out.println("3 flag:" + worker3.flag);
		System.out.println("4 flag:" + worker4.flag);
		System.out.println("5 flag:" + worker5.flag);
		System.out.println("6 flag:" + worker6.flag);

		System.exit(0);
	}

	static class Worker1 extends Thread {
		boolean flag = true;

		public void run() {
			int i = 0;
			while (flag) {
				//这些操作不会造成内存屏障
				Thread.currentThread();
				i = i + 1;
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
		boolean flag = true;

		public void run() {
			while (flag) {
				//内存屏障
				Thread.yield();
			}
			System.out.println("exit worker3");
		};
	}

	static class Worker4 extends Thread {
		boolean flag = true;

		public void run() {
			while (flag) {
				//同步块会造成内存屏障
				synchronized (this) {
				}
			}
			System.out.println("exit worker4");
		};
	}

	static class Worker5 extends Thread {
		boolean flag = true;
		final Lock lock = new ReentrantLock();

		public void run() {
			while (flag) {
				//lock.lock 和 lock.unlock 都会造成内存屏障
				lock.lock();
				try {
				} finally {
					lock.unlock();
				}
			}
			System.out.println("exit worker5");
		};
	}

	static class Worker6 extends Thread {
		boolean flag = true;

		public void run() {
			AtomicInteger atomic = new AtomicInteger();
			while (flag) {
				//内存屏障
				//incrementAndGet方法中的unsafe.getAndAddInt会造成内存屏障
				atomic.incrementAndGet();
			}
			System.out.println("exit worker6");
		};
	}
}
