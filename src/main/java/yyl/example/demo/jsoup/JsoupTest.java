package yyl.example.demo.jsoup;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * JSOUP 是一款Java 的HTML解析器，可直接解析某个URL地址、HTML文本内容。<br>
 * 它提供了一套非常省力的API，可通过DOM，CSS的操作方法来取出和操作数据。<br>
 */
public class JsoupTest {
	public static void main(String[] args) throws IOException {
		String url = "http://www.baidu.com";
		Document doc = Jsoup.connect(url).get();

		System.out.println(doc.title());

		Element kw = doc.getElementById("kw");
		System.out.println(kw.className());

	}
}
