package yyl.example.demo.htmlunit;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 模仿浏览器抓取数据(含AJAX) <br>
 */
public class HtmlUnitTest3 {

	public static void main(String[] args) throws Exception {
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_68);
		try {
			webClient.setCssErrorHandler(new SilentCssErrorHandler());
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			webClient.getOptions().setCssEnabled(true);
			webClient.getOptions().setRedirectEnabled(false);
			webClient.getOptions().setAppletEnabled(false);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setPopupBlockerEnabled(true);
			webClient.getOptions().setTimeout(10000);
			webClient.getOptions().setThrowExceptionOnScriptError(false); // JS运行错误时，是否抛出异常
			// webClient.waitForBackgroundJavaScript(10 * 1000);
			HtmlPage page = webClient.getPage("https://www.baidu.com/");

			WebWindow webWindow = page.getEnclosingWindow();

			System.out.println("# 等待页面加载");
			waitFor(() -> {
				DomElement input = page.getElementById("kw");
				return input instanceof HtmlInput;
			});

			System.out.println("# 文本框输入 htmlunit ");
			HtmlInput kw = (HtmlInput) page.getElementById("kw");
			kw.setAttribute("value", "htmlunit");

			System.out.println("# 触发回车事件");
			Thread.sleep(1000);
			kw.type(13);// Enter

			System.out.println("# 等待页面跳转");
			Thread.sleep(1000);

			HtmlPage page2 = (HtmlPage) webWindow.getEnclosedPage();
			System.out.println(page2.getUrl());

			DomNodeList<DomNode> nodes = page2.querySelectorAll(".result.c-container h3 a");

			System.out.println("# 输出结果");
			for (DomNode node : nodes) {
				System.out.println(node.asText());
			}

		} finally {
			webClient.close();
		}

	}

	static void waitFor(WaitBreaker waitBreaker) throws InterruptedException {
		for (int i = 0; i < 30; i++) {
			if (waitBreaker.checkBreak()) {
				break;
			}
			Thread.sleep(1000);
		}
	}

	static interface WaitBreaker {
		boolean checkBreak();
	}
}
