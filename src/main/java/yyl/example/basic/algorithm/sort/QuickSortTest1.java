package yyl.example.basic.algorithm.sort;

public class QuickSortTest1 {

	public static void swap(int a[], int i, int j) {
		count++;
		if (i == j) {
			return;
		}

		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	public static int partition(int array[], int left, int right) {

		int pivot = array[left];
		while (left < right) {
			while (left < right && array[right] >= pivot)
				right--;
			swap(array, left, right);
			while (left < right && array[left] <= pivot)
				left++;
			swap(array, left, right);
		}
		array[left] = pivot;
		return left;
	}

	static int count = 0;

	public static void quickSort(int array[], int left, int right) {

		if (left < right) {
			int pivot = partition(array, left, right);
			quickSort(array, left, pivot - 1);
			quickSort(array, pivot + 1, right);
		}
	}

	public static void main(String[] args) {

		int array[] = { 5, 6, 0, 7, 4, 8, 3, 2, 1, 10, 9 };

		quickSort(array, 0, array.length - 1);
		System.out.println(count);
		for (int i : array)
			System.out.print(i + ",");
	}

}
