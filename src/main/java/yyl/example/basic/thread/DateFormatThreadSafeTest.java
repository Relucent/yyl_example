package yyl.example.basic.thread;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 验证 DateFormat是否是线程安全<br>
 * 环境：JDK1.7<br>
 * 结果：高并发情况会抛出 java.lang.NumberFormatException: multiple points 异常，说明DateFormat不是线程安全的.<br>
 */
public class DateFormatThreadSafeTest {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			new Thread() {
				public void run() {
					while (true) {
						try {
							sdf.parse("2017-01-28 00:00:00");
						} catch (ParseException e) {
						}
					}
				}
			}.start();
		}
	}

}
