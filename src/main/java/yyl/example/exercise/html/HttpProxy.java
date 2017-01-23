package yyl.example.exercise.html;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class HttpProxy {
	public static void main(String[] args) {

		try {
			URL url = new URL("http://www.baidu.com");

			// 创建代理服务器
			InetSocketAddress addr = new InetSocketAddress("10.14.38.235", 9090);

			// Proxy proxy = new Proxy(Proxy.Type.SOCKS, addr); // Socket 代理

			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理

			// 如果我们知道代理server的名字, 可以直接使用

			// 结束

			URLConnection conn = url.openConnection(proxy);

			InputStream in = conn.getInputStream();

			// InputStream in = url.openStream();

			String s = toString(in);

			System.out.println(s);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	private static String toString(InputStream input) throws IOException {
		if (input == null) {
			return null;
		} else {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int n = 0;
			while (-1 != (n = input.read(buffer))) {
				output.write(buffer, 0, n);
			}
			return new String(output.toByteArray());
		}
	}
}
