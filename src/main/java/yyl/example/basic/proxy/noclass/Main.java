package yyl.example.basic.proxy.noclass;

public class Main {

	public static void main(String[] args) {
		Bean bean = (Bean) InterfaceProxyHandler.getBeanProxy(Bean.class);
		bean.setValue("Hello World");
		System.out.println("class=" + bean.getClass());
		System.out.println("value=" + bean.getValue());
		System.out.println("bean=" + bean);
		System.out.println("instanceof=" + (bean instanceof Bean));
	}
}
