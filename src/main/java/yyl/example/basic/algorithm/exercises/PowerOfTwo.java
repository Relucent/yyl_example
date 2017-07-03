package yyl.example.basic.algorithm.exercises;

/**
 * 题目:<br>
 * 判断一个数字是不是2的乘方<br>
 */
public class PowerOfTwo {
	public static void main(String[] args) {

		System.out.println(isPowerOf2(5));
		System.out.println(isPowerOf2(8));

	}

	// 2的乘方有一个规律，N&(N-1) === 0
	private static boolean isPowerOf2(int i) {
		return (i & (i - 1)) == 0;
	}
}
