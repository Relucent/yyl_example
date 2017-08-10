package yyl.example.basic.algorithm.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 快速排序（Quicksort）是对冒泡排序的一种改进。<br>
 * 它的基本思想是：通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小，然后再按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列。
 */
public class Quicksort {

	public static void main(String[] args) {

		int[] array = generate(10);
		System.out.println(Arrays.toString(array));

		sort(array, 0, array.length - 1);
		System.out.println(Arrays.toString(array));
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

	public static void sort(int[] array, int left, int right) {
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
					System.out.println(Arrays.toString(array) + " |*" + i + "<=>*" + j);
				}
			}
			//将基准数归位
			swap(array, i, left);

			System.out.println(Arrays.toString(array) + " |#" + i);

			sort(array, left, i - 1);
			sort(array, i + 1, right);
		}
	}

	private static void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

}
