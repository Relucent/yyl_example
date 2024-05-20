package yyl.example.basic.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NIO（New I/O，新输入输出）是Java编程语言中的一个核心技术，用于处理非阻塞I/O操作<br>
 * <br>
 * NIO主要包括以下几个核心组件：<br>
 * Channels（通道）：通道是一个抽象的I/O操作对象，可以表示文件、套接字等。NIO中的通道与传统的I/O流不同，它们支持非阻塞I/O操作。通道的主要类型有FileChannel（文件通道）、SocketChannel（套接字通道）、ServerSocketChannel（服务器套接字通道）等。<br>
 * Buffers（缓冲区）：缓冲区是用于存储数据的容器。NIO中的缓冲区与传统的I/O流中的缓冲区不同，它们支持直接内存访问，提高了I/O性能。缓冲区的主要类型有ByteBuffer、CharBuffer、IntBuffer等。<br>
 * Selectors（选择器）：选择器是用于处理多个通道的事件（如读、写、连接等）的组件。通过选择器，你可以实现单线程处理多个通道的I/O操作，从而提高系统的并发性能。<br>
 * <br>
 * NIO的主要优点包括：<br>
 * 非阻塞I/O：NIO支持非阻塞I/O操作，可以在等待I/O操作完成时执行其他任务，提高了系统的响应速度。<br>
 * 高性能：NIO通过使用缓冲区和直接内存访问，减少了数据拷贝次数，提高了I/O性能。<br>
 * 可扩展性：NIO支持处理大量并发连接，适用于构建高性能的网络应用。<br>
 */
public class NIOHttpServer {

	public static void main(String[] args) throws IOException {
		// 创建一个非阻塞的ServerSocketChannel
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);

		// 绑定服务器套接字到指定的地址和端口
		serverChannel.bind(new InetSocketAddress(8080));

		// 创建一个Selector用于处理多个通道
		Selector selector = Selector.open();

		// 将ServerSocketChannel注册到Selector，监听OP_ACCEPT事件
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);

		while (true) {
			// 等待事件发生
			selector.select();

			// 处理发生的事件
			Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
			while (keys.hasNext()) {
				SelectionKey key = keys.next();
				keys.remove();

				if (key.isAcceptable()) {
					// 处理新的连接
					SocketChannel clientChannel = serverChannel.accept();
					clientChannel.configureBlocking(false);
					clientChannel.register(selector, SelectionKey.OP_READ);
				} else if (key.isReadable()) {
					// 处理读取数据
					SocketChannel clientChannel = (SocketChannel) key.channel();
					ByteBuffer buffer = ByteBuffer.allocate(1024);
					int bytesRead = clientChannel.read(buffer);

					if (bytesRead == -1) {
						// 客户端已关闭连接
						clientChannel.close();
					} else {
						// 切换缓冲区为读模式
						buffer.flip();

						// 将接收到的数据打印到控制台
						System.out.println("[Request]\n" + new String(buffer.array()));

						// HTTP响应的报文内容
						String response = "HTTP/1.1 200 OK\r\n" //
								+ "Content-Type: text/html; charset=UTF-8\r\n" //
								+ "Content-Length: 12\r\n" //
								+ "Connection: close\r\n" //
								+ "\r\n" //
								+ "Hello, World!";
						// 向客户端发送响应消息
						clientChannel.write(ByteBuffer.wrap(response.getBytes()));

						// 关闭客户端套接字
						clientChannel.close();
					}
				}
			}
		}
	}
}
