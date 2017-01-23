package yyl.example.basic.algorithm.swap;

/**
 * 不使用中间变量交换两个INT值<br>
 * 异或运算: 异或也叫半加运算，其运算法则相当于不带进位的二进制加法,如果a、b两个值不相同，则异或结果为1。如果a、b两个值相同，异或结果为0.
 */
public class CleverSwap1 {

	public static void main(String[] args) {
		int i = 1982;
		int j = 2009;

		System.out.println("i=" + i + " j=" + j);

		i = i ^ j;
		j = i ^ j;
		i = i ^ j;

		System.out.println("i=" + i + " j=" + j);
	}
}