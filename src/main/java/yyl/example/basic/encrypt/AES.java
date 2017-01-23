package yyl.example.basic.encrypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密解密
 */
public class AES {

	//=================================Constants==============================================
	/** 16进制字符数组 */
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
	/** 默认AES密匙(16字节)*/
	private final static byte[] DEFAULT_KEY_BYTE = { 0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF };
	/** 密匙字节长度(16)*/
	private final static int KEY_BYTE_LENGTH = 16;
	/** 字符集编码(UTF-8) */
	private final static String encoding = "UTF-8";
	//=================================Fields=================================================
	/** AES密匙(16字节)*/
	private final byte[] keyByte;
	//=================================Constructors===========================================

	/**
	 * 构造函数
	 */
	public AES() {
		this(DEFAULT_KEY_BYTE);
	}
	/**
	 * 构造函数
	 * @param key
	 */
	public AES(String key) {
		this(key.getBytes());
	}
	/**
	 * 构造函数
	 * @param keyByte 16字节的密钥
	 */
	public AES(byte[] keyByte) {
		this.keyByte = DEFAULT_KEY_BYTE.clone();
		for (int i = 0, len = Math.min(keyByte.length, KEY_BYTE_LENGTH); i < len; i++) {
			this.keyByte[i] = keyByte[i];
		}
	}
	//=================================Methods================================================

	/**
	 * 将密文使用AES进行解密
	 * @param encryptText 密文字符串
	 * @return 解密后的字符串
	 */
	public String decrypt(String encryptText) throws Exception {
		//通过SecretKeySpec形成一个key
		SecretKey key = new SecretKeySpec(keyByte, "AES");
		//获得一个私鈅加密类Cipher，ECB是加密方式，PKCS5Padding是填充方法
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		//使用私鈅解密
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] NewCipherText = hexStringTobyteArray(encryptText);
		byte[] newString = cipher.doFinal(NewCipherText);
		return new String(newString, encoding);

	}

	/**
	 * 将传进来的明文以AES算法进行加密
	 * @param text 明文字符串
	 * @return 加密后的字符串
	 */
	public String encrypt(String text) throws Exception {
		byte[] OriByte = text.getBytes(encoding);
		//通过SecretKeySpec形成一个key
		SecretKey key = new SecretKeySpec(keyByte, "AES");
		//获得一个私鈅加密类Cipher，ECB是加密方式，PKCS5Padding是填充方法
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		//使用私鈅加密
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] OriCipherText = cipher.doFinal(OriByte);
		String b = byteArrayToHexString(OriCipherText);
		return b; //密码，转换成16进制
	}

	/**
	 * 一位Byte到16进制字符串的转换
	 * @param b byte
	 * @return String
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * Byte数组到16进制字符串的转换
	 * @param b byte[]
	 * @return String
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 16进制字符串到Byte转换
	 * @param b String
	 * @return byte
	 */
	private static byte HexStringTobyte(String b) {
		int By = 0;
		String b1 = b.substring(0, 1);
		int b11 = -1;
		String b2 = b.substring(1);
		int b12 = -1;
		for (int i = 0; i < 16; i++) {
			if (b1.equals(hexDigits[i])) {
				b11 = i;
			}
		}
		for (int i = 0; i < 16; i++) {
			if (b2.equals(hexDigits[i])) {
				b12 = i;
			}
		}
		By = b11 * 16 + b12;
		if (By > 256) {
			By = By - 256;
		}
		return (byte) By;
	}

	/**
	 * 16进制字符串到Byte数组的转换
	 * @param b String
	 * @return byte[]
	 */
	private static byte[] hexStringTobyteArray(String b) {
		byte[] r = new byte[b.length() / 2];
		for (int i = 0; i < b.length() / 2; i++) {
			r[i] = HexStringTobyte(b.substring(i * 2, i * 2 + 2));
		}
		return r;
	}

	public static void main(String[] args) {
		try {
			String key = "key";
			String str = "password";
			AES en = new AES(key);
			String encryptString = en.encrypt(str);
			System.out.println("AES加密后为：" + encryptString);
			System.out.println("AES解密后为：" + en.decrypt(encryptString));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}