package yyl.example.basic.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeZoneTest {
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateTime = sdf.parse("2017-10-24 00:00:00");
		TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
		long chineseMills = dateTime.getTime() + timeZone.getRawOffset();
		Date chineseDateTime = new Date(chineseMills);
		System.out.println(sdf.format(chineseDateTime));
	}
}
