package yyl.example.basic.algorithm.math;

/**
 * 获得100以内的质数
 */
public class PrimeNumber {

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			if (checkPrim(i)) {
				System.out.println(i);
			}
		}
	}

	public static boolean checkPrim(int number) {
		if (-2 < number && number < 2) {
			return false;
		}
		for (int i = 2; i * i <= number; i++) {
			if (number % i == 0) {
				return false;
			}
		}
		return true;
	}
}
