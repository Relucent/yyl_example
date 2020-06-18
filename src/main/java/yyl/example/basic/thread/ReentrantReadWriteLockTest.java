package yyl.example.basic.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * ReentrantReadWriteLock锁是一个读写分离的锁，这种锁主要用于读多写少的业务场景，口诀就是：读读共享、谢谢互斥、读写互斥。
 */
public class ReentrantReadWriteLockTest {

	public static void main(String[] args) {

		ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
		ReadLock readLock = rwLock.readLock();
		WriteLock writeLock = rwLock.writeLock();
		new Worker("R1", readLock).start();
		new Worker("R2", readLock).start();
		new Worker("R3", readLock).start();
		new Worker("W1", writeLock).start();
		new Worker("W2", writeLock).start();
		new Worker("W3", writeLock).start();
	}

	private static class Worker extends Thread {

		private final Lock lock;

		private Worker(String name, Lock lock) {
			super(name);
			this.lock = lock;
		}

		public void run() {
			try {
				lock.lock();
				System.out.println("Enter:" + getName() + ":" + (System.currentTimeMillis() / 1000));
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("Leave:" + getName() + ":" + (System.currentTimeMillis() / 1000));
				lock.unlock();
			}
		}
	}
}
