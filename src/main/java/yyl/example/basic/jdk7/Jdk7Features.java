package yyl.example.basic.jdk7;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Java7 新增特性
 */
public class Jdk7Features {

	public static void main(String[] args) throws Exception {
		testSwitch();//switch中使用string
		testGeneric();//泛型实例化类型自动推断
		testNumber();//数字字面量下划线支持 和 二进制字面量支持
		testClose();//自动资源管理
	}

	/**
	 * switch 中可以使用字串
	 */
	public static void testSwitch() {
		String s = "1";

		switch (s) {
		case "0":
			System.out.println("+0");
			break;
		case "1":
			System.out.println("+1");
			break;
		case "2":
			System.out.println("+2");
			break;
		default:
			System.out.println("default");
		}
	}

	/**
	 * 泛型实例化类型自动推断
	 */
	public static void testGeneric() {
		Map<String, List<String>> anagrams = new HashMap<>();
		System.out.println(anagrams);
	}

	/**
	 * 数字字面量下划线支持 和 二进制字面量支持
	 */
	public static void testNumber() {
		int one_million = 1_000____000;

		System.out.println(one_million);

		int binary = 0b101;
		System.out.println(binary);
	}

	/**
	 * 自动资源管理
	 */
	public static void testClose() throws Exception {
		try (AutoCloseable m = new AutoCloseable() {
			@Override
			public void close() throws Exception {
				System.out.println("close");
			}
		}) {
		}

		try (AutoCloseable m = null) {
		}
	}
}
