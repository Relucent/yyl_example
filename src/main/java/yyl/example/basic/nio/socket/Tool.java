package yyl.example.basic.nio.socket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Tool {

	private final String CHARSET = "UTF-8";
	private final char END_CHAR = '\n';
	private final ConcurrentMap<SocketChannel, ByteArrayOutputStream> bufferMap = new ConcurrentHashMap<>();

	public String read(SocketChannel channel, ByteBuffer buffer) {
		try {
			String result = null;
			channel.read(buffer);
			buffer.flip();

			byte[] array = buffer.array();
			int limit = buffer.remaining();

			ByteArrayOutputStream stream = bufferMap.get(channel);

			for (int i = 0; i < limit; i++) {
				byte b = array[i];
				if (b == END_CHAR) {
					result = new String(stream.toByteArray(), CHARSET);
					stream = new ByteArrayOutputStream();
				} else {
					if (stream == null) {
						stream = new ByteArrayOutputStream();
					}
					stream.write(b);
				}
			}
			bufferMap.put(channel, stream);
			return result;
		} catch (IOException e) {
			closeQuietly(channel);
			bufferMap.remove(channel);
			System.err.println(e);
			return null;
		}
	}

	public void write(SocketChannel channel, String value) {
		try {
			channel.write(ByteBuffer.wrap((value + END_CHAR).getBytes(CHARSET)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeQuietly(SocketChannel channel) {
		try {
			channel.close();
		} catch (IOException ioe) {
		}
	}

}
