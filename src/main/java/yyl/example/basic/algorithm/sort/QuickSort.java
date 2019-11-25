package yyl.example.basic.algorithm.sort;

import java.util.Arrays;

/**
 * 快速排序（Quicksort）是对冒泡排序的一种改进。<br>
 * 它的基本思想是：通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小，然后再按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列。
 */
public class QuickSort {

    public static void main(String[] args) {

        int[] array = Helper.generate(10);
        System.out.println(Arrays.toString(array));

        srot(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }


    public static void srot(int[] array, int left, int right) {
        if (left < right) {
            int i = left;
            int j = right;
            int x = array[i];// 基数
            while (i < j) {
                // 基数是从左边取的，所以要先从右边开始查找
                while (i < j && array[j] >= x) {
                    j--;
                }
                // 再从左边开始找
                while (i < j && array[i] <= x) {
                    i++;
                }
                // 交换两个数在数组中的位置
                if (i < j) {
                    swap(array, i, j);
                }
            }
            // 将 基数移动到位置(分割点的位置)
            swap(array, left, i);
            System.out.println("i=" + i);

            // 递归
            srot(array, left, i - 1);
            srot(array, i + 1, right);
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        System.out.println(Arrays.toString(array) + " |*" + i + "<=>*" + j);
    }
}
