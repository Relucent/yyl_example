package yyl.example.demo.bouncycastle;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Provider;

import javax.crypto.Cipher;

public class GetBlockSizeTest {
    public static void main(String[] args) throws Exception {
        Provider provider = new org.bouncycastle.jce.provider.BouncyCastleProvider();

        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = gen.generateKeyPair();
        Key key = keyPair.getPrivate();

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", provider);

        cipher.init(Cipher.ENCRYPT_MODE, key);
        System.out.println(cipher.getBlockSize());

        cipher.init(Cipher.DECRYPT_MODE, key);
        System.out.println(cipher.getBlockSize());
    }
}
