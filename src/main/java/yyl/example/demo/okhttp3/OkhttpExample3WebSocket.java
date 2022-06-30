package yyl.example.demo.okhttp3;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * OkHttp 示例： WebSocket
 */
public class OkhttpExample3WebSocket {

	public static void main(String[] args) throws IOException {
		Request request = new Request.Builder()//
				.get()//
				.url("ws://echo.websocket.org/")//
				.build();
		OkHttpClient client = new OkHttpClient.Builder()//
				.readTimeout(3, TimeUnit.SECONDS)// 设置读取超时时间
				.writeTimeout(3, TimeUnit.SECONDS)// 设置写的超时时间
				.connectTimeout(3, TimeUnit.SECONDS)// 设置连接超时时间
				.build();
		client.newWebSocket(request, new WebSocketListener() {
			public void onOpen(WebSocket webSocket, Response response) {
				System.out.println("onOpen:");
				webSocket.send("hello world");
				webSocket.send(ByteString.decodeHex("ABCDEF"));
				webSocket.close(1000, "byte");
			}

			public void onMessage(WebSocket webSocket, String text) {
				System.out.println("onMessage:" + text);
			}

			public void onMessage(WebSocket webSocket, ByteString bytes) {
				System.out.println("onMessage:" + bytes);
			}

			public void onClosing(WebSocket webSocket, int code, String reason) {
				System.out.println("onClosing:" + code + "/" + reason);
			}

			public void onClosed(WebSocket webSocket, int code, String reason) {
				System.out.println("onClosed:" + code + "/" + reason);
			}

			public void onFailure(WebSocket webSocket, Throwable error, Response response) {
				System.out.println("onFailure:" + error.getMessage());
			}
		});
		client.dispatcher().executorService().shutdown();
	}
}
