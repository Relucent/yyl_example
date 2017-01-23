package yyl.example.basic.ping;

import java.net.InetAddress;
import java.net.Socket;

/**
 * 扫描开放端口
 */
public class PingProt {
	public static void main(String[] args) throws Exception {
		String ip = "127.0.0.1";
		try {
			boolean status = InetAddress.getByName(ip).isReachable(3000);
			System.out.println(status);
			if (status) {
				for (int port = 0; port < 65535; port++) {
					try {
						Socket s = new Socket(ip, port);
						System.out.println(ip + ":" + port + " is open");
						s.close();
					} catch (Exception e) {
						System.out.println(ip + ":" + port + " is not open");
					}
				}
			} else {
				System.out.println(ip + " connect failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
