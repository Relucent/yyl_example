package yyl.example.basic.algorithm.select;

import java.util.Random;

/**
 * Quick select算法通常用来在未排序的数组中寻找第k小/第k大的元素。其方法类似于Quick sort。Quick select和Quick sort都是由Tony Hoare发明的，因此Quick select算法也被称为是Hoare's selection algorithm。<br>
 * Quick select采用和Quick sort类似的步骤。 首先选定一个pivot，然后根据每个数字与该pivot的大小关系将整个数组分为两部分。<br>
 * 1、随机产生一个pivot<br>
 * 2、根据这个pivot，将小于其的数放左边，大于其的数放右边<br>
 * 3、更新第n大数的估计值的位置，选择其中一边，直到=n<br>
 * 4、重复2、3步骤<br>
 * 与Quick sort不同的是，Quick select只考虑所寻找的目标所在的那一部分子数组，而非像Quick sort一样分别再对两边进行分割。<br>
 * 因为不需要全部排序，所以Quick select将平均时间复杂度从O(nlogn)降到了O(n)。 <br>
 * 时间复杂度：O(N)，平均的时间复杂度近似于2N，计算复杂度忽略常数得到O(N)，如果pivot选择固定位置，最坏情况(数组有序)时间复杂度是O(N^2)，通过随机pivot避免最坏情况<br>
 * 空间复杂度：O(1)<br>
 */
public class QuickSelect {

	public static void main(String[] args) {
		System.out.println(findKthLargest(new int[] { 3, 2, 1, 5, 6, 4 }, 2));// 5
		System.out.println(findKthLargest(new int[] { 3, 2, 3, 1, 2, 4, 5, 5, 6 }, 4));// 4
		System.out.println(findKthLargest(new int[] { 1 }, 1));// 1
	}

	private static Random RANDOM = new Random();

	/**
	 * 查找数组中的第K个最大元素
	 * @param nums 数组
	 * @param k K参数
	 * @return 数组中的第K个最大元素
	 */
	public static int findKthLargest(int[] nums, int k) {
		return quickSelect(nums, 0, nums.length - 1, k);
	}

	// 快速查找算法
	private static int quickSelect(int[] nums, int left, int right, int k) {
		// 如果对于一个已排序的数组，我们每次都选择最大/最小的值为 pivot，那么时间复杂度为 O(N^2) 。
		// 每次通过 random 选择 pivot 可以尽量避免最坏情况发生
		int pivot = RANDOM.nextInt(right - left + 1) + left;
		// 把 pivot 移动到最右的位置，以最右为标杆(nums[right])
		swap(nums, right, pivot);

		// 定义两个指针
		int i = left;
		int j = left;
		while (j < right) {
			// 如果 j 所在的指针是否小于等于 pivot的值，那么往左替换(和i位置交换)
			if (nums[j] <= nums[right]) {
				swap(nums, i, j);
				i++;
			}
			j++;
		}
		// 把标杆替换到分割位置（此处j===right）
		swap(nums, i, right);

		// pivot 是我们要找的 Top k
		if (right == k + i - 1) {
			return nums[i];
		}
		// Top k 在右边
		if (right > k + i - 1) {
			return quickSelect(nums, i + 1, right, k);
		}
		// Top k 在左边
		return quickSelect(nums, left, i - 1, k - (right - i + 1));
	}

	private static void swap(int[] nums, int i, int j) {
		int temp = nums[i];
		nums[i] = nums[j];
		nums[j] = temp;
	}
}
