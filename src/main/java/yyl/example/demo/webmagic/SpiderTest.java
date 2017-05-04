package yyl.example.demo.webmagic;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.processor.example.BaiduBaikePageProcessor;

/**
 * @see BaiduBaikePageProcessor
 */
public class SpiderTest {
	public static void main(String[] args) {
		final Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setUseGzip(true);
		Spider spider = Spider.create(new PageProcessor() {
			@Override
			public void process(Page page) {
				page.putField("name", page.getHtml().css("dl.lemmaWgt-lemmaTitle h1", "text").toString());
				page.putField("description", page.getHtml().xpath("//div[@class='lemma-summary']/allText()"));
			}

			@Override
			public Site getSite() {
				return site;
			}

		}).thread(2);
		String urlTemplate = "http://baike.baidu.com/search/word?word=%s&pic=1&sug=1&enc=utf8";
		List<String> list = new ArrayList<String>();
		list.add(String.format(urlTemplate, "石墨烯"));
		list.add(String.format(urlTemplate, "气凝胶"));
		list.add(String.format(urlTemplate, "液态金属"));
		list.add(String.format(urlTemplate, "生物塑料"));
		list.add(String.format(urlTemplate, "形状记忆合金"));
		list.add(String.format(urlTemplate, "纳米纤维"));
		List<ResultItems> resultItemses = spider.<ResultItems> getAll(list);
		for (ResultItems resultItemse : resultItemses) {
			System.out.println(resultItemse.getAll());
		}
		spider.close();
	}
}
