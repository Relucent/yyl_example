package yyl.example.basic.algorithm.exercises;

import java.util.Arrays;

/**
 * 笛卡尔积 (算法使用递推方式)
 */
public class CartesianProduct {
    public static void main(String[] args) {
        String[][] attributes = {//
                {"A1", "A2", "A3", "A4"}, //
                {"B1", "B2", "B3", "B4", "B5"}, //
                {"C1", "C2", "C3"}, //
        };
        int total = 1;
        for (int i = 0; i < attributes.length; i++) {
            total *= attributes[i].length;
        }

        int[] carrys = new int[attributes.length];
        carrys[attributes.length - 1] = 1;
        for (int i = attributes.length - 1; i > 0; i--) {
            carrys[i - 1] = carrys[i] * attributes[i].length;
        }
        System.out.println(Arrays.toString(carrys));

        for (int index = 0; index < total; index++) {
            int surplus = index;
            int[] zIndexs = new int[attributes.length];
            for (int k = 0; k < attributes.length; k++) {
                int carry = carrys[k];
                zIndexs[k] = surplus / carry;
                surplus = surplus % carry;
            }
            String[] values = new String[attributes.length];
            for (int k = 0; k < attributes.length; k++) {
                values[k] = attributes[k][zIndexs[k]];
            }
            System.out.println(Arrays.toString(values));
        }
    }
}
