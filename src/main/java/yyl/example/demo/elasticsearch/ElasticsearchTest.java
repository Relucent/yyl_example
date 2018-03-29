package yyl.example.demo.elasticsearch;

import java.util.Collections;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

public class ElasticsearchTest {

	private final static String HOST = "127.0.0.1";
	private final static int PORT = 9200;
	private final static String SCHEME = "http";

	public static void main(String[] args) throws Exception {
		try (RestClient client = RestClient.builder(new HttpHost(HOST, PORT, SCHEME)).build()) {
			info(client);
			catApi(client);
			if (!existsIndices(client)) {
				createIndex(client);
			}
			createDocument(client);
			pause();
			createDocumentPost(client);
			pause();
			getDocument(client);
			pause();
			queryByField(client);
			pause();
			deleteDocument(client);
			pause();
			queryAll(client);
			pause();
			deleteIndex(client);
		}
	}

	/** 验证ES集群是否搭建成功 */
	private static void info(RestClient client) throws Exception {
		// curl -XGET "http://localhost:9200/_count?pretty"
		Response response = client.performRequest("GET", "/", Collections.singletonMap("pretty", "true"));
		System.out.println(EntityUtils.toString(response.getEntity()));
	}

	/** 查看API信息 */
	private static void catApi(RestClient client) throws Exception {
		Response response = client.performRequest("GET", "/_cat");
		System.out.println(EntityUtils.toString(response.getEntity()));
	}

	/** 创建索引 */
	private static void createIndex(RestClient client) throws Exception {
		Response response = client.performRequest("PUT", "/test-index");
		System.out.println(EntityUtils.toString(response.getEntity()));
	}

	/** 查询索引是否存在 */
	private static boolean existsIndices(RestClient client) throws Exception {
		Response response = client.performRequest("HEAD", "/test-index", Collections.<String, String>emptyMap());
		boolean exists = response.getStatusLine().getReasonPhrase().equals("OK");
		System.out.println(exists);
		return exists;
	}

	/** 删除索引 */
	private static void deleteIndex(RestClient client) throws Exception {
		Response response = client.performRequest("DELETE", "/test-index");
		System.out.println(EntityUtils.toString(response.getEntity()));
	}

	/** 创建文档 */
	private static void createDocument(RestClient client) throws Exception {
		HttpEntity entity = new NStringEntity("{\"name\":\"hello\",\"value\":\"world\"}", ContentType.APPLICATION_JSON);
		Response response = client.performRequest("PUT", "/test-index/test/1", Collections.<String, String>emptyMap(), entity);
		System.out.println(EntityUtils.toString(response.getEntity()));
	}

	/** 创建文档 */
	// PUT方法 :在这个URL中存储文档
	// POST方法:在这个类型下存储文档
	private static void createDocumentPost(RestClient client) throws Exception {
		HttpEntity entity = new NStringEntity("{\"name\":\"welcome\",\"value\":\"universe\"}", ContentType.APPLICATION_JSON);
		Response response = client.performRequest("POST", "/test-index/test", Collections.<String, String>emptyMap(), entity);
		System.out.println(EntityUtils.toString(response.getEntity()));
	}

	/** 获取文档 */
	private static void getDocument(RestClient client) throws Exception {
		Response response = client.performRequest("GET", "/test-index/test/1");
		System.out.println(EntityUtils.toString(response.getEntity()));
	}

	/** 获取文档 */
	private static void queryAll(RestClient client) throws Exception {
		HttpEntity entity = new NStringEntity("{" //
				+ "  \"query\": {" //
				+ "    \"match_all\": {}" //
				+ "  }\n" //
				+ "}", ContentType.APPLICATION_JSON);
		Response response = client.performRequest("GET", "/test-index/test/_search", Collections.<String, String>emptyMap(), entity);
		System.out.println(EntityUtils.toString(response.getEntity()));
	}

	/** 获取文档 */
	private static void queryByField(RestClient client) throws Exception {
		HttpEntity entity = new NStringEntity("{" //
				+ "  \"query\": {" //
				+ "    \"match\": {" //
				+ "      \"name\": \"welcome\"" //
				+ "    }" //
				+ "  }" //
				+ "}", ContentType.APPLICATION_JSON);

		Response response = client.performRequest("GET", "/test-index/test/_search", Collections.<String, String>emptyMap(), entity);
		System.out.println(EntityUtils.toString(response.getEntity()));
	}

	/** 删除文档 */
	private static void deleteDocument(RestClient client) throws Exception {
		Response response = client.performRequest("DELETE", "/test-index/test/1", Collections.<String, String>emptyMap());
		System.out.println(EntityUtils.toString(response.getEntity()));
	}

	/** 暂停 */
	private static void pause() throws InterruptedException {
		Thread.sleep(500);
	}
}
