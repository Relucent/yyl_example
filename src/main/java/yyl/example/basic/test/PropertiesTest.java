package yyl.example.basic.test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Properties;

/**
 * 简单的面向行的格式从输入字符流中读取属性列表（键和元素对）<br>
 * 忽略空白的行和注释的行('#' 或 '!' 作为其首个非空白字符的行)<br>
 * 键值之间使用第一个非转义的 : = 或者非行结束符的空白字符作为分割(如果键中存在特殊字符 : = 或者空白字符， 需要使用 \转义)<br>
 * 非行结束符的空白字符包括 空格(' '，'\u0020')、制表符('\t'，'\u0009')和换页符('\f'，'\u000C')<br>
 * 自然行定义为通过行结束符字符集(\n 或 \r 或 \r\n)，或者通过流的结尾来终止的字符行<br>
 * {@link Properties#load(java.io.Reader)}
 */

public class PropertiesTest {

	public static void main(String[] args) throws IOException {

		Properties properties = new Properties();

		StringReader reader = new StringReader("" //
				+ " !a.b.0:==VALUE\n"//
				+ " a.\\ b .1 :==VALUE\n"//
				+ "a.b.2:==VALUE\n"//
				+ "  a.b.3 = VALUE\n"//
				+ "  a.b.4 :VALUE  \n"//结尾的空格不会被去掉
				+ "  a.b.5 =:VALUE\n"//
				+ "  a.b.6 ==VALUE\n"//
				+ "  a.b.7 VALUE 123\n"//
				+ "a.b.8=\'VALUE 123\'\n"//
				+ "");

		properties.load(reader);

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			System.out.println(entry.getKey() + "|^" + entry.getValue() + "$");
		}

	}

}
