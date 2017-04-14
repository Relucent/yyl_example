package yyl.example.demo.redis;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import redis.clients.jedis.Jedis;

/**
 * 使用JedisList模拟生产者消费者模式
 */
public class JedisListQueueTest2 {

	private static final String QUEUE_KEY = "my-test-queue2";

	public static void main(String[] args) {

		Registration registration = new Registration();

		registration.register(new Consumer("C1"));
		registration.register(new Consumer("C2"));
		registration.register(new Consumer("C3"));

		Producer producer = new Producer(registration);
		producer.start();
	}

	private static class Registration {

		Map<String, Consumer> consumers = new ConcurrentHashMap<String, Consumer>();

		public void register(Consumer consumer) {
			consumers.put(consumer.name, consumer);
		}

		public void notice() {
			for (Consumer consumer : consumers.values()) {
				consumer.notice();
			}
		}
	}

	private static class Producer {

		private final Registration registration;
		private final ExecutorService pool = Executors.newCachedThreadPool();
		private int batchSequence = 0;

		public Producer(Registration registration) {
			this.registration = registration;
		}

		private String[] generate() {
			List<String> tasks = new ArrayList<String>();
			for (int i = 0; i < 10; i++) {
				tasks.add(batchSequence + ":" + i);
			}
			return tasks.toArray(new String[tasks.size()]);
		}

		private void waitFinish(Jedis jedis) {
			while (!Thread.currentThread().isInterrupted()) {
				if (jedis.llen(QUEUE_KEY) == 0L) {
					return;
				}
				sleepQuietly(3000);
			}
		}

		public void start() {

			pool.submit(new Runnable() {

				@Override
				public void run() {
					Thread currentThread = Thread.currentThread();

					try (Jedis jedis = new Jedis("localhost", 6379)) {
						jedis.del(QUEUE_KEY);

						while (!currentThread.isInterrupted()) {

							log("[#" + batchSequence + "] Generate! ");

							String[] tasks = generate();

							jedis.rpush(QUEUE_KEY, tasks);

							registration.notice();

							waitFinish(jedis);

							log("[#" + batchSequence + "] Finish! \n");

							batchSequence++;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	private static class Consumer {

		private final ExecutorService pool = Executors.newCachedThreadPool();

		private final String name;

		public Consumer(String name) {
			this.name = name;
		}

		public void notice() {
			pool.submit(new Runnable() {
				@Override
				public void run() {
					Thread currentThread = Thread.currentThread();
					try (Jedis jedis = new Jedis("localhost", 6379)) {
						while (!currentThread.isInterrupted()) {
							String value = jedis.lpop(QUEUE_KEY);
							if (value == null) {
								break;
							} else {
								log("[" + name + "] -> " + value);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					log("[" + name + "] # stop ");
				}
			});
		}
	}

	private static void sleepQuietly(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			Thread.interrupted();
		}
	}

	private static void log(String log) {
		System.out.println(DATE_FORMAT.get().format(System.currentTimeMillis()) + " " + log);
	}

	private static ThreadLocal<DateFormat> DATE_FORMAT = new ThreadLocal<DateFormat>() {
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		};
	};

}
