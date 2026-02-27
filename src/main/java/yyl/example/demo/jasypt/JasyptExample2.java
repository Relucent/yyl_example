package yyl.example.demo.jasypt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;

/**
 * Jasypt 这个Java类包为开发人员提供一种简单的方式来为项目增加加密功能。<br>
 * 包括：密码Digest认证，文本和对象加密，它可与Spring Framework、Hibernate和Acegi Security集成。<br>
 * 
 * <pre>
 * jasypt.encryptor.algorithm = PBEWithHMACSHA512AndAES_256
 * jasypt.encryptor.iv-generator-classname = org.jasypt.iv.RandomIvGenerator
 * </pre>
 */
public class JasyptExample2 {
    public static void main(String[] args) {
        String plain = "123456";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        encryptor.setIvGenerator(new RandomIvGenerator());
        encryptor.setPassword("password");
        String encrypted = encryptor.encrypt(plain);
        System.out.println("ENC(" + encrypted + ")");
        String decrypted = encryptor.decrypt(encrypted);
        System.out.println(decrypted);
    }
}
