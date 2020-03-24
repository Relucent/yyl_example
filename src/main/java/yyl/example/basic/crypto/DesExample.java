package yyl.example.basic.crypto;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES加密
 */
public class DesExample {

	/** 获得一个密匙数据* */
	public static byte[] getKeyDate() throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		kg.init(new SecureRandom());
		SecretKey key = kg.generateKey();
		return key.getEncoded();
	}

	/** 根据密匙加密数据* */
	public static byte[] encrypt(byte[] data, byte[] keydate) throws Exception {
		DESKeySpec dks = new DESKeySpec(keydate);
		// 创建一个密匙工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, key, new SecureRandom());
		// 执行加密操作
		return cipher.doFinal(data);
	}

	public static byte[] decode(byte[] bytes, byte[] keydate) throws Exception {
		// 从原始密匙数据创建一个DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(keydate);
		// 创建一个密匙工厂获得SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, key, new SecureRandom());
		// 执行解密操作
		return cipher.doFinal(bytes);
	}

	public static void main(String[] args) {
		try {
			byte[] keydate = getKeyDate();
			System.out.println(new String(decode(encrypt("你好世界！".getBytes(), keydate), keydate)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
