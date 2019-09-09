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
        builder.setId("1");// JWT ID // JWT_ID
        builder.setSubject("MySubject"); // 主题
        builder.claim("custom", "CustomClaim");// 自定义属性;如果属性名与标准属性一致，会覆盖前面的标准属性
        builder.setIssuedAt(iat);// 签发时间
        builder.setExpiration(exp); // 过期时间
        builder.signWith(algorithm, encodedKey); // 签名算法以及密匙

        String token = builder.compact();
        System.out.println(token);

        // 获得JWT解析器
        JwtParser parser = Jwts.parser().setSigningKey(encodedKey);
        // 解析载荷为Claims
        Jws<Claims> jwt = parser.parseClaimsJws(token);
        Claims claims = jwt.getBody();
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
