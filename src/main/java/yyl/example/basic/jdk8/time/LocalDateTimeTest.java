package yyl.example.basic.jdk8.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * 日期+时间
 */
public class LocalDateTimeTest {
	public static void main(String[] args) {
		LocalDateTime now = LocalDateTime.now();
		System.out.println(now);

		LocalDateTime century = LocalDateTime.of(1999, 12, 31, 23, 23, 59, 5 * 1000000);
		System.out.println(century.format(DateTimeFormatter.ISO_DATE_TIME));

		LocalDateTime newCentury = LocalDateTime.parse("2100-01-01T00:00:00");
		System.out.println(newCentury.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS")));

		//LocalDateTime -> LocalDate
		LocalDate today = now.atZone(ZoneId.systemDefault()).toLocalDate();
		System.out.println(today);

		//获得下个周一
		LocalDateTime nextWeekMonday = now.plus(1, ChronoUnit.WEEKS)//
				.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY))//
				.withHour(0)//
				.withMinute(0)//
				.withSecond(0)//
				.withNano(0);

		System.out.println("nextWeekMonday->" + nextWeekMonday);

		//计算时间差
		System.out.println(now.until(nextWeekMonday, ChronoUnit.DAYS));
		System.out.println(now.until(nextWeekMonday, ChronoUnit.HALF_DAYS));
	}
}
