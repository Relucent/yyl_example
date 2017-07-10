package yyl.example.demo.guava.concurrent;

import java.util.concurrent.ExecutionException;

import com.google.common.util.concurrent.SettableFuture;

public class SettableFutureTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		SettableFuture<Boolean> future = SettableFuture.create();

		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				try {
					Thread.sleep(1000);
					System.out.println(Thread.currentThread() + "->" + i);
				} catch (Exception e) {
					break;
				}
			}
			future.set(true);

		}).start();

		System.out.println(Thread.currentThread() + "^");
		System.out.println(future.get());
		System.out.println(Thread.currentThread() + "$");
	}
}
