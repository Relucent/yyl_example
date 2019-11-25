package yyl.example.basic.algorithm.sort;

import java.util.Arrays;

/**
 * 全排序
 * @author yyl
 */
public class AllTaxisArray {

    public static void main(String[] s) {
        Object[] src = {"0", "1", "2",};
        Object[] arrays = new Object[src.length];
        System.arraycopy(src, 0, arrays, 0, src.length);
        recursionAllTaxis(arrays, 0);
    }

    static public void recursionAllTaxis(Object[] arrays, int indexLevel) {
        if (indexLevel == arrays.length - 1) {
            System.out.println(Arrays.toString(arrays));
        } else {
            for (int i = indexLevel; i < arrays.length; i++) {
                swap(arrays, indexLevel, i);
                recursionAllTaxis(arrays, indexLevel + 1);
                swap(arrays, indexLevel, i);
            }
        }
    }

    final static public void swap(Object[] arrays, int index0, int index1) {
        Object obj = arrays[index0];
        arrays[index0] = arrays[index1];
        arrays[index1] = obj;
    }
}
