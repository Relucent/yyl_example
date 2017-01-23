package yyl.example.basic.algorithm.sort;

public class QuickSortTest2 {

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

		int p_pos = left;
		for (int i = left + 1; i <= right; i++) {
			if (array[i] < pivot) {
				p_pos++;
				swap(array, p_pos, i);
			}
		}
		swap(array, left, p_pos);
		return p_pos;
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
