package yyl.example.demo.vertx;

import java.io.IOException;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Vert.x是一个异步、可伸缩、并发应用的框架<br>
 * Vert.x提供一个事件驱动编程模型，使用Vert.x作为服务器时，程序员只要编写事件处理器event handler即可. <br>
 * 当TCP socket有数据时，event handler理解被创建调用，另外它还可以在以下几种情况激活： <br>
 * '当事件总线Event Bus接受到消息时,' '当接收到HTTP消息时,' 当一个连接断开时',' '当计时器超时时.'<br>
 * 运行这个例子，然后访问 http://localhost:8080/hello 查看效果
 */
public class HelloVertxVerticle extends AbstractVerticle {

	/** 运行 */
	@Override
	public void start() throws Exception {

		Router router = Router.router(vertx);

		router.route().handler(BodyHandler.create());

		router.get("/hello").handler(new Handler<RoutingContext>() {
			public void handle(RoutingContext event) {
				event.response().putHeader("content-type", "text/html").end("Hello Vert.x");
			}
		});

		vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
			public void handle(HttpServerRequest event) {
				router.accept(event);
			}
		}).listen(8080);//监听端口号
	}

	public static void main(String[] args) throws IOException {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(HelloVertxVerticle.class.getName());
	}
}
