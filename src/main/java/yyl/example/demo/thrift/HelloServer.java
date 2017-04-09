package yyl.example.demo.thrift;

import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * 简单的Thrift服务器<br>
 */
public class HelloServer {

	private static final int SERVER_PORT = 8090;

	public static void main(String[] args) {

		try {

			HelloService.Processor<HelloServiceHandler> processor = new HelloService.Processor<>(new HelloServiceHandler());

			TServer server = getSimpleServer(SERVER_PORT, processor);

			System.out.println("Starting the simple server...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 简单的单线程服务模型，一般用于测试 */
	public static TServer getSimpleServer(int port, HelloService.Processor<HelloServiceHandler> processor) throws TTransportException {
		TServerTransport transport = new TServerSocket(port);
		TServer server = new TSimpleServer(new TServer.Args(transport).processor(processor));
		return server;
	}

	/** 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求 */
	public static TServer getPoolServer(int port, HelloService.Processor<HelloServiceHandler> processor) throws TTransportException {
		TServerTransport transport = new TServerSocket(port);
		TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(transport).processor(processor));
		return server;
	}

	/** 使用非阻塞式IO，服务端和客户端需要指定 TFramedTransport 数据传输的方式 。 */
	public static TServer getNonblockingServer(int port, HelloService.Processor<HelloServiceHandler> processor) throws TTransportException {
		TNonblockingServerSocket transport = new TNonblockingServerSocket(port);
		TNonblockingServer.Args args = new TNonblockingServer.Args(transport).processor(processor);
		//服务端和客户端需要指定TFramedTransport数据传输的方式
		TServer server = new TNonblockingServer(args);
		return server;
	}

	/** 半同步半异步的服务端模型，需要指定为： TFramedTransport 数据传输的方式 */
	public static TServer getHsHaServer(int port, HelloService.Processor<HelloServiceHandler> processor) throws TTransportException {
		TNonblockingServerSocket transport = new TNonblockingServerSocket(port);
		THsHaServer.Args args = new THsHaServer.Args(transport).processor(processor);
		TServer server = new THsHaServer(args);
		return server;
	}
}
