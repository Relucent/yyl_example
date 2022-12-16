package yyl.example.demo.bouncycastle;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithID;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.crypto.signers.StandardDSAEncoding;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.bouncycastle.jcajce.spec.OpenSSHPrivateKeySpec;
import org.bouncycastle.jcajce.spec.OpenSSHPublicKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.Strings;

/**
 * 国密SM2（非对称加密算法）实现，该实现基于BC库<br>
 * SM2算法只支持公钥加密，私钥解密<br>
 * 标准号：GB/T 35276-2017 <br>
 * 中文标准名称：信息安全技术 SM2密码算法使用规范 <br>
 * 英文标准名称：Information security technology—SM2 cryptographic algorithm usage specification<br>
 * @see <a href="https://openstd.samr.gov.cn/bzgk/gb/newGbInfo?hcno=2127A9F19CB5D7F20D17D334ECA63EE5">GB/T 35276-2017</a>
 */
public class Sm2Example {

	/** BouncyCastle 算法支持 */
	private static final BouncyCastleProvider BC_PROVIDER = new BouncyCastleProvider();
	static {
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
			Security.addProvider(BC_PROVIDER);
		}
	}

	/** SM2默认曲线 */
	private static final String SM2_CURVE_NAME = "sm2p256v1";
	/** 秘钥生成算法 */
	private static final String EC_ALGORITHM = "EC";
	/** SM2非对称加密的结果由C1,C2,C3三部分组成。 其中C1是生成随机数的计算出的椭圆曲线点，C2是密文数据，C3是SM3的摘要值。 新标准的是按C1C3C2顺序存放。 */
	private static final SM2Engine.Mode SM2_ENGINE_MODE = SM2Engine.Mode.C1C3C2;
	/** SM3摘要的实现类 */
	private static final Digest DIGEST = new SM3Digest();
	/** SM2公钥加密引擎 */
	private static final SM2Engine SM2_ENGINE = new SM2Engine(DIGEST, SM2_ENGINE_MODE);
	/** SM2数字签名算法 */
	private static final SM2Signer SM_SIGNER = new SM2Signer(StandardDSAEncoding.INSTANCE, DIGEST);
	/** 椭圆曲线ECParameters ASN.1 结构 */
	private static final X9ECParameters X9_EC_PARAMETERS = GMNamedCurves.getByName(SM2_CURVE_NAME);
	/** SM2推荐曲线参数 */
	private static final ECDomainParameters SM2_DOMAIN_PARAMS = new ECDomainParameters(//
			X9_EC_PARAMETERS.getCurve(), //
			X9_EC_PARAMETERS.getG(), //
			X9_EC_PARAMETERS.getN()//
	);
	/** 获取椭圆曲线KEY生成器 */
	private static final KeyFactory EC_KEY_FACTORY;
	static {
		try {
			EC_KEY_FACTORY = KeyFactory.getInstance(EC_ALGORITHM, BC_PROVIDER);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}

	public static void main(String[] args) throws Exception {
		KeyPair keyPair = createECKeyPair();
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();

		String original = "你好世界";

		byte[] plaintext = original.getBytes(StandardCharsets.UTF_8);
		byte[] ciphertext = encrypt(plaintext, publicKey);
		System.out.println(Base64.getEncoder().encodeToString(ciphertext));

		byte[] decrypted = decrypt(ciphertext, privateKey);

		System.out.println(new String(decrypted, StandardCharsets.UTF_8));

		byte[] sign = sign(plaintext, privateKey);
		System.out.println(verify(plaintext, sign, publicKey));
	}

	/**
	 * @Description 生成秘钥对
	 * @return KeyPair
	 */
	public static KeyPair createECKeyPair() {
		// 使用标准名称创建EC参数生成的参数规范
		final ECGenParameterSpec sm2Spec = new ECGenParameterSpec(SM2_CURVE_NAME);
		// 获取一个椭圆曲线类型的密钥对生成器
		final KeyPairGenerator kpg;
		try {
			kpg = KeyPairGenerator.getInstance(EC_ALGORITHM, BC_PROVIDER);

			// 使用SM2的算法域参数集和指定的随机源初始化密钥生成器
			kpg.initialize(sm2Spec, new SecureRandom());

			// 通过密钥生成器生成密钥对
			return kpg.generateKeyPair();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 使用公钥加密数据<br>
	 * @param data 被加密的数据
	 * @param publicKey 公钥
	 * @return 加密后的数据
	 */
	public synchronized static byte[] encrypt(byte[] data, PublicKey publicKey) {
		try {
			ECPublicKeyParameters publicKeyParameters = (ECPublicKeyParameters) ECUtil.generatePublicKeyParameter(publicKey);
			ParametersWithRandom parametersWithRandom = new ParametersWithRandom(publicKeyParameters);
			DIGEST.reset();
			SM2_ENGINE.init(true, parametersWithRandom);
			return SM2_ENGINE.processBlock(data, 0, data.length);
		} catch (InvalidCipherTextException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 使用私钥解密数据<br>
	 * @param data 被解密的数据
	 * @param publicKey 公钥
	 * @param privateKey 私钥参
	 * @return 解密后的数据
	 */
	public synchronized static byte[] decrypt(byte[] data, PrivateKey privateKey) {
		try {
			ECPrivateKeyParameters privateKeyParameters = (ECPrivateKeyParameters) ECUtil.generatePrivateKeyParameter(privateKey);
			DIGEST.reset();
			SM2_ENGINE.init(false, privateKeyParameters);
			return SM2_ENGINE.processBlock(data, 0, data.length);
		} catch (InvalidCipherTextException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 私钥签名
	 * @param data 待签名的数据
	 * @param privateKey 私钥
	 * @return 签名
	 */
	public synchronized static byte[] sign(byte[] data, PrivateKey privateKey) {
		try {
			ECPrivateKeyParameters privateKeyParameters = (ECPrivateKeyParameters) ECUtil.generatePrivateKeyParameter(privateKey);
			// 初始化签名实例,带上ID,国密的要求,ID默认值:1234567812345678
			CipherParameters param = new ParametersWithRandom(privateKeyParameters);
			byte[] id = Strings.toByteArray("1234567812345678");
			CipherParameters cipherParameters = new ParametersWithID(param, id);
			SM_SIGNER.init(true, cipherParameters);
			SM_SIGNER.update(data, 0, data.length);
			return SM_SIGNER.generateSignature();
		} catch (CryptoException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 用公钥检验数字签名的合法性
	 * @param data 数据签名后的数据
	 * @param sign 签名
	 * @return 是否验证通过
	 */
	public synchronized static boolean verify(byte[] data, byte[] sign, PublicKey publicKey) {
		try {
			ECPublicKeyParameters publicKeyParameters = (ECPublicKeyParameters) ECUtil.generatePublicKeyParameter(publicKey);
			byte[] id = Strings.toByteArray("1234567812345678");
			CipherParameters cipherParameters = new ParametersWithID(publicKeyParameters, id);
			SM_SIGNER.init(false, cipherParameters);
			SM_SIGNER.update(data, 0, data.length);
			return SM_SIGNER.verifySignature(sign);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据私钥参数获取公钥参数
	 * @param privateKeyParameters 私钥参数
	 * @return 公钥参数
	 */
	public static ECPublicKeyParameters getPublicParams(ECPrivateKeyParameters privateKeyParameters) {
		final ECDomainParameters domainParameters = privateKeyParameters.getParameters();
		final ECPoint q = new FixedPointCombMultiplier().multiply(domainParameters.getG(), privateKeyParameters.getD());
		return new ECPublicKeyParameters(q, domainParameters);
	}

	/**
	 * 尝试解析转换各种类型私钥为{@link ECPrivateKeyParameters}，支持包括：<br>
	 * 1、D值<br>
	 * 2、PKCS#8<br>
	 * 3、PKCS#1<br>
	 * @param privateKeyBytes 私钥
	 * @return {@link ECPrivateKeyParameters}
	 */
	public static ECPrivateKeyParameters decodePrivateKeyParams(byte[] privateKeyBytes) {
		try {
			// 尝试D值
			BigInteger d = BigIntegers.fromUnsignedByteArray(privateKeyBytes);
			return new ECPrivateKeyParameters(d, SM2_DOMAIN_PARAMS);
		} catch (Exception ignore) {
			// ignore
		}
		PrivateKey privateKey;
		// 尝试PKCS#8
		try {
			KeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			privateKey = EC_KEY_FACTORY.generatePrivate(keySpec);
		} catch (Exception ignore) {
			// 尝试PKCS#1
			try {
				privateKey = EC_KEY_FACTORY.generatePrivate(new OpenSSHPrivateKeySpec(privateKeyBytes));
			} catch (InvalidKeySpecException e) {
				throw new RuntimeException(e);
			}
		}
		return toPrivateParams(privateKey);
	}

	/**
	 * 私钥转换为 {@link ECPrivateKeyParameters}
	 * @param privateKey 私钥，传入null返回null
	 * @return {@link ECPrivateKeyParameters}或null
	 */
	public static ECPrivateKeyParameters toPrivateParams(PrivateKey privateKey) {
		if (privateKey == null) {
			return null;
		}
		try {
			return (ECPrivateKeyParameters) ECUtil.generatePrivateKeyParameter(privateKey);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 尝试解析转换各种类型公钥为{@link ECPublicKeyParameters}，支持包括：<br>
	 * 1、Q值<br>
	 * 2、X.509<br>
	 * 3、PKCS#1<br>
	 * @param publicKeyBytes 公钥
	 * @return {@link ECPublicKeyParameters}
	 */
	public static ECPublicKeyParameters decodePublicKeyParams(byte[] publicKeyBytes) {
		try {
			// 尝试Q值
			ECCurve curve = SM2_DOMAIN_PARAMS.getCurve();
			ECPoint point = curve.decodePoint(publicKeyBytes);
			return new ECPublicKeyParameters(point, SM2_DOMAIN_PARAMS);
		} catch (Exception ignore) {
			// ignore
		}
		PublicKey publicKey;
		// 尝试X.509
		try {
			publicKey = EC_KEY_FACTORY.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
		} catch (Exception ignore) {
			// 尝试PKCS#1
			try {
				publicKey = EC_KEY_FACTORY.generatePublic(new OpenSSHPublicKeySpec(publicKeyBytes));
			} catch (InvalidKeySpecException e) {
				throw new RuntimeException(e);
			}
		}
		return toPublicParams(publicKey);
	}

	/**
	 * 公钥转换为 {@link ECPublicKeyParameters}
	 * @param publicKey 公钥，传入null返回null
	 * @return {@link ECPublicKeyParameters}或null
	 */
	public static ECPublicKeyParameters toPublicParams(PublicKey publicKey) {
		if (publicKey == null) {
			return null;
		}
		try {
			return (ECPublicKeyParameters) ECUtil.generatePublicKeyParameter(publicKey);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		}
	}
}
