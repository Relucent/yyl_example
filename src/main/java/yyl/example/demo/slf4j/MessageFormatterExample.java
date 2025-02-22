package yyl.example.demo.slf4j;

import java.time.LocalDate;

import org.slf4j.helpers.MessageFormatter;

/**
 * 使用 SLF4J 日志框架的 MessageFormatter 来拼装字符串
 */
public class MessageFormatterExample {
	public static void main(String[] args) {
		String template = "hello, {}! today is {}";
		String result = MessageFormatter.arrayFormat(template, new Object[] { "slf4j", LocalDate.now() }).getMessage();
		System.out.println(result);
	}
}
