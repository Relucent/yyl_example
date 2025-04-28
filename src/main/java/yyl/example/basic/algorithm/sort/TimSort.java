package yyl.example.basic.algorithm.sort;

import java.util.Arrays;

/**
 * TimSort 它是一种混合排序算法，结合了归并排序和插入排序的思想。<br>
 * TimSort 最初由 Tim Peters 在 2002 年为 Python 语言设计，并在 Java 等其他语言中也得到了应用。<br>
 * TimSort 主要基于以下几个关键点：<br>
 * 插入排序：对于小规模的子数组，TimSort 使用插入排序，因为插入排序对于小数据集表现很好。<br>
 * 归并排序：对于较大的子数组，TimSort 采用归并排序的方法，通过将子数组进行归并来保持高效性。<br>
 * TimSort 的核心思想是将数组分成小块（通常称为“run”），这些块的大小通常由插入排序处理。然后，使用归并排序的方式将这些块合并起来，以达到排序的目的。<br>
 * 归并排序算法非常复杂，本示例只是简化版的 TimSort 算法，没有实现归并栈的优化（按照归并规则归并）。<br>
 * 完整的归并排序算法可以参考 JDK8 的 java.util.ComparableTimSort。<br>
 */
public class TimSort {

	public static void main(String[] args) {
		int[] array = Helper.generate(100);
		System.out.println(Arrays.toString(array));
		sort(array);
		System.out.println(Arrays.toString(array));
	}

	// TimSort 主方法
	public static void sort(int[] array) {

		int n = array.length;
		int RUN = 32; // 划分子数组的大小

		// 对每个小子数组使用插入排序
		for (int i = 0; i < n; i += RUN) {
			insertionSort(array, i, Math.min(i + RUN - 1, n - 1));
		}

		// 对每两个已排序的子数组进行合并
		for (int size = RUN; size < n; size = 2 * size) {
			for (int left = 0; left < n; left += 2 * size) {
				int mid = Math.min(n - 1, left + size - 1);
				int right = Math.min((left + 2 * size - 1), (n - 1));
				if (mid < right) {
					merge(array, left, mid, right);
				}
			}
		}
	}

	// 插入排序，用于处理小规模数据
	private static void insertionSort(int[] array, int left, int right) {
		for (int i = left + 1; i <= right; i++) {
			int temp = array[i];
			int j = i - 1;
			while (left <= j && temp < array[j]) {
				array[j + 1] = array[j];
				j--;
			}
			array[j + 1] = temp;
		}
	}

	// 合并两个已经排序的子数组
	private static void merge(int[] array, int left, int mid, int right) {
		// 计算子数组的大小
		int len1 = mid - left + 1;
		int len2 = right - mid;

		// 创建临时数组
		int[] leftArray = new int[len1];
		int[] rightArray = new int[len2];

		// 将数据复制到临时数组
		System.arraycopy(array, left, leftArray, 0, len1);
		System.arraycopy(array, mid + 1, rightArray, 0, len2);

		// 合并临时数组
		int i = 0, j = 0;
		int k = left;
		while (i < len1 && j < len2) {
			if (leftArray[i] <= rightArray[j]) {
				array[k] = leftArray[i];
				i++;
			} else {
				array[k] = rightArray[j];
				j++;
			}
			k++;
		}

		// 复制剩余元素
		while (i < len1) {
			array[k] = leftArray[i];
			i++;
			k++;
		}
		while (j < len2) {
			array[k] = rightArray[j];
			j++;
			k++;
		}
	}
}