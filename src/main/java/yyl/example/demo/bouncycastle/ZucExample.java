package yyl.example.demo.bouncycastle;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;
import java.util.concurrent.ThreadLocalRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * 祖冲之算法集（ZUC算法）是由我国学者自主设计的加密和完整性算法，包括祖冲之算法、加密算法128-EEA3和完整性算法128-EIA3，已经被国际组织3GPP推荐为4G无线通信的第三套国际加密和完整性标准的候选算法。<br>
 * 标准号：GB/T 33133.1-2016 <br>
 * 中文标准名称：信息安全技术 祖冲之序列密码算法 <br>
 * 英文标准名称：Information security technology—ZUC stream cipher algorithm <br>
 * @see <a href="https://openstd.samr.gov.cn/bzgk/gb/newGbInfo?hcno=8C41A3AEECCA52B5C0011C8010CF0715">GB/T 33133.1-2016</a>
 */
public class ZucExample {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /** 算法名称 */
    private static final String ALGORITHM = "ZUC-256";
    /** 密钥256位，32字节（256÷8=32） */
    private static final int DEFAULT_KEY_SIZE = 256;
    /** 初始化向量25字节（256÷8=32） */
    private static final int DEFAULT_IV_BYTE_LENGTH = 25;

    public static void main(String[] args) throws Exception {
        byte[] key = generateKey();
        byte[] iv = generateIvParameter();
        String original = "你好世界！";
        byte[] ciphertext = encrypt(original.getBytes(StandardCharsets.UTF_8), key, iv);
        byte[] plaintext = decrypt(ciphertext, key, iv);
        System.out.println(new String(plaintext, StandardCharsets.UTF_8));
    }

    /**
     * 生成密钥
     * @return 密钥16位
     * @throws Exception 生成密钥异常
     */
    private static byte[] generateKey() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
        kg.init(DEFAULT_KEY_SIZE, new SecureRandom());
        return kg.generateKey().getEncoded();
    }

    /**
     * 初始化向量
     * @return 初始化向量
     */
    private static byte[] generateIvParameter() {
        byte[] iv = new byte[DEFAULT_IV_BYTE_LENGTH];
        ThreadLocalRandom.current().nextBytes(iv);
        return iv;
    }

    /**
     * 加密
     * @param data 要加密的明文
     * @param key 密钥
     * @param iv 初始化向量
     * @return 加密后的密文
     * @throws Exception 加密异常
     */
    private static byte[] encrypt(byte[] data, byte[] key, byte[] iv) throws Exception {
        return doFinal(data, key, iv, Cipher.ENCRYPT_MODE);
    }

    /**
     * 解密
     * @param data 要解密的密文
     * @param key 密钥
     * @param iv 初始化向量
     * @return 解密后的明文
     * @throws Exception 解密异常
     */
    private static byte[] decrypt(byte[] data, byte[] key, byte[] iv) throws Exception {
        return doFinal(data, key, iv, Cipher.DECRYPT_MODE);
    }

    /**
     * 对称加解密
     * @param input 明文（加密模式）或密文（解密模式）
     * @param key 密钥
     * @param iv 初始化向量
     * @param mode Cipher.ENCRYPT_MODE - 加密；Cipher.DECRYPT_MODE - 解密
     * @return 密文（加密模式）或明文（解密模式）
     * @throws Exception 加解密异常
     */
    private static byte[] doFinal(byte[] input, byte[] key, byte[] iv, int mode) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
        cipher.init(mode, keySpec, ivSpec);
        return cipher.doFinal(input);
    }
}
