package yyl.example.basic.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * AIO（Asynchronous I/O，异步I/O）与传统的同步I/O（阻塞I/O）不同，AIO允许应用程序在等待I/O操作完成的同时执行其他任务。<br>
 * 这种非阻塞性的I/O操作可以提高应用程序的性能和响应能力。 <br>
 * 在AIO中，I/O操作是由操作系统完成的，应用程序不需要等待I/O操作完成。<br>
 * 当I/O操作完成时，操作系统会通知应用程序，应用程序可以在回调函数中处理I/O操作的结果。
 */
public class AIOHttpExample {

	// 简单的AIO服务器示例
	public static void main(String[] args) throws IOException, InterruptedException {

		// 创建一个异步服务器套接字通道
		AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open();

		// 绑定服务器套接字到指定的地址和端口
		serverChannel.bind(new InetSocketAddress(8080));

		// 注册一个接受连接的回调处理器
		serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
			@Override
			public void completed(AsynchronousSocketChannel clientChannel, Object attachment) {

				// 继续监听新的连接
				serverChannel.accept(null, this);

				// 分配一个缓冲区用于读取客户端发送的数据
				ByteBuffer buffer = ByteBuffer.allocate(1024);

				clientChannel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
					@Override
					public void completed(Integer result, ByteBuffer attachment) {

						// 切换缓冲区为读模式
						attachment.flip();

						// 将接收到的数据打印到控制台
						System.out.println("[Request]\n" + new String(attachment.array()));

						// HTTP响应的报文内容
						String response = "HTTP/1.1 200 OK\r\n" //
								+ "Content-Type: text/html; charset=UTF-8\r\n" //
								+ "Content-Length: 12\r\n" //
								+ "Connection: close\r\n" //
								+ "\r\n" //
								+ "Hello, World!";

						// 向客户端发送响应消息
						clientChannel.write(ByteBuffer.wrap(response.getBytes()));
					}

					@Override
					public void failed(Throwable exc, ByteBuffer attachment) {
						// 处理读取数据失败的情况
						exc.printStackTrace();
					}
				});
			}

			@Override
			public void failed(Throwable exc, Object attachment) {
				// 处理接受连接失败的情况
				exc.printStackTrace();
			}
		});

		// 输出服务器启动信息
		System.out.println("AIO server started on port 8080");

		// 阻塞主线程，使服务器持续运行
		Thread.sleep(Long.MAX_VALUE);
	}
}
