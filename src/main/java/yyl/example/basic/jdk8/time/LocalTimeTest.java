package yyl.example.basic.jdk8.time;

import java.time.LocalTime;

/**
 * LocalTime只包含时间(精确到毫秒)
 */
public class LocalTimeTest {
	public static void main(String[] args) {
		LocalTime now = LocalTime.now();
		System.out.println(now);

		LocalTime zero = LocalTime.of(0, 0, 0); // 00:00:00
		System.out.println(zero);

		LocalTime middle = LocalTime.parse("12:00:00"); // 12:00:00
		System.out.println(middle);

		//清除毫秒
		LocalTime now2 = now.withNano(0);
		System.out.println(now2);
	}
}
