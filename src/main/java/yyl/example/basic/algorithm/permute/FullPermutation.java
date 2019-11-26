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
        permute(arrays, 0, arrays.length - 1, result);
        return result;
    }

    protected static void permute(Object[] arrays, int begin, int end, List<Object[]> result) {
        if (begin == end) {
            result.add(arrays.clone());
        } else {
            for (int i = begin; i <= end; i++) {
                swap(arrays, begin, i);
                permute(arrays, begin + 1, end, result);
                swap(arrays, begin, i);
            }
        }
    }

    protected static void swap(Object[] arrays, int i, int j) {
        Object obj = arrays[i];
        arrays[j] = arrays[i];
        arrays[j] = obj;
    }
}
