package yyl.example.basic.algorithm.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 */
public class BubbleSort {

    public static void main(String[] args) {

        int[] array = Helper.generate(10);
        System.out.println(Arrays.toString(array));

        sort(array);
        System.out.println(Arrays.toString(array));
    }

    public static void sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    swap(array, i, j);
                }
            }
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        System.out.println(Arrays.toString(array) + " |*" + i + "<=>*" + j);
    }
}
