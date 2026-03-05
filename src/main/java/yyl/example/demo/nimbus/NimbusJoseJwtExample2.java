package yyl.example.demo.nimbus;

import java.util.Date;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

/**
 * <h2>Nimbus JOSE + JWT</h2><br>
 */
public class NimbusJoseJwtExample2 {

    public static void main(String[] args) throws Exception {

        // 自定义密钥字符串（生产环境建议至少32位以上，越复杂越安全）
        String secretString = "123456789abcdefghijklmnopqrstuvw";

        // 2. 转换为 HMAC 密钥
        OctetSequenceKey hmacKey = new OctetSequenceKey.Builder(secretString.getBytes())//
                .algorithm(JWSAlgorithm.HS256)//
                .build();

        System.out.println("加载完成的 HMAC 密钥: " + hmacKey.toJSONString());

        String jwt = create(hmacKey);

        System.out.println("生成的 JWT：\n" + jwt);

        // 验证
        validate(jwt, hmacKey);
    }

    static String create(OctetSequenceKey hmacKey) throws Exception {

        // 构建 JWT 载荷
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()//
                .subject("subject")//
                .expirationTime(new Date(new Date().getTime() + 60 * 1000)) // 1分钟有效
                .claim("username", "test")//
                .build();

        // 设置签名算法 HS256
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        // 签名，生成最终 JWT
        SignedJWT signedJWT = new SignedJWT(header, claimsSet);
        signedJWT.sign(new MACSigner(hmacKey));

        // 得到最终的 JWT 字符串
        return signedJWT.serialize();
    }

    static boolean validate(String token, OctetSequenceKey hmacKey) {
        try {
            // 1. 解析 JWT
            SignedJWT signedJwt = SignedJWT.parse(token);

            // 2. 创建 HMAC 验签器
            MACVerifier verifier = new MACVerifier(hmacKey);

            // 3. 校验签名（防篡改）
            if (!signedJwt.verify(verifier)) {
                System.err.println("验签失败：密钥错误或 JWT 被篡改");
                return false;
            }

            // 4. 校验过期时间 exp
            Date now = new Date();
            if (signedJwt.getJWTClaimsSet().getExpirationTime().before(now)) {
                System.err.println("JWT 已过期");
                return false;
            }

            // 校验通过
            System.out.println("JWT 校验成功");
            return true;

        } catch (Exception e) {
            System.err.println("JWT 无效：" + e.getMessage());
            return false;
        }
    }

}
