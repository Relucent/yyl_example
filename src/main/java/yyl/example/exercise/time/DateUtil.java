package yyl.example.exercise.time;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	/* ============================================METHOD============================================ */
	/**
	 * 得到某一时间指定周期的起始时间
	 * 
	 * @param date 指定的时间
	 * @param period 指定的周期类型
	 * @return 指定时间指定周期的起始时间
	 */
	public static Date getStart(Date date, TimePeriod field) {
		if (date == null) {
			return null;
		}
		return CalendarUtil.getStart(CalendarUtil.create(date), field).getTime();
	}

	/**
	 * 得到某一时间指定周期的結束时间
	 * 
	 * @param date 指定的时间
	 * @param period 指定的周期类型
	 * @return 指定时间指定周期的結束时间
	 */
	public static Date getEnd(Date date, TimePeriod field) {
		if (date == null) {
			return null;
		}
		return CalendarUtil.getEnd(CalendarUtil.create(date), field).getTime();
	}

	/**
	 * 返回给定日历给定周期类型字段的值。
	 * 
	 * @param date 时间
	 * @param field 时间周期
	 * @return 所在季度(0-1)
	 */
	public static int getFieldValue(Date date, TimePeriod field) {
		return CalendarUtil.getFieldValue(CalendarUtil.create(date), field);
	}

	// 返回月份的最大值日期
	/**
	 * 返回某个月的最大天数
	 * @param year 年份
	 * @param month 月份(0-11)
	 * @return 最大日期数
	 */
	public static int getActualMaximumDays(int year, int month) {
		int days = 0;
		if (month < 0 || month > 11) {
			days = -1;
		} else {
			Calendar cal = new GregorianCalendar(year, month, 1);
			days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		return days;
	}

	//	public static Date getDate(int year, int month) {
	//		return new GregorianCalendar(year, month, 1).getTime();
	//	}
	//
	//	public static Date getMaximumDate(int year, int month) {
	//		return DateUtil.parse(year + "-" + (month + 1) + "-" + DateUtil.getActualMaximumDays(year, month) + " 23:59:59 999", "yyyy-MM-dd hh:mm:ss S");
	//	}
	//
	//	public static Date getMaximumDate(int year) {
	//		return DateUtil.parse(year + "-12- 23:59:59 999", "yyyy-MM-dd hh:mm:ss S");
	//	}

	//	public static Date parse(String str, String pattern) {
	//		try {
	//			String[] patterns = new String[] { pattern };
	//			return parseDate(str, patterns);
	//		} catch (ParseException e) {
	//			return null;
	//		}
	//	}

	//	public static Date parseDate(String str, String parsePatterns[]) throws ParseException {
	//		return parseDateWithLeniency(str, parsePatterns, true);
	//	}
	//
	// 
	//
	//	private static Date parseDateWithLeniency(String str, String pattern) throws ParseException {
	//		if (str == null || parsePatterns == null)
	//			throw new IllegalArgumentException("Date and Patterns must not be null");
	//		SimpleDateFormat parser = new SimpleDateFormat();
	//		parser.setLenient(true);
	//		ParsePosition pos = new ParsePosition(0);
	//
	//			if (pattern.endsWith("ZZ")){
	//				pattern = pattern.substring(0, pattern.length() - 1);}
	//			parser.applyPattern(pattern);
	//			pos.setIndex(0);
	//			String str2 = str;
	//			if (parsePatterns[i].endsWith("ZZ")) {
	//				for (int signIdx = indexOfSignChars(str2, 0); signIdx >= 0; signIdx = indexOfSignChars(str2, ++signIdx))
	//					str2 = reformatTimezone(str2, signIdx);
	//
	//			}
	//			Date date = parser.parse(str2, pos);
	//			if (date != null && pos.getIndex() == str2.length())
	//				return date;
	//		}
	//
	//		throw new ParseException("Unable to parse the date: " + str, -1);
	//	}

}