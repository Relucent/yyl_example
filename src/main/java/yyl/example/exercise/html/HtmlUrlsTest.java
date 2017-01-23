package yyl.example.exercise.html;

/**获取百度首页www.baidu.com的所有网址和链接文字*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 根据指定的规则，通过构造正则表达式获取网址
 */

public class HtmlUrlsTest {

	private String startUrl; //开始采集网址
	private String urlContent;
	private String contentArea;

	//	采集区域开始采集字符串和结束采集字符串
	private String strAreaBegin = "<body>";
	private String strAreaEnd = "/body>";

	public static void main(String[] args) {
		HtmlUrlsTest myurl = new HtmlUrlsTest();
		myurl.getStartUrl("http://www.baidu.com/");
		myurl.getUrlContent();
		myurl.getContentArea();
		myurl.urls();

		System.out.println("startUrl:" + myurl.startUrl);
		System.out.println("urlcontent:" + myurl.urlContent);
		System.out.println("ContentArea:" + myurl.contentArea);

	}

	//获得网址所在的匹配区域部分
	public void getContentArea() {
		int pos1 = 0, pos2 = 0;
		pos1 = urlContent.indexOf(strAreaBegin) + strAreaBegin.length();
		pos2 = urlContent.indexOf(strAreaEnd, pos1);
		contentArea = urlContent.substring(pos1, pos2);
	}

	public void urls() {
		int i = 0;
		String regex = "<a.*?/a>";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(contentArea);
		while (mt.find()) {
			System.out.println(mt.group());
			i++;

			//获取标题
			Matcher title = Pattern.compile(">.*?</a>").matcher(mt.group());
			while (title.find()) {
				System.out.println("标题:" + title.group().replaceAll(">|</a>", ""));
			}

			//获取网址
			Matcher myurl = Pattern.compile("href=.*?>").matcher(mt.group());
			while (myurl.find()) {
				System.out.println("网址:" + myurl.group().replaceAll("href=|>", ""));
			}
			System.out.println();

		}
		System.out.println("共有" + i + "个符合结果");

	}

	//获得开始采集网址
	public void getStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}

	//获得网址所在内容;
	public void getUrlContent() {

		StringBuffer is = new StringBuffer();
		try {
			URL myUrl = new URL(startUrl);
			BufferedReader br = new BufferedReader(new InputStreamReader(myUrl.openStream()));

			String s;
			while ((s = br.readLine()) != null) {
				is.append(s);
			}
			urlContent = is.toString();
		}
		catch (Exception e)

		{
			System.out.println("网址文件未能输出");
			e.printStackTrace();
		}

	}

}
