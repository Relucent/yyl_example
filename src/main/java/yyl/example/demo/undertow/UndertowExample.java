package yyl.example.demo.undertow;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

/**
 * Undertow 是红帽公司开发的一款基于 NIO 的高性能 Web 嵌入式服务器<br>
 * 特点 :<br>
 * 轻量级：它是一个 Web 服务器，但不像传统的 Web 服务器有容器概念，它由两个核心 Jar 包组成，加载一个 Web 应用可以小于 10MB 内存<br>
 * Servlet3.1 支持：它提供了对 Servlet3.1 的支持<br>
 * WebSocket 支持：对 Web Socket 完全支持，用以满足 Web 应用巨大数量的客户端<br>
 * 嵌套性：它不需要容器，只需通过 API 即可快速搭建 Web 服务器<br>
 */
public class UndertowExample {

	public static void main(final String[] args) {
		Undertow server = Undertow.builder().addHttpListener(8080, "0.0.0.0").setHandler(new HttpHandler() {
			@Override
			public void handleRequest(final HttpServerExchange exchange) throws Exception {
				exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
				exchange.getResponseSender().send("Hello World");
			}
		}).build();
		server.start();
	}
}
