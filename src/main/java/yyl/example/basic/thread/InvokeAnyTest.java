package yyl.example.basic.thread;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ExecutorService.invokeAny 的使用演示<br>
 * invokeAny的功能是启动新的线程执行Callable列表中的任务，并将第一个得到的结果作为返回值，然后立刻终结所有的线程。
 */
public class InvokeAnyTest {

	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName() + "......开始.");// 打印结束标记

		ExecutorService threadPool = Executors.newCachedThreadPool();
		try {

			Collection<TestCallable> tasks = new ArrayList<TestCallable>();
			tasks.add(new TestCallable("A"));
			tasks.add(new TestCallable("B"));
			tasks.add(new TestCallable("C"));
			tasks.add(new TestCallable("D"));
			tasks.add(new TestCallable("E"));

			Object result = threadPool.invokeAny(tasks);

			System.out.println("返回->" + result);

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			threadPool.shutdown();
		}
		// 等待所有任务
		System.out.println(Thread.currentThread().getName() + "......结束.");// 打印结束标记
	}

	static class TestCallable implements Callable<String> {

		private String name;

		TestCallable(String name) {
			this.name = name;
		}

		public String call() throws Exception {
			System.out.println(name + "开始...");
			try {
				Thread.sleep(1000L + (long) (1000 * Math.random()));
				System.out.println(name + "结束...");
			} catch (InterruptedException e) {
				System.out.println(name + "被中断");
			}
			return name;
		}
	}

}
