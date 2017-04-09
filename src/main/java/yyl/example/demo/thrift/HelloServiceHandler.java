package yyl.example.demo.thrift;

import org.apache.thrift.TException;

public class HelloServiceHandler implements HelloService.Iface {
	@Override
	public String hello(String text) throws TException {
		return "hello " + text;
	}
}