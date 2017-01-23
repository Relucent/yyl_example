package yyl.example.demo.okhttp3;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//异步模式
public class OkhttpTest2 {
	public static void main(String[] args) throws IOException {
		OkHttpClient client;
		(client = new OkHttpClient.Builder()//
				.build()//
		).newCall(//
				new Request.Builder()//
						.url("https://www.baidu.com/")//
						.header("Connection", "close")//close | keep-alive
						.get()//
						.build()//
		).enqueue(new Callback() {
			@Override
			public void onResponse(Call call, Response response) throws IOException {
				System.out.println(response.body().string());
			}

			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}
		});

		//应用关闭时候需要关闭线程池
		client.dispatcher().executorService().shutdown();
	}
}
