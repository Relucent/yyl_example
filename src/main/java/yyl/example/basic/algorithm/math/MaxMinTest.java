package yyl.example.basic.algorithm.math;

/**
 * 不使用条件语句<br>
 * 获得两个数值之中较大的一个。<br>
 * 获得两个数值之中较小的一个。<br>
 */
public class MaxMinTest {

	/**
	 * 返回两个值中较大的一个
	 * @param a 参数
	 * @param b 参数
	 * @return a 和 b 中的较大者
	 */
	public static int max(int a, int b) {
		int c = a - b;
		int sign = (c >>> 31);
		int difference = c * sign;
		return a - difference;
	}

	/**
	 * 返回两个值中较小的一个
	 * @param a 参数
	 * @param b 参数
	 * @return a 和 b 中的较小者
	 */
	public static int mix(int a, int b) {
		int c = a - b;
		int sign = 1 - (c >>> 31);
		int difference = c * sign;
		return a - difference;
	}

	/* 测试 */
	public static void main(String[] args) {
		System.out.println(max(10, 500));
		System.out.println(max(500, 10));
		System.out.println(mix(10, 500));
		System.out.println(mix(500, 10));
	}
}
