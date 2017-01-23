package yyl.example.basic.algorithm.swap;

/**
 * 不使用中间变量交换两个INT值 (如果数值过大，可能会出现溢出问题)
 */
public class CleverSwap2 {

	public static void main(String[] args) {
		int i = 1982;
		int j = 2009;

		System.out.println("i=" + i + " j=" + j);

		i = i + j;
		j = i - j;
		i = i - j;

		System.out.println("i=" + i + " j=" + j);
	}
}