package yyl.example.demo.elasticsearch;

import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

public class ElasticsearchTest {

    private final static String HOST = "127.0.0.1";
    private final static int PORT = 9200;
    private final static String SCHEME = "http";
    private final static String INDEX = "test";
    private final static String TYPE = INDEX;

    public static void main(String[] args) throws Exception {
        HttpHost[] hosts = {new HttpHost(HOST, PORT, SCHEME)};
        try (RestClient client = RestClient.builder(hosts).build()) {
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

    // 验证ES集群是否搭建成功
    private static void info(RestClient client) throws Exception {
        // curl -X GET "http://localhost:9200/_count?pretty"
        Request request = new Request("GET", "");
        request.addParameter("pretty", "true");
        Response response = client.performRequest(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    // 查看API信息
    private static void catApi(RestClient client) throws Exception {
        Request request = new Request("GET", "_cat");
        Response response = client.performRequest(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    // 创建索引
    private static void createIndex(RestClient client) throws Exception {
        Request request = new Request("PUT", INDEX);
        Response response = client.performRequest(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    // 查询索引是否存在
    private static boolean existsIndices(RestClient client) throws Exception {
        Request request = new Request("HEAD", INDEX);
        Response response = client.performRequest(request);
        boolean exists = response.getStatusLine().getReasonPhrase().equals("OK");
        System.out.println(exists);
        return exists;
    }

    // 删除索引
    private static void deleteIndex(RestClient client) throws Exception {
        Request request = new Request("DELETE", INDEX);
        Response response = client.performRequest(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    // 创建文档
    // PUT方法 :在这个URL中存储文档
    private static void createDocument(RestClient client) throws Exception {
        Request request = new Request("PUT", INDEX + "/" + TYPE + "/1");
        request.setEntity(new NStringEntity("{" //
                + "\"name\":\"hello\"," //
                + "\"value\":\"world\"" //
                + "}", ContentType.APPLICATION_JSON));
        Response response = client.performRequest(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    // 创建文档
    // POST方法:在这个类型下存储文档
    private static void createDocumentPost(RestClient client) throws Exception {
        Request request = new Request("POST", INDEX + "/" + TYPE);
        request.setEntity(new NStringEntity("{" //
                + "\"name\":\"welcome\"," //
                + "\"value\":\"universe\"" //
                + "}", ContentType.APPLICATION_JSON));
        Response response = client.performRequest(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    // 获取文档
    private static void getDocument(RestClient client) throws Exception {
        Request request = new Request("GET", INDEX + "/" + TYPE + "/1");
        Response response = client.performRequest(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    // 获取文档
    private static void queryAll(RestClient client) throws Exception {
        Request request = new Request("GET", INDEX + "/" + TYPE + "/_search");
        request.setEntity(new NStringEntity("{" //
                + " \"query\": {" //
                + " \"match_all\": {}" //
                + " }\n" //
                + "}", ContentType.APPLICATION_JSON));
        Response response = client.performRequest(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    // 获取文档
    private static void queryByField(RestClient client) throws Exception {
        Request request = new Request("GET", INDEX + "/" + TYPE + "/_search");
        request.setEntity(new NStringEntity("{" //
                + " \"query\": {" //
                + " \"match\": {" //
                + " \"name\": \"welcome\"" //
                + " }" //
                + " }" //
                + "}", ContentType.APPLICATION_JSON));

        Response response = client.performRequest(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    // 删除文档
    private static void deleteDocument(RestClient client) throws Exception {
        Request request = new Request("DELETE", INDEX + "/" + TYPE + "/1");
        Response response = client.performRequest(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    // 暂停
    private static void pause() throws InterruptedException {
        Thread.sleep(500);
    }
}
