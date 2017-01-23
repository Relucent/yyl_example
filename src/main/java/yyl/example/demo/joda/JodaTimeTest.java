package yyl.example.demo.joda;

import org.joda.time.DateTime;

public class JodaTimeTest {
	public static void main(String[] args) {
		System.out.println(new DateTime().toString("yyyy-MM-dd"));
		System.out.println(new DateTime("1921-11-12T21:11:21").toString("yyyy-MM-dd HH:mm:ss"));
	}
}
