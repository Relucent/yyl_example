package yyl.example.demo.htmlunit;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * HtmlUnit 是一款开源的 JAVA 页面分析工具，相当于一个没有界面的浏览器。<br>
 * 通过HtmlUnit不仅可以获得网页内容，还提供了对网页进行操作的方法。<br>
 */
public class HtmlUnitTest1 {
	public static void main(String[] args) {

		//得到浏览器对象
		WebClient webClient = null;

		try {

			webClient = new WebClient();

			webClient.waitForBackgroundJavaScript(60 * 1000);

			WebClientOptions options = webClient.getOptions();

			options.setCssEnabled(true);//不加载css
			options.setJavaScriptEnabled(false);// 启用JS解释器，默认为true
			options.setUseInsecureSSL(true);
			//options.setRedirectEnabled(true);
			options.setThrowExceptionOnFailingStatusCode(false);//当出现HttpError时，是否抛出异常
			options.setThrowExceptionOnScriptError(false); //JS运行错误时，是否抛出异常

			// 设置AJAX异步处理控制器(启用AJAX支持)
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());

			//拿到网页
			HtmlPage htmlpage = webClient.getPage("http://news.baidu.com/advanced_news.html");

			//根据名字得到表单(表单的名字叫“f”)
			HtmlForm form = htmlpage.getFormByName("f");

			System.out.println(form);

			//获取“百度一下”这个按钮
			HtmlSubmitInput button = form.getInputByValue("百度一下");
			System.out.println(button);

			//得到搜索框
			HtmlTextInput textField = form.getInputByName("q1");
			System.out.println(textField);

			//在搜索框内填入“HtmlUnit”
			textField.setValueAttribute("HtmlUnit");

			//点击按钮
			HtmlPage nextPage = button.click();

			//下一个页面
			System.out.println(nextPage);

			//获得页面结果
			String result = nextPage.asXml();

			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (webClient != null) {
				webClient.close();
			}
		}
	}
}
