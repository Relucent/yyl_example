package yyl.example.basic.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO（Blocking I/O，阻塞式I/O）是一种同步、阻塞的I/O模型，它是Java早期版本中的默认I/O模型。<br>
 * 在BIO模型中，服务器为每个客户端连接创建一个新的线程。这意味着每个线程都会阻塞地等待客户端的I/O操作完成。当服务器处理大量并发连接时，这种模型可能会导致线程数量过多，从而消耗大量系统资源。<br>
 * <br>
 * BIO模型的主要特点如下：<br>
 * 同步：服务器线程在处理客户端请求时，会阻塞地等待I/O操作完成。<br>
 * 阻塞：服务器线程在处理客户端请求时，如果I/O操作未完成，线程会被阻塞。<br>
 * 线程模型：BIO模型中，每个客户端连接都需要一个线程来处理。<br>
 */
public class BIOHttpExample {

	// 简单的BIO服务器示例
	public static void main(String[] args) throws IOException, InterruptedException {

		// 创建一个ServerSocket，监听8080端口
		try (ServerSocket serverSocket = new ServerSocket(8080)) {

			while (true) {
				// 等待客户端连接
				Socket clientSocket = serverSocket.accept();

				// 客户端输入流
				BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				// 客户端输出流
				PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

				// 读取客户端发送信息
				System.out.println("[Request]");
				for (String line; (line = input.readLine()) != null;) {
					System.out.println(line);
				}

				// HTTP响应的报文内容
				String response = "HTTP/1.1 200 OK\r\n" //
						+ "Content-Type: text/html; charset=UTF-8\r\n" //
						+ "Content-Length: 12\r\n" //
						+ "Connection: close\r\n" //
						+ "\r\n" //
						+ "Hello, World!";

				// 向客户端发送响应
				output.print(response);

				// 关闭客户端套接字
				clientSocket.close();
			}
		}
	}
}
