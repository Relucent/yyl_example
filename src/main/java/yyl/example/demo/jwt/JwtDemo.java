package yyl.example.demo.jwt;

import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.NullClaim;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import io.jsonwebtoken.JwtException;

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
public class JwtDemo {

	public static void main(String[] args) throws Exception {

		long currentMillis = System.currentTimeMillis();
		// JWT 生存时间(5秒)
		long ttl = 5000;
		// 生成JWT的时间
		Date iat = new Date(currentMillis);
		// 生成JWT失效时间
		Date exp = new Date(currentMillis + ttl);
		// 签名秘钥
		String secret = "key";
		// 签发人
		String issuer = "root";

		// 算法
		Algorithm algorithm = Algorithm.HMAC256(secret);

		// 本地的密码解码
		JWTCreator.Builder builder = JWT.create();
		builder.withIssuedAt(iat);// 签发时间
		builder.withIssuer(issuer);// 签发人
		builder.withExpiresAt(exp);// 过期时间
		builder.withClaim("subject", "MySubject");// 主题
		String token = builder.sign(algorithm);
		System.out.println(token);

		// 解密
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT jwt = verifier.verify(token);
		Map<String, Claim> claims = jwt.getClaims();

		NullClaim nullClaim = new NullClaim();
		System.out.println(claims.getOrDefault("subject", nullClaim).asString());

		// 等待5秒
		System.out.println("Wait 5 seconds!");
		Thread.sleep(5000);

		try {
			// 这时候Token已经超时了，会抛出异常
			verifier.verify(token);
		} catch (JwtException e) {
			System.err.println(e);
		}
	}
}
