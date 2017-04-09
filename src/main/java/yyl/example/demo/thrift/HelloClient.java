package yyl.example.demo.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class HelloClient {
	public static void main(String[] args) {

		TTransport transport = null;

		try {

			transport = new TSocket("localhost", 8090);
			transport.open();

			TProtocol protocol = new TBinaryProtocol(transport);
			HelloService.Client client = new HelloService.Client(protocol);

			System.out.println(client.hello("thrift world"));

		} catch (TException e) {
			e.printStackTrace();
		} finally {
			transport.close();
		}
	}
}
