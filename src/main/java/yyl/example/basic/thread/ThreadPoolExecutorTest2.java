package yyl.example.basic.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * RejectedExecutionHandler使用例子<br>
 * RejectedExecutionHandler有几个默认的实现类：<br>
 * CallerRunsPolicy 使用当前线程继续执行任务<br>
 * AbortPolicy 中断任务(就是抛异常)<br>
 * DiscardPolicy 抛弃任务(不做任何处理)<br>
 * DiscardOldestPolicy 抛弃队列中未执行的任务，添加新的任务<br>
 */
public class ThreadPoolExecutorTest2 {
	public static void main(String[] args) throws InterruptedException {

		int cpu = Runtime.getRuntime().availableProcessors();

		ThreadPoolExecutor executor = new ThreadPoolExecutor(//
				0, cpu * 2, //
				60, TimeUnit.SECONDS, //
				new SynchronousQueue<Runnable>(), //
				// 自定义实现,使用当前线程继续执行任务
				new RejectedExecutionHandler() {
					@Override
					public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
						if (!executor.isShutdown()) {
							runnable.run();
						}
					}
				});
		for (int i = 0; i < 100; i++) {
			executor.submit(new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					Thread.sleep(1 * 1000);
					System.out.println(Thread.currentThread().getName());
					return null;
				}
			});

		}

		executor.awaitTermination(1 * 1000 * 100, TimeUnit.SECONDS);
		executor.shutdown();
	}
}
