package yyl.example.basic.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExecutor相关说明：<br>
 * 当提交的任务数小于corePoolSize时，直接使用线程池的核心线程来执行。<br>
 * 当提交的任务数大于corePoolSize时，其余的任务会放到队列中等待执行。<br>
 * 只有当核心线程和队列都已经满了的时候，才会启动新的线程(补偿)来执行。<br>
 * 如果创建线程数大于maximumPoolSize，会使用RejectedExecutionHandler类处理新的任务(默认是抛出异常)<br>
 */
public class ThreadPoolExecutorTest1 {

	/**
	 * 以下例子，因为任务数没有超过队列的限制，所以不会启用新的线程。(永远单线程)
	 */
	public static void main(String[] args) throws InterruptedException {
		ThreadPoolExecutor pool = new ThreadPoolExecutor(//
				1, // 核心线程数
				100, // 最大允许的线程数(补偿)
				60, TimeUnit.SECONDS, //
				new ArrayBlockingQueue<Runnable>(200));

		for (int i = 0; i < 100; i++) {
			pool.submit(new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					Thread.sleep(1 * 1000);
					System.out.println(Thread.currentThread().getName());
					return null;
				}
			});
		}

		pool.awaitTermination(1 * 1000 * 100, TimeUnit.SECONDS);
		pool.shutdown();
	}
}
