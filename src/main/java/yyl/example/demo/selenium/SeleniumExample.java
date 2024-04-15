package yyl.example.demo.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Selenium是一个用于Web应用程序测试的工具。支持包括Chrome和Firefox在内的主流浏览器。<br>
 * Chromedriver下载： <a href="https://googlechromelabs.github.io/chrome-for-testing/">chromedriver</a><br>
 * Selenium v4.5.0 之后不在支持JDK8 <br>
 * <br>
 * ChromeOptions类用于设置Chrome浏览器的启动选项：<br>
 * --disable-popup-blocking：禁用弹出窗口阻止。<br>
 * --disable-extensions：禁用扩展。<br>
 * --disable-notifications：禁用通知。<br>
 * --disable-gpu：禁用GPU加速。<br>
 * --no-sandbox：禁用沙盒模式。<br>
 * --disable-dev-shm-usage：禁用/dev/shm共享内存使用。<br>
 * --disable-setuid-sandbox：禁用setuid沙盒。<br>
 * --disable-web-security：禁用Web安全。<br>
 * --allow-running-insecure-content：允许运行不安全的内容。<br>
 * --ignore-certificate-errors：忽略证书错误。<br>
 * --enable-features=NetworkService,NetworkServiceInProcess：启用网络服务和进程内网络服务。<br>
 * --start-maximized：以最大化模式启动浏览器。<br>
 * --headless：以无头模式启动浏览器。<br>
 * --window-size=WIDTH,HEIGHT：设置浏览器窗口大小。<br>
 * --user-data-dir=PATH：设置用户数据目录的路径。<br>
 * --profile-directory=NAME：设置配置文件目录的名称。<br>
 * --proxy-server=PROXY：设置代理服务器。<br>
 * --incognito：以隐身模式启动浏览器。<br>
 * --lang=LANGUAGE：设置浏览器语言。<br>
 * --user-agent=USER_AGENT：设置用户代理。<br>
 */
public class SeleniumExample {
	public static void main(String[] args) {

		// 设置ChromeDriver的路径
		System.setProperty("webdriver.chrome.driver", "D:/Software/chromedriver-win64/chromedriver.exe");

		// 创建ChromeOptions对象
		ChromeOptions options = new ChromeOptions();

		// 禁用沙箱模式
		options.addArguments("--no-sandbox");

		// 启用无头模式
		options.addArguments("--headless");

		// 创建WebDriver实例
		WebDriver driver = new ChromeDriver(options);

		// 导航到目标网站
		driver.get("https://www.baidu.com/");

		// 输入关键词"selenium"
		WebElement searchInput = driver.findElement(By.id("kw"));
		searchInput.sendKeys("selenium");

		// 点击搜索按钮
		WebElement searchButton = driver.findElement(By.id("su"));
		searchButton.click();

		// 等待搜索结果加载完成(设置超时时间为10秒)
		WebDriverWait wait = new WebDriverWait(driver, 10);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#content_left div.result")));

		// 获取搜索结果列表
		List<WebElement> searchResults = driver.findElements(By.cssSelector("#content_left div.result"));

		// 打印搜索结果列表
		System.out.println("搜索结果列表：");
		for (WebElement result : searchResults) {
			System.out.println("----------");
			System.out.println(result.getText());
		}

		// 关闭窗口
		driver.close();

		// 关闭ChromeDriver
		driver.quit();
	}
}
