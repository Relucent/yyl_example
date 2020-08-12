package yyl.example.demo.quartz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.CronExpression;

/**
 * CronExpression
 */
public class CronExpressionExample {
	public static void main(String[] args) throws Throwable {
		DateFormat format = new SimpleDateFormat("HH:mm:ss");
		CronExpression expression = new CronExpression("0 */1 * * * ?");
		// 任意一个时间
		Date from = new Date();
		// 获得下一次满足条件的时间
		Date after = expression.getNextValidTimeAfter(from);
		System.out.println(format.format(from));
		System.out.println(format.format(after));
	}
}
