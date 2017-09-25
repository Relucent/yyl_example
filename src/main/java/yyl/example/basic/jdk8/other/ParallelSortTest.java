package yyl.example.basic.jdk8.other;

import java.util.Arrays;
import java.util.Random;

/**
 * <h1>并行排序</h1><br>
 * Java 8实现了一种简洁的方法来加速排序 <br>
 * 使用 Arrays.parallelSort 来代替 Arrays.sort <br>
 * 这会自动把目标数组分割成几个部分，这些部分会被放到独立的CPU核上去运行，再把结果合并起来。<br>
 * 这里唯一需要注意的是，在一个大量使用多线程的环境中，比如一个繁忙的Web容器，这种方法的好处就会减弱（降低90%以上），因为越来越多的CPU上下文切换增加了开销。<br>
 */
public class ParallelSortTest {

	public static void main(String[] args) {

		int[] values = generate(100);

		System.out.println(Arrays.toString(values));

		//并行排序
		Arrays.parallelSort(values);

		System.out.println(Arrays.toString(values));
	}

	//生成乱序数字
	private static int[] generate(int size) {
		int[] array = new int[size];
		for (int i = 0; i < size; i++) {
			array[i] = i;
		}
		Random rnd = new Random();
		for (int i = size - 1; i >= 0; i--) {
			int j = rnd.nextInt(i + 1);
			int temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
		return array;
	}
}
