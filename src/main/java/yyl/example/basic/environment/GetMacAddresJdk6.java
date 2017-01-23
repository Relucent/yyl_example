package yyl.example.basic.environment;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 获得电脑网卡的MAC地址
 * @author YYL
 */
public class GetMacAddresJdk6 {

	public static final Collection<String> getMacAddress() throws SocketException {
		Set<String> mSet = new LinkedHashSet<String>();
		Enumeration<NetworkInterface> el = NetworkInterface.getNetworkInterfaces();
		while (el.hasMoreElements()) {
			byte[] mac = el.nextElement().getHardwareAddress();
			if (mac == null || mac.length == 0) {
				continue;
			}
			StringBuilder builder = new StringBuilder();
			boolean first = true;
			for (byte b : mac) {
				if (first) {
					first = false;
				} else {
					builder.append("-");
				}
				builder.append(hexByte(b));
			}
			mSet.add(builder.toString().toUpperCase());
		}
		return mSet;
	}
	private static String hexByte(byte b) {
		String hex = Integer.toHexString(b);
		int length = hex.length();
		if (length == 0) {
			return "00";
		} else if (length == 1) {
			return "0" + hex;
		} else if (length == 2) {
			return hex;
		} else {
			return hex.substring(length - 2);
		}
	}

	public static void main(String[] args) throws SocketException {
		System.out.println(getMacAddress().toString());
	}

}
