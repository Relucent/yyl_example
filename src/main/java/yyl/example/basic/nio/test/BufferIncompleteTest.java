package yyl.example.basic.nio.test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * 期望交替输出 ABCDEFGHIGKLMNOPQRSTUVWXYZ 和 1234567890<br>
 * 但是实际上输出内容可能是不连续的<br>
 * 无论 ByteBuffer设置多大，也不能保证一次读取内容是完整的<br>
 */
public class BufferIncompleteTest {

	public static void main(String[] args) {
		new Receiver().start();
		new Sender().start();
	}

	static class Receiver extends Thread {

		@Override
		public void run() {
			try {
				Selector selector = Selector.open();
				ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);

				ServerSocketChannel socketChannel = ServerSocketChannel.open();
				socketChannel.socket().bind(new InetSocketAddress(9998));
				socketChannel.configureBlocking(false);
				socketChannel.register(selector, SelectionKey.OP_ACCEPT);

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
							try {
								channel.read(buffer);
								buffer.flip();
								System.out.println(new String(buffer.array(), 0, buffer.remaining()));
							} catch (Exception e) {
								System.err.println(e);
								channel.close();
								key.cancel();
							}
						}
					}
					keySet.clear();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	static class Sender extends Thread {
		@Override
		public void run() {
			try {
				Selector selector = Selector.open();

				SocketChannel channel = SocketChannel.open();
				channel.configureBlocking(false);
				channel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE);
				channel.connect(new InetSocketAddress("127.0.0.1", 9998));

				int mod = 0;

				byte[][] content = { "ABCDEFGHIGKLMNOPQRSTUVWXYZ".getBytes(), "1234567890".getBytes() };

				while (!Thread.currentThread().isInterrupted()) {
					selector.select();
					Set<SelectionKey> keySet = selector.selectedKeys();
					for (SelectionKey key : keySet) {
						if (key.isConnectable()) {
							if (!channel.finishConnect()) {
								return;
							}
						} else if (key.isWritable()) {
							ByteBuffer buffer = ByteBuffer.wrap(content[mod = (mod + 1) % 2]);
							channel.write(buffer);
						}
					}
					keySet.clear();
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
