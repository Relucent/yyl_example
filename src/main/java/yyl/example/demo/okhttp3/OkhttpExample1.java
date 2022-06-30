package yyl.example.demo.okhttp3;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * OkHttp 示例
 */
public class OkhttpExample1 {
	public static void main(String[] args) throws IOException {
		System.out.println(//
				new OkHttpClient.Builder()//
						.addInterceptor(new TraceInterceptor())//
						.build()//
						.newCall(//
								new Request.Builder()//
										.url("https://www.baidu.com/")//
										.get()//
										.build()//
						)//
						.execute()//
						.body()//
						.string()//
		);
	}
}
