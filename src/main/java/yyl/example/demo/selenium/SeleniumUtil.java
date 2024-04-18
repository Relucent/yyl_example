package yyl.example.demo.selenium;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

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
public class SeleniumUtil {

	// SeleniumServer访问地址
	public static final String SERVER_URL = "http://127.0.0.1:4444/wd/hub";

	// ChromeDriver的路径
	public static final String LOCAL_DRIVER = "D:/Software/chromedriver-win64/chromedriver.exe";

	// Chrome 浏览器路径
	public static final String LOCAL_CHROME = "D:/Software/chrome-headless-shell-win64/chrome-headless-shell.exe";

	// 文件下载地址
	public static final String DOWNLOADS_DIRECTORY = "D:/downloads/";

	public static ChromeDriver getChromeDriver() {

		ChromeOptions options = getChromeOptions();

		// 设置ChromeDriver的路径
		System.setProperty("webdriver.chrome.driver", LOCAL_DRIVER);

		// 设置 CHROME 路径
		options.setBinary(LOCAL_CHROME);

		// 创建WebDriver实例
		return new ChromeDriver(options);
	}

	public static RemoteWebDriver getRemoteWebDriver(ChromeOptions options) {

		URL serverUrl;
		try {
			serverUrl = new URL(SERVER_URL);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}

		// 设置所需的浏览器以及任何其他选项
		DesiredCapabilities capabilities = new DesiredCapabilities();

		/// 设置Chrome浏览器的选项
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);

		// 实例化远程WebDriver对象
		return new RemoteWebDriver(serverUrl, capabilities);
	}

	public static ChromeOptions getChromeOptions() {

		// 创建ChromeOptions对象
		ChromeOptions options = new ChromeOptions();

		// 启用无头模式
		options.addArguments("--headless");
		// 禁用弹出窗口阻止
		options.addArguments("--disable-popup-blocking");
		// 禁用扩展
		options.addArguments("--disable-extensions");
		// 禁用通知
		options.addArguments("--disable-notifications");
		// 禁用 GPU 加速
		options.addArguments("--disable-gpu");
		// 不使用沙盒模式运行
		options.addArguments("--no-sandbox");
		// 禁用 /dev/shm 的使用
		options.addArguments("--disable-dev-shm-usage");
		// 禁用 setuid sandbox
		options.addArguments("--disable-setuid-sandbox");
		// 禁用 Web 安全性
		options.addArguments("--disable-web-security");
		// 允许运行不安全的内容
		options.addArguments("--allow-running-insecure-content");
		// 忽略证书错误
		options.addArguments("--ignore-certificate-errors");
		// 允许跨域访问
		options.addArguments("--remote-allow-origins=*");
		// 启用 NetworkService 和 NetworkServiceInProcess 功能
		options.addArguments("--enable-features=NetworkService,NetworkServiceInProcess");

		// 设置下载地址
		Map<String, String> prefs = new HashMap<>();
		prefs.put("download.default_directory", new File(DOWNLOADS_DIRECTORY).getAbsolutePath());
		prefs.put("download.prompt_for_download", "false");
		options.setExperimentalOption("prefs", prefs);

		return options;
	}
}
