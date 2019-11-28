package yyl.example.basic.algorithm.sort;

import java.util.Arrays;

/**
 * <h3>希尔排序（ShellSort）</h3><br>
 * 希尔排序是插入排序的一种更高效的改进版本，它出自D.L.Shell，因此而得名。<br>
 * 希尔排序又称作缩小增量排序，希尔排序的执行时间依赖于增量序列。<br>
 * 希尔排序先将整个待排序的记录序列分割成为若干子序列分别进行直接插入排序，待整个序列中的记录“基本有序”时，再对全体记录进行依次直接插入排序。<br>
 * 说明：<br>
 * 对于大规模乱序数组直接插入排序很慢，因为它只会交换相邻的元素，因此元素只能一点一点地从数组的一端移动到另一端。<br>
 * 例如，如果主键最小的元素正好在数组的尽头，要将它挪到正确的位置就需要N-1次移动。<br>
 * 希尔排序为了加快速度简单的改进了插入排序，交换不相邻的元素对数组的局部进行排序，并最终用插入排序将局部有序的数组排序。<br>
 * 希尔排序的思想是使数组中任意间隔为h的元素都是有序的。这样的数组被称为h有序数组。<br>
 * 希尔排序使用一个序列h1，h2，h3，…，ht，叫做增量序列（increment sequence）。<br>
 * 在使用增量hk的一趟排序之后，对于每一个i我们都有a[i] ≤ a[i+hk]，所有相隔hk的元素都被排序，此时称文件是hk排序的（hk-sorted）。<br>
 * 希尔排序的一个重要性质：一个hk排序的文件（然后将是hk-1排序的）保持它的hk排序性。<br>
 * hk排序的一般做法是，对于hk，hk+1，...，N-1中的每一个位置i，把其上的元素放到i，i-hk，i-2hk，...中的正确位置上。<br>
 * 一趟hk排序的作用就是对hk个独立的子数组执行一次插入排序。<br>
 * 希尔排序的时间性能O(n^(1.3~2)) 优于直接插入排序 O(n^2) ，但是没有快速排序算法快O(n*logn)。 因此中等大小规模表现良好，对规模非常大的数据排序不是最优选择。<br>
 */
public class ShellSort {

    public static void main(String[] args) {

        int[] array = Helper.generate(10);
        System.out.println(Arrays.toString(array));

        sort(array);
        System.out.println(Arrays.toString(array));
    }

    public static void sort(int[] array) {

        // 初始增量h=数组长度/2 ;增量等于0的时候说明已经排完了 ;增量每次减半
        // 如果增量是 h，那么一组的元素个数是 length/h
        for (int h = array.length >> 1; h >= 1; h = h >> 1) {

            // 这是一个插入排序 (h1~ht)
            // 1. 每一组的数据间隔 h 个元素，[i,i+h,i+2h...]是一组，[i+1,i+1+h,i+1+2h...]是另一组，以此类推
            // 2. 这个算法并不是完成一组之后，再执行下一组的，而是一个循环里面一起执行的
            for (int i = h; i < array.length; i++) {
                int temp = array[i];
                int j = i - h;
                while (j >= 0 && temp < array[j]) {
                    array[j + h] = array[j];
                    j -= h;
                }
                array[j + h] = temp;
            }
        }
    }
}

