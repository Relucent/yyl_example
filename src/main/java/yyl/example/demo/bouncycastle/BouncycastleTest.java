package yyl.example.demo.bouncycastle;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Bouncy castle（轻量级密码术包）是一种用于 Java 平台的开放源码的轻量级密码术包；它支持大量的密码术算法，并提供JCE 1.2.1的实现。<br>
 */
public class BouncycastleTest {

    public static void main(String[] args) {

        String text = "hello world";

        // JDK默认不支持RipeMD160，所以会抛出异常
        try {
            System.out.println(digest(text));
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }

        // 注册BouncyCastle:
        Security.addProvider(new BouncyCastleProvider());

        // BouncyCastle 支持RipeMD160算法，所以可以正常输出哈希摘要信息
        try {
            System.out.println(digest(text));
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
    }

    public static String digest(String text) throws NoSuchAlgorithmException {
        // RIPEMD (RACE原始完整性校验讯息摘要)是一种加密哈希函数，这个算法JDK默认是不支持的
        MessageDigest md = MessageDigest.getInstance("RipeMD160");
        md.update(text.getBytes(StandardCharsets.UTF_8));
        byte[] result = md.digest();
        return new BigInteger(1, result).toString(16);
    }
}
