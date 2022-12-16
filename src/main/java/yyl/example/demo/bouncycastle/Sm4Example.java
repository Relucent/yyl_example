package yyl.example.demo.bouncycastle;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * 国密SM4（对称加密算法）实现，该实现基于BC库<br>
 * SM4加密算法是中华人民共和国政府采用的一种分组密码标准，由国家密码管理局于2012年发布。<br>
 * 与DES和AES算法相似，国密SM4算法是一种分组加密算法。SM4分组密码算法是一种迭代分组密码算法，由加解密算法和密钥扩展算法组成。<br>
 * SM4是一种Feistel结构的分组密码算法，其分组长度和密钥长度均为128位（16字节）。加密算法和密钥扩展算法迭代轮数均为32轮。SM4加解密过程的算法相同但是轮密钥的使用顺序相反。<br>
 * 标准号：GB/T 32907-2016 <br>
 * 中文标准名称：信息安全技术 SM4分组密码算法 <br>
 * 英文标准名称：Information security technology—SM4 block cipher algorthm <br>
 * @see <a href="https://openstd.samr.gov.cn/bzgk/gb/newGbInfo?hcno=7803DE42D3BC5E80B0C3E5D8E873D56A">GB/T 32907-2016</a>
 */
public class Sm4Example {

	static {
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
			Security.addProvider(new BouncyCastleProvider());
		}
	}

	/** SM4算法名称 */
	private static final String ALGORITHM = "SM4";
	/** SM4/ECB/PKCS5Padding 算法完整字符串 */
	private static final String TRANSFORMATION = "SM4/ECB/PKCS5Padding";
	/** SM4算法目前只支持128位（即密钥16字节） */
	private static final int DEFAULT_KEY_SIZE = 128;
	/** SM4算法目前密钥只支持16字节（128÷8=16） */
	private static final int DEFAULT_KEY_BYTE_LENGTH = 16;

	public static void main(String[] args) throws Exception {
		byte[] key = generateKey();
		String original = "你好世界！";
		byte[] ciphertext = encrypt(original.getBytes(StandardCharsets.UTF_8), key);
		byte[] plaintext = decrypt(ciphertext, key);
		System.out.println(new String(plaintext, StandardCharsets.UTF_8));
	}

	/**
	 * 生成密钥
	 * @return 密钥16位
	 * @throws Exception 生成密钥异常
	 */
	public static byte[] generateKey() throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
		kg.init(DEFAULT_KEY_SIZE, new SecureRandom());
		return kg.generateKey().getEncoded();
	}

	/**
	 * 获得秘钥 {@link SecretKey}
	 * @param encoded 密钥数据内容
	 * @return 密钥
	 */
	public static SecretKey getSecretKey(byte[] encoded) {
		byte[] key = new byte[DEFAULT_KEY_BYTE_LENGTH];
		System.arraycopy(encoded, 0, key, 0, Math.min(key.length, encoded.length));
		return new SecretKeySpec(key, ALGORITHM);
	}

	/**
	 * 加密，SM4-ECB-PKCS5Padding
	 * @param data 要加密的明文
	 * @param key 密钥16字节
	 * @return 加密后的密文
	 * @throws Exception 加密异常
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		return doFinal(data, key, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 解密，SM4-ECB-PKCS5Padding
	 * @param data 要解密的密文
	 * @param key 密钥16字节
	 * @return 解密后的明文
	 * @throws Exception 解密异常
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		return doFinal(data, key, Cipher.DECRYPT_MODE);
	}

	/**
	 * 对称加解密
	 * @param input 明文（加密模式）或密文（解密模式）
	 * @param key 密钥
	 * @param mode Cipher.ENCRYPT_MODE - 加密；Cipher.DECRYPT_MODE - 解密
	 * @return 密文（加密模式）或明文（解密模式）
	 * @throws Exception 加解密异常
	 */
	private static byte[] doFinal(byte[] input, byte[] key, int mode) throws Exception {
		SecretKeySpec sm4Key = new SecretKeySpec(key, ALGORITHM);
		Cipher cipher = Cipher.getInstance(TRANSFORMATION, BouncyCastleProvider.PROVIDER_NAME);
		cipher.init(mode, sm4Key);
		return cipher.doFinal(input);
	}
}