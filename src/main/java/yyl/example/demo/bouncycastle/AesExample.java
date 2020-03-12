package yyl.example.demo.bouncycastle;

import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AesExample {

	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES/CBC/PKCS7Padding";
	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	public static void main(String[] args) {
		String plaintext = "Hello, World!";
		String key = "0123456789abcdef";
		String iv = "0123456789abcdef";
		System.out.println("plaintext	=	" + plaintext);
		String ciphertext = encrypt(plaintext, key, iv);
		System.out.println("ciphertext	=	" + ciphertext);
		String decrypttext = decrypt(ciphertext, key, iv);
		System.out.println("decrypttext	=	" + decrypttext);
	}

	/**
	 * 加密数据
	 * @param plaintext 明文
	 * @param key 密钥(16位)
	 * @param iv 偏移量(16位)
	 * @return 密文(BASE64格式)
	 */
	public static String encrypt(String plaintext, String key, String iv) {
		try {
			byte[] plaintextdata = plaintext.getBytes(StandardCharsets.UTF_8);
			byte[] keydata = key.getBytes(StandardCharsets.UTF_8);
			byte[] ivdata = iv.getBytes(StandardCharsets.UTF_8);
			SecretKey secretkey = new SecretKeySpec(keydata, ALGORITHM);
			IvParameterSpec ivParameter = new IvParameterSpec(ivdata);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION, BouncyCastleProvider.PROVIDER_NAME);
			cipher.init(Cipher.ENCRYPT_MODE, secretkey, ivParameter);
			byte[] encrypted = cipher.doFinal(plaintextdata);
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解密数据
	 * @param ciphertext 密文(BASE64格式)
	 * @param key 密钥(16位)
	 * @param iv 偏移量(16位)
	 * @return 明文
	 */
	public static String decrypt(String ciphertext, String key, String iv) {
		try {
			byte[] ciphertextdata = Base64.getDecoder().decode(ciphertext);
			byte[] keydata = key.getBytes(StandardCharsets.UTF_8);
			byte[] ivdata = iv.getBytes(StandardCharsets.UTF_8);
			SecretKey secretkey = new SecretKeySpec(keydata, ALGORITHM);
			IvParameterSpec ivParameter = new IvParameterSpec(ivdata);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION, BouncyCastleProvider.PROVIDER_NAME);
			cipher.init(Cipher.DECRYPT_MODE, secretkey, ivParameter);
			byte[] encrypted = cipher.doFinal(ciphertextdata);
			return new String(encrypted, StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}