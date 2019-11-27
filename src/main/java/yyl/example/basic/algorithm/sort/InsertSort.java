package yyl.example.basic.algorithm.sort;

import java.util.Arrays;

/**
 * <h3>插入排序（InsertSort）</h3><br>
 * 插入排序是一种简单直观且稳定的排序算法。<br>
 * 插入排序的基本操作就是将一个数据插入到已经排好序的有序数据中，从而得到一个新的、个数加一的有序数据。<br>
 * 算法适用于少量数据的排序，时间复杂度为O(n^2)，最好情况是O(n)--数据本来就有序，辅助空间复杂度为O(1)。 <br>
 * 如果细分插入排序包括：直接插入排序，二分插入排序（又称折半插入排序），链表插入排序，希尔排序（又称缩小增量排序）。<br>
 * 属于稳定排序的一种（通俗地讲，就是两个相等的数不会交换位置） 。<br>
 */
public class InsertSort {
    public static void main(String[] args) {

        int[] array = Helper.generate(10);
        System.out.println(Arrays.toString(array));

        srot(array);
        System.out.println(Arrays.toString(array));
    }

    // 这个算法是直接插入排序
    public static void srot(int[] array) {

        // 整个排序过程为 n-1 趟插入，即将第1个记录看做有序子序列，然后从第2个记录开始，逐个进行插入，直至整个序列有序
        for (int i = 1; i < array.length; i++) {
            // 后面比前面小，那么往前插
            if (array[i] < array[i - 1]) {
                int temp = array[i];
                int j = i - 1;
                // 依次往后移动，直到找到位置(temp < array[j]), 或者 j==-1
                while (j >= 0 && temp < array[j]) {
                    array[j + 1] = array[j--];
                }
                // 在这个位置插入元素
                array[j + 1] = temp;
            }
            // # 打印一下，可以看到每次进行插入后的数组情况
            System.out.println(Arrays.toString(array));
        }
    }
}
