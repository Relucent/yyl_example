package yyl.example.basic.nio.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

class Tools {

	public static void printHEX(byte[] data) {
		for (int i = 0; i < data.length; i++) {
			int u = (data[i] & 0xFF);
			String s = Integer.toHexString(u);
			System.out.print(u < 0x10 ? ("0" + s) : (s));
			System.out.print(" ");
		}
		System.out.println();
	}

	public static byte[] parseBytes(String values) {
		String[] ss = values.split("[\\s]+");
		byte[] data = new byte[ss.length];
		for (int i = 0; i < ss.length; i++) {
			data[i] = (byte) Integer.parseInt(ss[i], 16);
		}
		return data;
	}

	public static ByteBuffer getInByteBuffer() throws IOException {
		return ByteBuffer.wrap(Tools.parseBytes(new BufferedReader(new InputStreamReader(System.in)).readLine()));
	}

}
