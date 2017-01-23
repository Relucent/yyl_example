package yyl.example.basic.pattern;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 生产者消费者模式
 */
public class ProducerConsumer {

	public static void main(String[] args) throws InterruptedException {

		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10);
		new Producer("p1", queue).start();
		new Producer("p2", queue).start();
		new Consumer("c1", queue).start();
		new Consumer("c2", queue).start();

	}

	// 生产者
	static class Producer extends Thread {
		final String name;
		final BlockingQueue<String> queue;

		Producer(String name, BlockingQueue<String> queue) {
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

		final BlockingQueue<String> queue;
		final String name;

		Consumer(String name, BlockingQueue<String> queue) {
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
}
