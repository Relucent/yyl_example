package yyl.example.exercise.time;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

	/* ============================================METHOD============================================ */
	public static Calendar create(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/* ============================================METHOD============================================ */
	/**
	 * 得到某一时间指定周期的起始时间
	 * @param calendar 指定的时间
	 * @param period 指定的周期类型
	 * @return 指定时间指定周期的起始时间
	 */
	public static Calendar getStart(Calendar cal, TimePeriod field) {
		Calendar start = Calendar.getInstance();
		switch (field) {
		case YEAR: {//年
			int year = cal.get(Calendar.YEAR);
			start.set(year, Calendar.JANUARY, 1, 0, 0, 0);//_年1月0日0时0分0秒
			break;
		}
		case HALFYEAR: {//半年
			int halfYear = getFieldValue(cal, TimePeriod.HALFYEAR);
			int year = cal.get(Calendar.YEAR);
			int month = halfYear == 0 ? Calendar.JANUARY : Calendar.JULY;//[01月|07月]
			start.set(year, month, 1, 0, 0, 0);//年月日时分秒
			break;
		}
		case QUARTER: {//季度
			int year = cal.get(Calendar.YEAR);
			int quarter = getFieldValue(cal, TimePeriod.QUARTER); // 季度
			int month = quarter * 3;// 季度的开始月 JANUARY_01|APRIL_04|JULY_07|OCTOBER_10
			start.set(year, month, 1, 0, 0, 0);//年月日时分秒
			break;
		}
		case MONTH: {//月份
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			start.set(year, month, 1, 0, 0, 0);//年月日时分秒
			break;
		}
		case FULL: //全周期
		case DATE: {//日期
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int date = cal.get(Calendar.DATE);
			start.set(year, month, date, 0, 0, 0);//年月日时分秒
			break;
		}
		default:
			//Ignore
		}
		return start;
	}
	/**
	 * 得到某一时间指定周期的結束时间
	 * @param calendar 指定的时间
	 * @param period 指定的周期类型
	 * @return 指定时间指定周期的結束时间
	 */
	public static Calendar getEnd(Calendar cal, TimePeriod field) {
		Calendar end = Calendar.getInstance();//calendar.clone();
		switch (field) {
		case YEAR: { //年
			int year = cal.get(Calendar.YEAR);
			end.set(year, Calendar.DECEMBER, 31, 23, 59, 59);//_年12月31日23时59分59秒
			break;
		}
		case HALFYEAR: {//半年
			int halfYear = getFieldValue(cal, TimePeriod.HALFYEAR);
			int year = cal.get(Calendar.YEAR);
			int month = halfYear == 0 ? Calendar.JUNE : Calendar.DECEMBER;//[06月|12月]
			end.set(Calendar.YEAR, year);//年
			end.set(Calendar.MONTH, month);//月
			end.set(Calendar.DATE, 1);
			int dayOfEndMonth = end.getActualMaximum(Calendar.DAY_OF_MONTH);//该月天数
			end.set(Calendar.DATE, dayOfEndMonth);// 设置季度结束日期的日子
			end.set(Calendar.HOUR_OF_DAY, 23); // 时
			end.set(Calendar.MINUTE, 59);// 分
			end.set(Calendar.SECOND, 59);// 秒
			break;
		}
		case QUARTER: {//季度
			int quarter = getFieldValue(cal, TimePeriod.QUARTER);// 季度
			int endMonth = quarter * 3 + 2;// 季度的结束月 MARCH_03|JUNE_06|SEPTEMBER_09|DECEMBER_12
			int year = cal.get(Calendar.YEAR);
			end.set(Calendar.YEAR, year);
			end.set(Calendar.MONTH, endMonth);
			end.set(Calendar.DATE, 1);
			int dayOfEndMonth = end.getActualMaximum(Calendar.DAY_OF_MONTH);//该月天数
			end.set(Calendar.DATE, dayOfEndMonth);// 设置季度结束日期的日子
			end.set(Calendar.HOUR_OF_DAY, 23); // 时
			end.set(Calendar.MINUTE, 59);// 分
			end.set(Calendar.SECOND, 59);// 秒
			break;
		}
		case MONTH: {//月份
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			end.set(Calendar.YEAR, year);
			end.set(Calendar.MONTH, month);
			end.set(Calendar.DATE, 1);
			int dayOfEndMonth = end.getActualMaximum(Calendar.DAY_OF_MONTH);//该月天数
			end.set(Calendar.DATE, dayOfEndMonth);// 设置季度结束日期的日子
			end.set(Calendar.HOUR_OF_DAY, 23); // 时
			end.set(Calendar.MINUTE, 59);// 分
			end.set(Calendar.SECOND, 59);// 秒
			break;
		}
		case FULL: //全周期
		case DATE: {//日期
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int date = cal.get(Calendar.DATE);
			end.set(year, month, date, 23, 59, 59);//年月日时分秒
			break;
		}
		default:
			//Ignore
		}
		return end;
	}

	/**
	 * 返回给定日历给定周期类型字段的值。
	 * @param calendar 时间
	 * @param field 时间周期
	 * @return 所在季度(0-1)
	 */
	public static int getFieldValue(Calendar cal, TimePeriod field) {
		switch (field) {
		case FULL: //全周期
			return -1;
		case YEAR: //年
			return cal.get(Calendar.YEAR);
		case HALFYEAR://半年
			return cal.get(Calendar.MONTH) < Calendar.JULY ? 0 : 1;//小于[7月]
		case QUARTER://季度
			return cal.get(Calendar.MONTH) / 3;
		case MONTH://月份
			return cal.get(Calendar.MONTH);
		case DATE://日期
			return cal.get(Calendar.DAY_OF_MONTH);
		default:
			return -1;
		}
	}
}
