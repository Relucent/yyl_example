package yyl.example.basic.test;

import java.util.Arrays;

/**
 * Arrays.binarySearch() 是JDK提供的二分查找方法<br>
 * 返回值规则如下：<br>
 * 1、如果找到关键字，则返回值为关键字在数组中的位置索引，且索引从0开始。<br>
 * 2、如果没有找到关键字，返回值为负的插入点值，所谓插入点值就是第一个比关键字大的元素在数组中的位置索引，而且这个位置索引从1开始。<br>
 */
public class ArraysBinarySearchTest {
	public static void main(String[] args) {
		int[] sample = { 10, 20, 30, 40, 50, 60, 70, 80, 90 };
		System.out.println(Arrays.binarySearch(sample, 20));
		System.out.println(Arrays.binarySearch(sample, 90));
		System.out.println(Arrays.binarySearch(sample, 0));
		System.out.println(Arrays.binarySearch(sample, 25));
		System.out.println(Arrays.binarySearch(sample, 100));
	}
}
