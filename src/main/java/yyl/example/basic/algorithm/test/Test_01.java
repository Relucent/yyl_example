/*
 * 题目: 
 *
 * 写个方法 int account(int p)
 * 参数p 
 * 为 1  2  3  4  5  6   返回 1
 * 为 7  8  9 10 11 12   返回 7
 * 为 13 14 15 16 17 18  返回 13
 * 为 19 20 21 22 23 24  返回 19
 * 为 25 26 27 28 29 30  返回 25
 * 
 */

package yyl.example.basic.algorithm.test;

public class Test_01 {
	public static int account(int p) {
		return (p - (p - 1) % 6);
	}

	// 测试
	public static void main(String[] ages) {
		for (int i = 0; i < 30; i++) {
			System.out.println(i + "	:	" + account(i));
		}
	}

}
