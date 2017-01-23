package yyl.example.basic.identifier;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class GuidGenerator {
	private static SecureRandom mySecureRand = new SecureRandom();;
	private static String s_id;
	private String valueAfterMD5 = "";

	static {
		try {
			s_id = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	private GuidGenerator() {
		try {
			StringBuffer sbValueBeforeMD5 = new StringBuffer(128);
			MessageDigest md5 = MessageDigest.getInstance("MD5");

			long time = System.currentTimeMillis();
			long rand = mySecureRand.nextLong();
			sbValueBeforeMD5.append(s_id);
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(time));
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(rand));

			String valueBeforeMD5 = sbValueBeforeMD5.toString();
			md5.update(valueBeforeMD5.getBytes());
			byte[] array = md5.digest();
			StringBuffer sb = new StringBuffer(32);
			for (int j = 0; j < array.length; ++j) {
				int b = array[j] & 0xFF;
				if (b < 0x10)
					sb.append('0');
				sb.append(Integer.toHexString(b));
			}
			valueAfterMD5 = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		String raw = valueAfterMD5.toUpperCase().toString();
		StringBuffer sb = new StringBuffer(64);
		sb.append("{");
		sb.append(raw.substring(0, 8));
		sb.append("-");
		sb.append(raw.substring(8, 12));
		sb.append("-");
		sb.append(raw.substring(12, 16));
		sb.append("-");
		sb.append(raw.substring(16, 20));
		sb.append("-");
		sb.append(raw.substring(20));
		sb.append("}");
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(new GuidGenerator().toString());
	}
}
