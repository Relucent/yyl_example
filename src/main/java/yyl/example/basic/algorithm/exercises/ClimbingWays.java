package yyl.example.basic.algorithm.exercises;

/**
 * 问题：<br>
 * 有一座高度是 N 级台阶的楼梯，从下往上走，每一步只能向上1级或者2级台阶。<br>
 * 问一共有多少种走法。<br>
 */
public class ClimbingWays {

	public static void main(String[] args) {
		int n = 10;

		System.out.println(getClimbingWays(n));
		System.out.println(getClimbingWays2(n));

	}

	/**
	 * 使用递归方式计算走法，该方法便于理解，时间复杂度为 O(2^N)
	 * @param n 剩余多少阶台阶
	 * @return 一共多少种走法
	 */
	static int getClimbingWays(int n) {
		//没有台阶
		if (n < 1) {
			return 0;
		}
		//只有1阶台阶，一种走法
		if (n == 1) {
			return 1;//1->E
		}
		//只有2阶台阶，两种走法
		if (n == 2) {
			return 2;// 1->1->E , 2->E
		}
		//第一步走1阶 和 第二步走2阶 两种走法总数加起来
		return getClimbingWays(n - 1) + getClimbingWays(n - 2);
	}

	/**
	 * 递推方式计算走法 ，时间复杂度 O(N)
	 * @param n 剩余多少阶台阶
	 * @return 一共多少种走法
	 */
	static int getClimbingWays2(int n) {
		//没有台阶
		if (n < 1) {
			return 0;
		}
		//只有1阶台阶，一种走法
		if (n == 1) {
			return 1;//1->E
		}
		//只有2阶台阶，两种走法
		if (n == 2) {
			return 2;// 1->1->E , 2->E
		}

		//|[归纳法]
		//|台阶数：1 2 3 4 5 ...
		//|走法数：1 2 3 5 8 ...
		//|F(N) = F(N-1) + F(N-2)

		int a = 1; //F(N-2)
		int b = 2; //F(N-1)
		int result = 0;
		//从 i=3 开始迭代，每次迭代计算 多一级的走法数量
		for (int i = 3; i <= n; i++) {
			result = a + b;//
			a = b;
			b = result;
		}
		return result;
	}
}
