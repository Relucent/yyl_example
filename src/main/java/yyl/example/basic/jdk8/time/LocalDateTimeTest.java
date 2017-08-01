package yyl.example.basic.jdk8.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期+时间
 */
public class LocalDateTimeTest {
	public static void main(String[] args) {
		LocalDateTime datetime = LocalDateTime.now();
		System.out.println(datetime);

		LocalDateTime century = LocalDateTime.of(1999, 12, 31, 23, 23, 59, 5 * 1000000);
		System.out.println(century.format(DateTimeFormatter.ISO_DATE_TIME));

		LocalDateTime newCentury = LocalDateTime.parse("2100-01-01T00:00:00");
		System.out.println(newCentury.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS")));
	}
}
