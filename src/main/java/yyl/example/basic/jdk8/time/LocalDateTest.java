package yyl.example.basic.jdk8.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * LocalTime只包含日期
 */
public class LocalDateTest {
	public static void main(String[] args) {

		LocalDate sparkler = LocalDate.of(1997, 9, 12);
		System.out.println(sparkler);

		LocalDate kestrel = LocalDate.parse("2000-05-08");
		System.out.println(kestrel);

		try {
			LocalDate.parse("2017-02-29");//无效日期
		} catch (DateTimeParseException e) {
			System.out.println(e);
		}

		//获得当前日期
		LocalDate today = LocalDate.now();
		System.out.println("today->" + today);

		//取本月第1天
		LocalDate firstDayOfThisMonth = today.with(TemporalAdjusters.firstDayOfMonth());
		System.out.println("firstDayOfThisMonth->" + firstDayOfThisMonth);

		//取本月第2天
		LocalDate secondDayOfThisMonth = today.withDayOfMonth(2);
		System.out.println("secondDayOfThisMonth->" + secondDayOfThisMonth);

		// 取本月最后一天
		LocalDate lastDayOfThisMonth = today.with(TemporalAdjusters.lastDayOfMonth());
		System.out.println("lastDayOfThisMonth->" + lastDayOfThisMonth);

		// 获得下一天
		LocalDate nextDay = today.plusDays(1);
		System.out.println("nextDay->" + nextDay);

		//获得本月第一个周一
		LocalDate firstInMonth = today.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
		System.out.println("firstInMonth->" + firstInMonth);

		//比较两个日期之前时间差
		System.out.println(firstDayOfThisMonth.until(lastDayOfThisMonth, ChronoUnit.MONTHS));
		System.out.println(firstDayOfThisMonth.until(lastDayOfThisMonth, ChronoUnit.DAYS));

		//因为LocalDate只包含日期，所以求小时差会抛出异常
		//System.out.println(firstDayOfThisMonth.until(lastDayOfThisMonth, ChronoUnit.HOURS));
	}
}
