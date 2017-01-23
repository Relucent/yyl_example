package yyl.example.demo.okhttp3;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class TraceInterceptor implements Interceptor {

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();

		System.out.println("/==========TraceInterceptor==========");
		System.out.println("\nRequest:");
		System.out.println(request.method());
		System.out.println(request.url());
		System.out.println(request.headers());

		RequestBody requestBody = request.body();

		if (requestBody != null) {
			try (Buffer buffer = new Buffer()) {
				requestBody.writeTo(buffer);
				String content = buffer.readUtf8();
				System.out.println(content);
				requestBody = RequestBody.create(requestBody.contentType(), content);
			}
		}

		request = request.newBuilder().method(request.method(), requestBody).build();

		Response response = chain.proceed(request);
		MediaType mediaType = response.body().contentType();
		String content = response.body().string();

		System.out.println("\nResponse:");
		System.out.println(response.headers());
		System.out.println(content);
		System.out.println("==========TraceInterceptor==========/\n\n\n\n\n");

		return response.newBuilder().body(ResponseBody.create(mediaType, content)).build();
	}

}
