package yyl.example.basic.algorithm.bit;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;

/**
 * BitSet类实现了一个按需增长的位向量。<br>
 * BitSet的每一个索引都有一个boolean值，默认情况下，set 中所有位的初始值都是false，可以对每个编入索引的位进行测试、设置或者清除。通过逻辑与、逻辑或和逻辑异或操作。 <br>
 * BitSet的底层实现是使用long数组作为内部存储结构的，所以BitSet的大小为long类型大小(64位)的整数倍。<br>
 */
public class BitSetExample {

    // 有一个随机数数组，范围在[1~n]之间，要求将[1-n]之间没有在随机数中的数求出来。
    public static void main(String[] args) {

        int size = 20;
        int[] values = randomInts(size);
        BitSet bitSet = new BitSet(size);
        for (int i = 0; i < size; i++) {
            bitSet.set(values[i]);
        }
        System.out.println(Arrays.toString(values));

        int[] excluded = new int[size - bitSet.cardinality()];
        for (int i = 0, j = 0; i < size; i++) {
            if (!bitSet.get(i)) {
                excluded[j++] = i;
            }
        }
        System.out.println(Arrays.toString(excluded));
    }

    private static int[] randomInts(int size) {
        Random random = new Random();
        int[] values = new int[size];
        for (int i = 0; i < size; i++) {
            values[i] = random.nextInt(size);
        }
        return values;
    }
}
