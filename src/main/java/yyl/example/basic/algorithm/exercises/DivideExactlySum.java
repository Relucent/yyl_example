package yyl.example.basic.algorithm.exercises;

/**
 * 问题：<br>
 * 求从10到100中能被3或5整除的数的和<br>
 */
public class DivideExactlySum {
	public static void main(String[] args) {
		int sum = 0;
		for (int i = 10; i <= 100; i++) {
			if (i % 3 == 0 && i % 5 == 0) {
				sum += i;
			}
		}
		System.out.println(sum);
	}
}