package yyl.example.basic.http;

/**
 * 测试
 */
public abstract class Test {

	public static void main(String[] args) {
		HttpResponse response = HttpUtil.get("http://www.360.com");
		System.out.println(response.getContentAsString());
	}

}
