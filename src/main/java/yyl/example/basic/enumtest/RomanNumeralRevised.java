package yyl.example.basic.enumtest;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 这是上一个演示的修改，内部类的定义的静态变量map会在枚举构造之前初始化(虽然不建议这么写，但是程序使可以正常执行的)。
 */
public enum RomanNumeralRevised {

	I(1), V(5), X(10), L(50), C(100), D(500), M(1000);

	static class Internal {
		public static Map<Integer, RomanNumeralRevised> map = new LinkedHashMap<Integer, RomanNumeralRevised>();
	}

	public final int val;

	RomanNumeralRevised(int val) {
		this.val = val;
		storeInMap();
	}

	private void storeInMap() {
		Internal.map.put(val, this);
	}

	public static RomanNumeralRevised fromInt(int val) {
		return Internal.map.get(val);
	}

	public static void main(String[] args) {
		int sum = 0;
		RomanNumeralRevised e = null;
		for (int i = 0; i < 1000; i++) {
			if ((e = fromInt(i)) != null) {
				System.out.println(e);
				sum += i;
			}
		}
		System.out.println(sum);
	}
}
