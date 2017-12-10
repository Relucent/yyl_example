package yyl.example.basic.thread;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * shutdown调用后，不可以再submit新的task，已经submit的将继续执行<br>
 * shutdownNow试图停止当前正执行的task，并返回尚未执行的task的list <br>
 */
public class ThreadPoolExecutorTest3 {
	public static void main(String[] args) throws InterruptedException {

		ExecutorService pool = Executors.newSingleThreadExecutor();
		pool.submit(createCallable());
		pool.submit(createCallable());
		pool.submit(createCallable());
		pool.awaitTermination(1, TimeUnit.SECONDS);
		List<Runnable> runnables = pool.shutdownNow();
		System.out.println(runnables.size());
	}

	private static Callable<Boolean> createCallable() {
		return new Callable<Boolean>() {
			@Override
			public Boolean call() {
				try {
					System.out.println("call()");
					TimeUnit.SECONDS.sleep(5);
					return Boolean.TRUE;
				} catch (InterruptedException e) {
					System.err.println(e);
					return Boolean.FALSE;
				}
			}
		};
	}
}
