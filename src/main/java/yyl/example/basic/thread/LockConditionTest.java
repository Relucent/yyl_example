package yyl.example.basic.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * await() 造成当前线程在接到信号或被中断之前一直处于等待状态 <br>
 * signal() 唤醒一个等待线程 <br>
 */
public class LockConditionTest {

	public static void main(String[] args) {

		int workCount = 3;
		Lock lock = new ReentrantLock();
		Condition[] conditions = new Condition[workCount];
		for (int i = 0; i < workCount; i++) {
			conditions[i] = lock.newCondition();
		}

		for (int i = 0; i < workCount; i++) {
			new Worker(lock, conditions, i).start();
		}
	}

	static class Worker extends Thread {
		private final Lock lock;
		private final Condition[] conditions;
		private final int index;

		public Worker(Lock lock, Condition[] conditions, int index) {
			this.lock = lock;
			this.conditions = conditions;
			this.index = index;
		}

		@Override
		public void run() {
			Condition self = conditions[index];
			Condition next = conditions[(index + 1) % conditions.length];
			try {
				while (true) {
					lock.lock();
					try {
						System.out.println(index);
						if (next != self) {
							next.signal();
							self.await();
						}
					} finally {
						lock.unlock();
					}
				}
			} catch (InterruptedException e) {
			}
		}
	}

}
