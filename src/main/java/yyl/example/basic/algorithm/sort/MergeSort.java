package yyl.example.basic.algorithm.sort;

import java.util.Arrays;

/**
 * <h3>归并排序（MergeSort）</h3><br>
 * 归并排序是建立在归并操作上的一种有效的排序算法,该算法是采用分治法（Divide and Conquer）的一个非常典型的应用。<br>
 * 将已有序的子序列合并，得到完全有序的序列；即先使每个子序列有序，再使子序列段间有序。<br>
 * 若将两个有序表合并成一个有序表，称为二路归并。<br>
 * 归并排序是一种稳定的排序方法。<br>
 * 时间复杂度：O(nlogn)<br>
 * 空间复杂度：O(N)，归并排序需要一个与原数组相同长度的数组做辅助来排序。<br>
 */
public class MergeSort {

    public static void main(String[] args) {

        int[] array = Helper.generate(10);
        System.out.println(Arrays.toString(array));

        sort(array);
        System.out.println(Arrays.toString(array));
    }

    public static void sort(int[] array) {
        sort(array, 0, array.length - 1);
    }

    private static void sort(int[] array, int left, int right) {
        if (left < right) {
            // 分割数组，递归最终会分割到只剩下一个元素(一个元素的时候可以当做是有序的)
            int middle = (left + right) / 2;
            // 左侧排序
            sort(array, left, middle);
            // 右侧排序
            sort(array, middle + 1, right);
            // 合并两个数组
            merge(array, left, middle, right);
        }
    }

    // 合并两个有序的数组
    private static void merge(int[] array, int left, int middle, int right) {

        // 创建临时数组 temp
        int[] temp = new int[right - left + 1];
        int i = 0;
        int p1 = left;
        int p2 = middle + 1;

        // 比较左右两部分的元素，将小的元素填入temp中
        while (p1 <= middle && p2 <= right) {
            temp[i++] = array[p1] < array[p2] ? array[p1++] : array[p2++];
        }

        // 把剩余的元素依次填入到temp中
        while (p1 <= middle) {
            temp[i++] = array[p1++];
        }
        // 把剩余的元素依次填入到temp中
        while (p2 <= right) {
            temp[i++] = array[p2++];
        }
        // 把最终的排序的结果复制给原数组
        for (i = 0; i < temp.length; i++) {
            array[left + i] = temp[i];
        }
    }
}
