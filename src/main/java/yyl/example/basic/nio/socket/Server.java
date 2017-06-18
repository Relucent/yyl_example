package yyl.example.basic.nio.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class Server {

	public static void main(String[] args) throws Exception {

		final Selector selector = Selector.open();
		final ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
		final Tool tool = new Tool();

		ServerSocketChannel socketChannel = ServerSocketChannel.open();
		socketChannel.socket().bind(new InetSocketAddress(9998));
		socketChannel.configureBlocking(false);
		socketChannel.register(selector, SelectionKey.OP_ACCEPT);

		System.out.println("[Start Server]");

		while (selector.select() > 0) {
			Set<SelectionKey> keySet = selector.selectedKeys();
			for (SelectionKey key : keySet) {
				if (key.isAcceptable()) {
					SocketChannel channel = socketChannel.accept();
					System.out.println("[Accep Client]:" + channel.socket());
					channel.configureBlocking(false);
					channel.register(selector, SelectionKey.OP_READ);
				} else if (key.isReadable()) {

					SocketChannel channel = (SocketChannel) key.channel();

					String value = tool.read(channel, buffer);

					if (value == null) {
						if (!channel.isOpen()) {
							key.cancel();
						}
						continue;
					}

					System.out.println(value);
					tool.write(channel, "hello " + value);
				}
			}
			keySet.clear();
		}
	}

}
