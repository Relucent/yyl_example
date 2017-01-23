package yyl.example.demo.crawler4j;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class MyControllerTest {
	public static void main(String[] args) throws Exception {

		//#爬虫配置
		CrawlConfig config = new CrawlConfig();

		//#设置爬取深度
		config.setMaxDepthOfCrawling(5);
		//#设置页面抓取的最大数量 
		config.setMaxPagesToFetch(Integer.MAX_VALUE);
		//每次请求前等待200毫秒
		config.setPolitenessDelay(200);

		//爬取数据存储文件夹
		config.setCrawlStorageFolder(System.getProperty("user.dir") + "/temp/crawl");

		//#设置代理
		//config.setProxyHost("proxyserver.example.com");
		//config.setProxyPort(8080);
		//config.setProxyUsername(username);
		//config.getProxyPassword(password);

		//#配置恢复停止/崩溃的爬虫
		//config.setResumableCrawling(true);

		//#实例化控制器 
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		//#种子网址
		controller.addSeed("http://www.ics.uci.edu/~lopes/");
		controller.addSeed("http://www.ics.uci.edu/~welling/");
		controller.addSeed("http://www.ics.uci.edu/");

		//爬虫并发数
		int numberOfCrawlers = 7;

		//#开始爬取网页(阻塞操作)
		controller.start(MyCrawler.class, numberOfCrawlers);
	}
}
