package yyl.example.demo.nimbus;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.UUID;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

/**
 * <h2>Nimbus JOSE + JWT</h2><br>
 * 官方：https://connect2id.com/products/nimbus-jose-jwt<br>
 * 用途：处理 JWT（JSON Web Token）、JWS（JSON Web Signature）、JWE（JSON Web Encryption）、JWK（JSON Web Key） 等标准 Web 安全协议<br>
 */
public class NimbusJoseJwtExample1 {

    public static void main(String[] args) throws Exception {

        // 1、创建 RSA 密钥对（公钥 + 私钥）
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        // 2、使用 Nimbus 创建 JWK
        RSAKey jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()) // 随机 kid
                .keyUse(KeyUse.SIGNATURE) // 用于签名
                .build();

        // 可以生成 JWK JSON
        String jwkJson = jwk.toJSONString();
        System.out.println("生成的 JWK JSON: \n" + jwkJson);

        // 3、创建 JWT 并签名
        JWSSigner signer = new RSASSASigner(jwk); // 使用私钥签名

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder().subject("alice").issuer("https://example.com")
                .expirationTime(new Date(new Date().getTime() + 60 * 1000)) // 1分钟有效
                .claim("role", "admin").build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(jwk.getKeyID()) // kid 放到
                                                                                                            // header
                .build(), claimsSet);

        signedJWT.sign(signer);

        String token = signedJWT.serialize();
        System.out.println("生成的 JWT: \n" + token);

        // 4️、解析 JWK（通常从 JSON 文件获取）
        RSAKey parsedJwk = RSAKey.parse(jwkJson); // 从 JSON 解析
        RSAPublicKey parsedPublicKey = parsedJwk.toRSAPublicKey();

        // 5、验证 JWT
        JWSVerifier verifier = new RSASSAVerifier(parsedPublicKey);
        boolean isValid = signedJWT.verify(verifier);

        System.out.println("JWT 验证结果: " + isValid);
        if (isValid) {
            System.out.println("Payload: " + signedJWT.getJWTClaimsSet().toJSONObject());
        }
    }
}
