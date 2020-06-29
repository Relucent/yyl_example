package yyl.example.basic.algorithm.sort;

import java.util.Arrays;

/**
 * <h3>基数排序(Radix Sort)</h3><br>
 * 基数排序是一种按记录关键字的各位值逐步进行排序的方法。属于“分配式排序”(Distribution Sort)，是一种非比较型整数排序算法。<br>
 * 比较型排序算法主要是利用比较和交换，而基数排序则是利用分配和收集两种基本操作。<br>
 * 此种排序一般适用于记录的关键字为整数类型的情况，对于字符串和文字排序不适用。<br>
 * <br>
 * 基数排序的发明可以追溯到1887年赫尔曼·何乐礼在打孔卡片制表机(Tabulation Machine)上的贡献。<br>
 * 它的实现方式是这样的：<br>
 * 将所有待比较数值（自然数）统一为同样的数位长度，数位较短的数前面补零。<br>
 * 然后，从最低位开始，依次进行一次排序。这样从最低位排序一直到最高位排序完成以后, 数列就变成一个有序序列。<br>
 * 基数排序的两种方式：<br>
 * 高位优先，又称为最有效键(MSD),它的比较方向是由右至左；<br>
 * 低位优先，又称为最无效键(LSD),它的比较方向是由左至右；<br>
 * 具体实现上，从个位数开始，逐位进行排序。由于每次partition都是稳定的，从而保证整体有序。<br>
 * 简单的来讲，如果是个三位数，先按照百位排序，百位相同按照十位排序，十位相同的按照个位排序。<br>
 * 基数排序法是属于稳定性的排序，其时间复杂度为O (nlog(r)m)，其中r为所采取的基数，而m为堆数。<br>
 * 在某些时候，基数排序法的效率高于其它的稳定性排序法。<br>
 */
public class RadixSort {

    public static void main(String[] args) {

        int[] array = Helper.generate(100);
        System.out.println(Arrays.toString(array));

        sort(array);
        System.out.println(Arrays.toString(array));
    }

    public static void sort(int[] array) {

        // 查找排序列最大值
        int max = array[0];
        for (int i : array) {
            if (i > max) {
                max = i;
            }
        }

        // 从个位开始，对数组进行排序，EXP指数
        for (int exp = 1; max / exp > 0; exp *= 10) {

            // 存储待排元素的临时数组
            int[] temp = new int[array.length];
            // 每位可能的数字为0~9，所以设置10个桶
            int[] buckets = new int[10];
            // 每个位数的起始位索引
            int[] offsets = new int[10];

            // 将该位数据出现的次数存储在buckets中
            for (int value : array) {
                // 桶的索引 0~9
                int index = (value / exp) % 10;
                // 次数计数
                buckets[index]++;
            }

            // 记录每个桶的数据插入到临时数组中的起始位置
            // 桶[0]的开始位置是0，桶[i]的开始位置是前一个桶开始位置加前一个桶的元素数
            for (int i = 1; i < 10; i++) {
                offsets[i] = offsets[i - 1] + buckets[i - 1];
            }

            // 将数据存储到临时数组TEMP中
            for (int i = 0; i < array.length; i++) {
                // #获得数据所属的桶的索引 0~9
                // int index = (array[i] / exp) % 10;
                // #获得数据应添加到临时数组中的位置
                // int offset = offsets[index];
                // #将数据添加到临时数组中
                // temp[offset] = array[i];
                // #每添加一个元素，这个位置向后移动一格
                // offsets[index]=offset+1;
                temp[offsets[(array[i] / exp) % 10]++] = array[i];
            }

            // 将有序数组赋给array
            System.arraycopy(temp, 0, array, 0, array.length);
        }
    }

}
