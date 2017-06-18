package yyl.example.basic.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Set;

public class Client {

	public static void main(String[] args) throws Exception {

		Scanner scanner = new Scanner(System.in);
		Selector selector = Selector.open();

		ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
		SocketChannel channel = SocketChannel.open();
		channel.configureBlocking(false);
		channel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		channel.connect(new InetSocketAddress("127.0.0.1", 9998));

		while (!Thread.currentThread().isInterrupted()) {

			if (selector.select() == 0) {
				Thread.sleep(1000);
				continue;
			}

			Set<SelectionKey> keySet = selector.selectedKeys();
			for (SelectionKey key : keySet) {
				int ops = key.readyOps();
				if ((ops & SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT) {
					if (channel.finishConnect()) {
						System.out.println("[Connect successfully]");
						input(channel, buffer, scanner);
					} else {
						System.out.println("[Connect failed]");
						Tool.closeQuietly(key, channel);
						return;
					}
				} else if ((ops & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
					String value = Tool.read(channel, buffer);
					System.out.println(value);
					input(channel, buffer, scanner);
				}
			}
			keySet.clear();
		}

	}

	private static void input(SocketChannel channel, ByteBuffer buffer, Scanner scanner) throws IOException {
		buffer.clear();
		System.out.print("Please enter:");
		String line = scanner.nextLine();
		Tool.write(channel, line);
	}

}