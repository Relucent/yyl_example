package yyl.example.basic.algorithm.sort;

import java.util.Random;

class Helper {

    // 用来生成测试数据的
    public static int[] generate(int size) {
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

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}
