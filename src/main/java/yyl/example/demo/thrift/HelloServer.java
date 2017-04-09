package yyl.example.demo.thrift;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

/**
 * 简单的Thrift服务器<br>
 */
public class HelloServer {

	public static void main(String[] args) {

		try {

			HelloService.Processor<HelloServiceHandler> processor = new HelloService.Processor<>(new HelloServiceHandler());

			TServerTransport transport = new TServerSocket(8090);

			//#简单的单线程服务模型，一般用于测试
			TServer server = new TSimpleServer(new TServer.Args(transport).processor(processor));

			//#多线线程服务模型（正式环境使用）
			// TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));  

			System.out.println("Starting the simple server...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
