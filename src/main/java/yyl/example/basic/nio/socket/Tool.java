package yyl.example.basic.nio.socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class Tool {

	public static final String CHARSET = "UTF-8";

	public static void closeQuietly(SelectionKey key, SocketChannel channel) {
		try {
			key.channel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			channel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String read(SocketChannel channel, ByteBuffer buffer) {
		try {
			channel.read(buffer);
			buffer.flip();
			return new String(buffer.array(), 0, buffer.remaining(), CHARSET);
		} catch (IOException e) {
			System.err.println(e);
		}
		return null;
	}

	public static void write(SocketChannel channel, String value) {
		try {
			channel.write(ByteBuffer.wrap(value.getBytes(CHARSET)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
