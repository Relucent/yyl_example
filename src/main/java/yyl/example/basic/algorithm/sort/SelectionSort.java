package yyl.example.basic.algorithm.sort;

import java.util.Arrays;

/**
 * <h3>选择排序(Selection Sort)</h3><br>
 * 选择排序是一种简单的排序方法，它的基本思想是：从R[0]~R[n-1]中选取最小值，与R[0]交换。<br>
 * 实现流程如下：<br>
 * 对长度为n的数组R进行排序<br>
 * 第一次从R[0]~R[n-1]中选取最小值，与R[0]交换；<br>
 * 第二次从R[1]~R[n-1]中选取最小值，与R[1]交换；<br>
 * ......<br>
 * 第i次从R[i-1]~R[n-1]中选取最小值，与R[i-1]交换；<br>
 * ......<br>
 * 第n-1次从R[n-2]~R[n-1]中选取最小值，与R[n-2]交换；<br>
 * 总共通过n-1次，得到一个按排序码从小到大排列的有序序列。<br>
 * 直接选择排序的时间复杂度是O(n^2)，辅助存储是O(1)。<br>
 * 由于在直接选择排序中存在着不相邻元素之间的互换，因此，直接选择排序是一种不稳定的排序方法。<br>
 */
public class SelectionSort {

    public static void main(String[] args) {

        int[] array = Helper.generate(10);
        System.out.println(Arrays.toString(array));

        sort(array);
        System.out.println(Arrays.toString(array));
    }

    public static void sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int k = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[k]) {
                    k = j;
                }
            }
            if (k != i) {
                int temp = array[i];
                array[i] = array[k];
                array[k] = temp;
            }
        }
    }

}
