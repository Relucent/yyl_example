package yyl.example.basic.thread;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Fork/Join框架是Java7提供了的一个用于并行执行任务的框架， 是一个把大任务分割成若干个小任务，最终汇总每个小任务结果后得到大任务结果的框架。
 */
public class ForkJoinTest {

	public static void main(String[] args) {
		int[] array = generate(10);
		println(array);

		ForkJoinPool pool = new ForkJoinPool();

		QuicksortTask task = new QuicksortTask(array);

		pool.submit(task);

		try {
			task.get();//等待计算完成
		} catch (Exception e) {
			e.printStackTrace();
		}
		pool.shutdown();

		println(array);
	}

	//快速排序
	@SuppressWarnings("serial")
	private static class QuicksortTask extends RecursiveTask<Void> {
		private final int[] array;
		private final int left;
		private final int right;

		public QuicksortTask(int[] array) {
			this(array, 0, array.length - 1);
		}

		public QuicksortTask(int[] array, int left, int right) {
			super();
			this.array = array;
			this.left = left;
			this.right = right;
		}

		@Override
		protected Void compute() {
			if (left < right) {
				int i = left;
				int j = right;
				int temp = array[left];//基数
				// i < j
				while (i != j) {
					//顺序很重要，要先从右边开始找 
					while (i < j && temp <= array[j]) {
						j--;
					}
					//再从左边开始找 
					while (i < j && array[i] <= temp) {
						i++;
					}
					//交换两个数在数组中的位置 
					if (i < j) {
						swap(array, i, j);
					}
				}
				//将基准数归位
				swap(array, i, left);

				//打印
				println(array);

				invokeAll(//
						new QuicksortTask(array, left, i - 1), //	
						new QuicksortTask(array, i + 1, right)//
				);
			}
			return null;
		}
	}

	private static int[] generate(int size) {
		int[] array = new int[size];
		for (int i = 0; i < size; i++) {
			array[i] = i;
		}
		Random rnd = new Random();
		for (int i = size - 1; i >= 0; i--) {
			swap(array, i, rnd.nextInt(i + 1));
		}
		return array;
	}

	private static void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	private static void println(int[] array) {
		synchronized (array) {
			System.out.println(Arrays.toString(array) + " " + Thread.currentThread());
		}
	}
}
