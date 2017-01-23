package yyl.example.basic.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程变量测试<br>
 * 这个测试可以看出volatile关键字不能保证同步(事实上这个关键字只保证变量的可见性)
 */
public class SynchronizedTest {

	private static int generalVariable = 0;
	private static volatile int volatileVariable = 0;
	private static final AtomicInteger atomicVariable = new AtomicInteger(0);
	private static int synchronizedVariable = 0;

	public static void main(String[] args) throws InterruptedException {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		for (int i = 0; i < 1000; i++) {
			threadPool.submit(new Runnable() {
				public void run() {
					generalVariable++;
					volatileVariable++;
					atomicVariable.incrementAndGet();
					synchronized (SynchronizedTest.class) {
						synchronizedVariable++;
					}
				}
			});
		}

		threadPool.shutdown();
		threadPool.awaitTermination(1, TimeUnit.MINUTES);
		System.out.println("general ->" + generalVariable);// 每次运行结果可能不相同，可能不等于1000
		System.out.println("volatile ->" + volatileVariable);// 每次运行结果可能不相同，可能不等于1000
		System.out.println("atomic  ->" + atomicVariable);// 等于1000
		System.out.println("synchronized-> " + synchronizedVariable);// 等于1000
	}

}
