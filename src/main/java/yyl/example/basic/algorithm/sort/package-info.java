/**
 * <pre>
 * 排序算法                         #_平均情况_        #_最好情况_     #_最坏情况_        #_辅助存储_       #_稳定性 
 * ├─插入排序 
 * │  ├─直接插入           #O(n^2)         #O(n)         #O(n^2)          #O(1)          #稳定
 * │  └─希尔排序     #O(n^1.3)       #O(n)         #O(n^2)          #O(1)          #不稳定
 * ├─选择排序
 * │  ├─直接选择           #O(n^2)         #O(n^2)       #O(n^2)          #O(1)          #不稳定
 * │  └─堆排序               #O(n*log2(n))   #O(n*log2(n)) #O(n*log2(n))    #O(1)          #不稳定
 * ├─交换排序
 * │  ├─冒泡排序           #O(n^2)         #O(n)         #O(n^2)          #O(1)          #稳定
 * │  └─快速排序           #O(n*log2(n))   #O(n*log2(n)) #O(n^2)          #O(n*log2(n))  #不稳定
 * ├─归并排序                   #O(n*log2(n))   #O(n*log2(n)) #O(n*log2(n))    #O(n)          #稳定
 * └─基数排序                   #O(d(r+n))      #O(d(n+rd))   #O(d(r+rd))      #O(rd+n)       #稳定
 * </pre>
 * 
 * 排序算法中英文对照 <br>
 * 插入排序 ：InsertSort<br>
 * 希尔排序 ：ShellSort<br>
 * 选择排序：SelectionSort<br>
 * 堆排序 ： HeapSort<br>
 * 冒泡排序：BubbleSort<br>
 * 快速排序：QuickSort<br>
 * 归并排序：MergeSort<br>
 * 基数排序：BucketSort<br>
 */
package yyl.example.basic.algorithm.sort;
