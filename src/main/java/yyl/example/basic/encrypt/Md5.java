package yyl.example.basic.encrypt;

import java.security.MessageDigest;

public class Md5 {

	public static void main(String[] args) throws Throwable {

		MessageDigest md = MessageDigest.getInstance("MD5");
		System.out.println(encode(md.digest("1".getBytes())));
	}

	private static final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static char[] encode(byte[] bytes) {
		int nBytes = bytes.length;
		char[] result = new char[2 * nBytes];

		int j = 0;
		for (int i = 0; i < nBytes; i++) {
			result[(j++)] = HEX[((0xF0 & bytes[i]) >>> 4)];

			result[(j++)] = HEX[(0xF & bytes[i])];
		}

		return result;
	}
}
