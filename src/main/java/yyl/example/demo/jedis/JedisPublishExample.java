package yyl.example.demo.jedis;

import java.text.ParseException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Jedis 发布订阅
 */
public class JedisPublishExample {

	private static final String CHANNEL_KEY = "my-test-channel";

	public static void main(String[] args) throws ParseException {
		new Thread(new Subcriber("S1")).start();
		new Thread(new Subcriber("S2")).start();
		new Thread(new Subcriber("S3")).start();
		new Thread(new Publisher()).start();
	}

	private static class Publisher implements Runnable {
		@Override
		public void run() {
			long sequence = 0;
			try (Jedis jedis = new Jedis("localhost", 6379)) {
				while (true) {
					jedis.publish(CHANNEL_KEY, "MESSAGE#" + (sequence++));
					try {
						Thread.sleep(2 * 1000);
					} catch (InterruptedException e) {
						break;
					}
				}
			}
		}
	}

	private static class Subcriber implements Runnable {

		final String name;

		public Subcriber(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			try (Jedis jedis = new Jedis("localhost", 6379)) {
				jedis.subscribe(new JedisPubSub() {
					@Override
					public void onMessage(String channel, String message) {
						System.out.println(name + " <- " + message);
					}
				}, CHANNEL_KEY);
			}
		}
	}

}
