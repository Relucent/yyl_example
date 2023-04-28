package yyl.example.demo.jvppeteer;

import java.util.ArrayList;

import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;

/**
 * Jvppeteer 通过 DevTools 控制 Chrome。 <br>
 * 类似于 Puppeteer(Node.js), API 也与其基本上保持一致，默认情况下，以 headless 模式运行，也可以通过配置运行'有头'模式。<br>
 * 在浏览器中手动执行的绝大多数操作都可以使用 Jvppeteer 来完成。 <br>
 * https://github.com/fanyong920/jvppeteer
 */
public class JvppeteerExample {

    public static void main(String[] args) throws Exception {

        ArrayList<String> argList = new ArrayList<>();
        argList.add("--no-sandbox");
        argList.add("--disable-setuid-sandbox");

        // 浏览器路径（如果自动下载，此处不用设置）
        String executablePath = "C:/Users/admin/AppData/Local/Google/Chrome/Application/chrome.exe";

        // 自动下载浏览器，第一次下载后不会再下载
        // BrowserFetcher.downloadIfNotExist(null);

        LaunchOptions options = new LaunchOptionsBuilder()//
                .withArgs(argList)//
                .withHeadless(true)//
                .withExecutablePath(executablePath)//
                .build();

        Browser browser = Puppeteer.launch(options);
        try {
            Page page = browser.newPage();
            page.goTo("https://www.baidu.com/");
            page.waitForSelector("body");
            String html = (String) page.evaluate("()=>{return document.documentElement.outerHTML;}");
            System.out.println(html);
        } finally {
            browser.close();
        }
    }
}
