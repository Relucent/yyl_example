package yyl.example.demo.redis;

import java.util.List;

import redis.clients.jedis.Jedis;

/**
 * 使用JedisList模拟生产者消费者模式
 */
public class JedisListQueueTest {

	private static final String QUEUE_KEY = "my-test-queue";

	public static void main(String[] args) {
		new Thread(new Producer()).start();
		new Thread(new Consumer("C1")).start();
		new Thread(new Consumer("C2")).start();
		new Thread(new Consumer("C3")).start();
	}

	private static class Producer implements Runnable {
		@Override
		public void run() {
			long sequence = 0;
			try (Jedis jedis = new Jedis("localhost", 6379)) {
				jedis.del(QUEUE_KEY);
				while (true) {
					for (int i = 0; i < 100; i++) {
						jedis.lpush(QUEUE_KEY, String.valueOf(sequence++));
					}
					try {
						Thread.sleep(1 * 1000);
					} catch (InterruptedException e) {
						break;
					}
				}
			}
		}
	}

	private static class Consumer implements Runnable {

		final String name;

		public Consumer(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			try (Jedis jedis = new Jedis("localhost", 6379)) {
				while (!Thread.currentThread().isInterrupted()) {
					List<String> values = jedis.blpop(0, QUEUE_KEY);
					System.out.println("[" + name + "] -> " + values);
				}
			}
		}
	}

}
