package yyl.example.basic.algorithm.permute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 全排列
 */
public class FullPermutation {

    public static void main(String[] s) {
        Object[] src = {"0", "1", "2",};
        List<Object[]> result = permute(src);
        for (Object[] item : result) {
            System.out.println(Arrays.toString(item));
        }
    }

    public static List<Object[]> permute(Object[] arrays) {
        List<Object[]> result = new ArrayList<Object[]>();
        permute(arrays, 0, result);
        return result;
    }

    protected static void permute(Object[] arrays, int level, List<Object[]> result) {
        if (level == arrays.length - 1) {
            result.add(arrays.clone());
        } else {
            for (int i = level; i < arrays.length; i++) {
                swap(arrays, level, i);
                permute(arrays, level + 1, result);
                swap(arrays, level, i);
            }
        }
    }

    protected static void swap(Object[] arrays, int i, int j) {
        Object obj = arrays[i];
        arrays[j] = arrays[i];
        arrays[j] = obj;
    }
}
