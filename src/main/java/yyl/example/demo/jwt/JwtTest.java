package yyl.example.demo.jwt;

import java.util.Base64;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JSON Web token (JWT), 是为了在网络应用环境间传递声明而执行的一种基于JSON的开放标准(RFC 7519)。<br>
 * 该token被设计为紧凑且安全的，特别适用于分布式站点的单点登录(SSO)场景。<br>
 * JWT的声明一般被用来在身份提供者和服务提供者间传递被认证的用户身份信息，以便于从资源服务器获取资源，也可以增加一些额外的其它业务逻辑所必须的声明信息，该token也可直接被用于认证，也可被加密。<br>
 * 加密后JWT信息由.分割的三部分组成，分别为Header、Payload、Signature (头、载荷、签名)<br>
 * Header 主要包含两个部分,alg指加密类型，可选值为HS256、RSA等等，typ=JWT为固定值，表示token的类型<br>
 * { "alg": "HS256", "typ": "JWT" } <br>
 * Payload 也被称为Claims. 包含要签署的任何信息，格式为JSON： { "sub": "subject"} <br>
 * 其中有一部分预定义的标准属性(推荐，但不强制使用)<br>
 * iss (issuer)发布者的url地址<br>
 * sub (subject)该JWT所面向的用户，用于处理特定应用，不是常用的字段<br>
 * aud (audience)接受者的url地址<br>
 * exp (expiration) 该jwt销毁的时间；unix时间戳<br>
 * nbf (not before) 该jwt的使用时间不能早于该时间；unix时间戳<br>
 * iat (issued at) 该jwt的发布时间；unix 时间戳<br>
 * JWT的标准属性使用三个字母的原因是保证 JWT的紧凑 。<br>
 * Signature 是为对Header、Payload的签名<br>
 */
public class JwtTest {

    public static void main(String[] args) throws Exception {

        long currentMillis = System.currentTimeMillis();
        // JWT 生存时间(5秒)
        long ttl = 5000;
        // 生成JWT的时间
        Date iat = new Date(currentMillis);
        // 生成JWT失效时间
        Date exp = new Date(currentMillis + ttl);
        // 指定签名的时候使用的签名算法
        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
        // 签名秘钥
        String secret = "key";
        // 本地的密码解码
        byte[] encodedKey = Base64.getEncoder().encode(secret.getBytes());

        JwtBuilder builder = Jwts.builder();
        builder.setHeaderParam(JwsHeader.TYPE, JwsHeader.JWT_TYPE);
        builder.setHeaderParam(JwsHeader.ALGORITHM, algorithm.getValue());
        builder.setId("1");// JWT ID 可选
        builder.setSubject("MySubject"); // 主题
        builder.claim("custom", "CustomClaim");// 自定义属性;如果属性名与标准属性一致，会覆盖前面的标准属性
        builder.setIssuedAt(iat);// 签发时间
        builder.setExpiration(exp); // 过期时间
        builder.signWith(algorithm, encodedKey); // 签名算法以及密匙
        String token = builder.compact(); // 生成Token
        System.out.println(token);

        // 获得JWT解析器
        JwtParser parser = Jwts.parser().setSigningKey(encodedKey);
        // 解析载荷为Claims
        Jws<Claims> jws = parser.parseClaimsJws(token);
        Claims claims = jws.getBody();
        System.out.println(claims);

        // 等待5秒
        System.out.println("Wait 5 seconds!");
        Thread.sleep(5000);

        try {
            // 这时候Token已经超时了，会抛出异常
            parser.parseClaimsJws(token);
        } catch (JwtException e) {
            System.err.println(e);
        }
    }

}
