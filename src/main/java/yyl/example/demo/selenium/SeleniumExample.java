package yyl.example.demo.selenium;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Selenium是一个用于Web应用程序测试的工具。支持包括Chrome和Firefox在内的主流浏览器。<br>
 * Chromedriver下载： <a href="https://googlechromelabs.github.io/chrome-for-testing/">chromedriver</a><br>
 */
public class SeleniumExample {
	public static void main(String[] args) {

		// 创建WebDriver实例
		RemoteWebDriver driver = SeleniumUtil.getChromeDriver();
		try {
			// 导航到目标网站
			driver.get("https://www.baidu.com/");

			// 输入关键词"selenium"
			WebElement searchInput = driver.findElement(By.id("kw"));
			searchInput.sendKeys("selenium");

			// 点击搜索按钮
			WebElement searchButton = driver.findElement(By.id("su"));
			searchButton.click();

			// 等待搜索结果加载完成(设置超时时间为10秒)
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#content_left div.result")));

			// 设置浏览器窗口的尺寸
			int scrollWidth = ((Number) driver.executeScript("return document.documentElement.scrollWidth")).intValue();
			int scrollHeight = ((Number) driver.executeScript("return document.documentElement.scrollHeight")).intValue();
			driver.manage().window().setSize(new Dimension(scrollWidth, scrollHeight));

			// 截图
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(scrFile, new File("D:/screenshot" + System.currentTimeMillis() + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			// 关闭窗口
			driver.close();
		} finally {
			// 关闭ChromeDriver
			driver.quit();
		}
	}
}
