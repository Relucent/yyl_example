package yyl.example.basic.rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import yyl.example.basic.rmi.api.HelloService;

public class HelloClient {
	public static void main(String args[]) {
		try {
			// 在RMI服务注册表中查找名称为RHello的对象，并调用其上的方法
			HelloService service = (HelloService) Naming.lookup("rmi://localhost:8888/hello");
			System.out.println(service.hello());
			System.out.println(service.hello("I'm the king of the world!"));
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
