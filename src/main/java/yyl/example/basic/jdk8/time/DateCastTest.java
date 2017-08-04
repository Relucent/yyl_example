package yyl.example.basic.jdk8.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 日期对象的转换(通过Instant当中介)
 */
public class DateCastTest {

	public static void main(String[] args) {
		testDateToLocalDateTime();
		testDateToLocalDate();
		testDateToLocalTime();
		testLocalDateTimeToDate();
		testLocalDateToDate();
		testLocalTimeToDate();
	}

	// 01. java.util.Date --> java.time.LocalDateTime
	private static void testDateToLocalDateTime() {
		Date date = new Date();
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		System.out.println(localDateTime);
	}

	// 02. java.util.Date --> java.time.LocalDate
	private static void testDateToLocalDate() {
		Date date = new Date();
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		LocalDate localDate = localDateTime.toLocalDate();
		System.out.println(localDate);
	}

	// 03. java.util.Date --> java.time.LocalTime
	private static void testDateToLocalTime() {
		Date date = new Date();
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		LocalTime localTime = localDateTime.toLocalTime();
		System.out.println(localTime);
	}

	// 04. java.time.LocalDateTime --> java.util.Date
	private static void testLocalDateTimeToDate() {
		LocalDateTime localDateTime = LocalDateTime.now();
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		Date date = Date.from(instant);
		System.out.println(date);

	}

	// 05. java.time.LocalDate --> java.util.Date
	private static void testLocalDateToDate() {
		LocalDate localDate = LocalDate.now();
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
		Date date = Date.from(instant);
		System.out.println(date);
	}

	// 06. java.time.LocalTime --> java.util.Date
	private static void testLocalTimeToDate() {
		// LocalTime只有时间没有日期，所以需要给他加上日期
		LocalTime localTime = LocalTime.now();
		LocalDate localDate = LocalDate.now();
		LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		Date date = Date.from(instant);
		System.out.println(date);
	}
}
