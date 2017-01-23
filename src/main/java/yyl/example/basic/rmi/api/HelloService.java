package yyl.example.basic.rmi.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 定义一个远程接口(RMI是支持方法重载的)
 */
public interface HelloService extends Remote {

	public String hello() throws RemoteException;

	public String hello(String name) throws RemoteException;
}
