package yyl.example.basic.test;

/**
 * 浮点数采用“尾数+阶码”的编码方式，类似于科学计数法的“有效数字+指数”的表示方式。
 */
public class FloatTest {
	public static void main(String[] args) {
		float a = 1.0f - 0.9f;
		float b = 0.9f - 0.8f;

		// 二进制无法精确表示大部分的十进制小数。
		System.out.println(a);
		System.out.println(b);
		System.out.println(a == b);

		// 指定一个误差范围，两个浮点数的差值在此范围之内，则认为是相等的。
		float diff = 1e-6f;
		System.out.println(Math.abs(a - b) < diff);
	}
}
