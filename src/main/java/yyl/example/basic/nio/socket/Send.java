package yyl.example.basic.nio.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class Send {

	public static void main(String[] args) throws Exception {
		SocketChannel socketChannel = SocketChannel.open();

		socketChannel.configureBlocking(false);

		Selector selector = Selector.open();
		socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 9998));

		while (!socketChannel.finishConnect()) {
		}

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		socketChannel.write(ByteBuffer.wrap("-".getBytes()));
		int index = 0;
		while (true) {
			if (selector.select() == 0) {
				Thread.sleep(1000);
				continue;
			}
			Set<SelectionKey> set = selector.selectedKeys();
			for (SelectionKey key : set) {
				int ops = key.readyOps();
				if ((ops & SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT) {
					socketChannel.write(buffer);
					System.out.println("OP_CONNECT:");
				}
				if ((ops & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
					socketChannel.read(buffer);
					buffer.flip();
					System.out.println(new String(buffer.array(), 0, buffer.remaining()));
					buffer.clear();
					socketChannel.write(ByteBuffer.wrap(("No-" + index + " | ").getBytes()));
					index++;
				}
			}
			set.clear();
			Thread.sleep(1000);
		}
	}
}
