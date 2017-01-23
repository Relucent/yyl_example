package yyl.example.basic.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import yyl.example.basic.rmi.api.HelloService;

@SuppressWarnings("serial")
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {

	public HelloServiceImpl() throws RemoteException {
	}

	@Override
	public String hello() throws RemoteException {
		return "hello, world";
	}

	@Override
	public String hello(String value) throws RemoteException {
		return value;
	}
}
