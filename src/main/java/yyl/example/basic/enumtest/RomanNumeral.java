package yyl.example.basic.enumtest;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 这是个错误的演示,因为map的初始化时间在枚举之后，所以storeInMap会出现空指针异常(NullPointerException)
 */
public enum RomanNumeral {

	I(1), V(5), X(10), L(50), C(100), D(500), M(1000);
	private static Map<Integer, RomanNumeral> map = new LinkedHashMap<Integer, RomanNumeral>();
	public final int val;

	RomanNumeral(int val) {
		this.val = val;
		storeInMap();
	}

	private void storeInMap() {
		map.put(val, this);
	}

	public static RomanNumeral fromInt(int val) {
		return map.get(val);
	}

	public static void main(String[] args) {
		int sum = 0;
		RomanNumeral e = null;
		for (int i = 0; i < 1000; i++) {
			if ((e = fromInt(i)) != null) {
				System.out.println(e);
				sum += i;
			}
		}
		System.out.println(sum);
	}
}
