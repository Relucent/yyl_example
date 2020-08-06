package yyl.example.demo.htmlunit;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 模仿浏览器抓取数据(含AJAX) <br>
 */
public class HtmlUnitTest2 {

	public static void main(String[] args) throws Exception {
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_68);
		webClient.setCssErrorHandler(new SilentCssErrorHandler());
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setCssEnabled(true);
		webClient.getOptions().setRedirectEnabled(false);
		webClient.getOptions().setAppletEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setPopupBlockerEnabled(true);
		webClient.getOptions().setTimeout(10000);
		webClient.getOptions().setThrowExceptionOnScriptError(false); // JS运行错误时，是否抛出异常
		HtmlPage page = webClient.getPage("http://huaban.com/favorite/home/");
		System.out.println(page.asXml());
		webClient.close();
	}

}
