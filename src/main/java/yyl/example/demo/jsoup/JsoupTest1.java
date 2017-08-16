package yyl.example.demo.jsoup;

import java.io.IOException;

import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * JSOUP 是一款Java 的HTML解析器，可直接解析某个URL地址、HTML文本内容。<br>
 * 它提供了一套非常省力的API，可通过DOM，CSS的操作方法来取出和操作数据。<br>
 */
public class JsoupTest1 {
	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.connect("http://www.360.com/")//
				.method(Method.GET)//	
				.timeout(5000)//
				.header("Host", "www.360.com")//
				.header("User-Agent", "text/html; charset=utf-8")//
				.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")//
				.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")//
				.header("Connection", "keep-alive")//
				.execute()//
				.parse();
		System.out.println(doc);
	}
}
