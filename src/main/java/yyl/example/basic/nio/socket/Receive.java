package yyl.example.basic.nio.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class Receive {
	public static void main(String[] args) throws Exception {

		ByteBuffer buffer = ByteBuffer.allocate(1024);

		ServerSocketChannel ss = ServerSocketChannel.open();

		ss.socket().bind(new InetSocketAddress(9998));
		ss.configureBlocking(false);
		Selector se = Selector.open();
		ss.register(se, SelectionKey.OP_ACCEPT);
		while (se.select() > 0) {
			Set<SelectionKey> set = se.selectedKeys();
			for (SelectionKey key : set) {
				System.out.println("-------------");
				SocketChannel sc = null;
				try {
					if (key.isAcceptable()) {
						sc = ss.accept();
						System.err.println("ACCEPTABLE:" + sc.socket());
						sc.configureBlocking(false);
						sc.register(se, SelectionKey.OP_READ);
					} else if (key.isReadable()) {
						sc = (SocketChannel) key.channel();
						System.out.println(sc.isConnected());
						sc.read(buffer);
						buffer.flip();

						System.out.println(new String(buffer.array(), 0, buffer.remaining()));
						sc.write(ByteBuffer.wrap("+".getBytes()));
					}
				} catch (Exception e) {
					key.cancel();
					if (sc != null) {
						sc.close();
					}
					e.printStackTrace();
				}
			}
			set.clear();
		}

	}
}
