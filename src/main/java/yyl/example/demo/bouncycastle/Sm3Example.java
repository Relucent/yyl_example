package yyl.example.demo.bouncycastle;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.Security;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * 国密SM3（摘要算法）实现，该实现基于BC库<br>
 * SM3密码摘要算法是中国国家密码管理局2010年公布的中国商用密码杂凑算法标准。<br>
 * SM3算法适用于商用密码应用中的数字签名和验证，是在SHA-256基础上改进实现的一种算法。<br>
 * SM3算法采用Merkle-Damgard结构，消息分组的长度为512位，生成的摘要长度为256位（32字节，16进制字符串长度64），具有相对较高的安全性。<br>
 * 标准号：GB/T 32905-2016 <br>
 * 中文标准名称：信息安全技术 SM3密码杂凑算法<br>
 * 英文标准名称：Information security techniques—SM3 cryptographic hash algorithm <br>
 * @see <a href="https://openstd.samr.gov.cn/bzgk/gb/newGbInfo?hcno=45B1A67F20F3BF339211C391E9278F5E">GB/T 32905-2016</a>
 */
public class Sm3Example {

    private static final String ALGORITHM = "SM3";
    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public static void main(String[] args) throws Exception {
        String sample = "hello world";
        byte[] input = sample.getBytes(StandardCharsets.UTF_8);
        MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
        messageDigest.update(input);
        byte[] hash = messageDigest.digest();
        messageDigest.reset();
        String digest = Hex.encodeHexString(hash);
        System.out.println(digest);
    }
}