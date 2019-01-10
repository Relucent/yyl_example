package yyl.example.demo.jasypt;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * Jasypt 这个Java类包为开发人员提供一种简单的方式来为项目增加加密功能。<br>
 * 包括：密码Digest认证，文本和对象加密，它可与Spring Framework、Hibernate和Acegi Security集成。<br>
 * @author Administrator
 */
public class JasyptSample {
    public static void main(String[] args) {

        BasicTextEncryptor encryptor = new BasicTextEncryptor();

        encryptor.setPassword("passwoord");
        String ciphertext = encryptor.encrypt("12345");

        System.out.println(ciphertext);

        String plaintext = encryptor.decrypt(ciphertext);
        System.out.println(plaintext);
    }
}
