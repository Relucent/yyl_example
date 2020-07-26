package yyl.example.basic.algorithm.bit;

/**
 * 求掩码
 */
public class SubnetMaskExample {
	public static void main(String[] args) {
		int p = 0b10101010;
		for (int m = p; m > 0; m = (m - 1) & p) {
			System.out.println(Integer.toBinaryString(m));
		}
	}
}
