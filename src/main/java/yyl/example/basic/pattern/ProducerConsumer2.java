package yyl.example.basic.pattern;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 生产者消费者模式<br>
 * 自定义的的阻塞队列(这里是为了演示wait/notify的用法，实际上有更好的选择是直接用java.util.concurrent.BlockingQueue)
 */
public class ProducerConsumer2 {

	public static void main(String[] args) throws InterruptedException {

		MyQueue queue = new MyQueue(1);
		new Producer("p1", queue).start();
		new Producer("p2", queue).start();
		new Consumer("c1", queue).start();
		new Consumer("c2", queue).start();

	}

	// 生产者
	static class Producer extends Thread {
		final String name;
		final MyQueue queue;

		Producer(String name, MyQueue queue) {
			this.name = name;
			this.queue = queue;
		}

		@Override
		public void run() {
			while (true) {
				try {
					queue.put(name + ":" + System.currentTimeMillis());
					Thread.sleep((int) (Math.random() * 1000));
				} catch (InterruptedException error) {
					break;
				}
			}
		}
	}

	// 消费者
	static class Consumer extends Thread {

		final MyQueue queue;
		final String name;

		Consumer(String name, MyQueue queue) {
			this.name = name;
			this.queue = queue;
		}

		@Override
		public void run() {
			while (true) {
				try {
					System.out.println(name + "<-" + queue.take());
					Thread.sleep((int) (Math.random() * 1000));
				} catch (InterruptedException error) {
					break;
				}
			}
		}
	}

	/**
	 * 自定义的的阻塞队列(这里是为了演示wait/notify的用法，实际上有更好的选择是直接用java.util.concurrent.BlockingQueue)
	 */
	static class MyQueue {
		private Queue<String> queue = new LinkedList<String>();
		private final int capacity;// 队列的容量
		private volatile int count = 0;
		private Object lock = new Object();

		public MyQueue(int capacity) {
			this.capacity = capacity;
		}

		public void put(String e) throws InterruptedException {
			synchronized (lock) {
				while (count == capacity) {
					lock.wait();
				}
				queue.add(e);
				count++;
				lock.notify();
			}
		}

		public String take() throws InterruptedException {
			String x;
			synchronized (lock) {
				while (count == 0) {
					lock.wait();
				}
				x = queue.remove();
				count--;
				lock.notify();
			}
			return x;
		}

	}

}
