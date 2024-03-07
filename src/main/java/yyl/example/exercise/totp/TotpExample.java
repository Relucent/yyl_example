package yyl.example.exercise.totp;

import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 基于时间同步（Time-based One-time Password）算法（TOTP）的示例代码<br>
 * 算法参考：<a href="https://www.rfc-editor.org/rfc/rfc6238">rfc6238</a>
 */
public class TotpExample {

	private static final int[] DIGITS_POWER = { //
			1, // 0
			10, // 1
			100, // 2
			1000, // 3
			10000, // 4
			100000, // 5
			1000000, // 6
			10000000, // 7
			100000000 // 8
	};

	/**
	 * 该方法使用JCE（Java Cryptography Extension）来提供加密算法。<br>
	 * 使用MAC(Message Authentication Code,消息认证码算法)，通过指定的秘钥来计算消息的认证码。<br>
	 * @param crypto 加密算法（HmacSHA1、HmacSHA256、HmacSHA512）
	 * @param keyBytes 用于HMAC密钥的字节
	 * @param text 指定的消息
	 */
	private static byte[] hmacSha(String crypto, byte[] keyBytes, byte[] text) {
		try {
			Mac hmac = Mac.getInstance(crypto);
			SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
			hmac.init(macKey);
			return hmac.doFinal(text);
		} catch (GeneralSecurityException gse) {
			throw new UndeclaredThrowableException(gse);
		}
	}

	/**
	 * 将16进制字符串转换为字节数组
	 * @param hex 16进制字符串
	 * @return 字节数组
	 */
	private static byte[] hexStr2Bytes(String hex) {
		// 添加一个字节以获得正确的转换，可以转换以"0"开头的值
		byte[] bArray = new BigInteger("10" + hex, 16).toByteArray();
		// 复制字节，排除第一个字节（因为第一个10是前面加的）
		byte[] ret = new byte[bArray.length - 1];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = bArray[i + 1];
		}
		return ret;
	}

	/**
	 * 参数生成一个TOTP值
	 * @param key 秘钥（16进制字符串）
	 * @param time 时间的值
	 * @param returnDigits 要返回的位数
	 * @return 以10为底的数字字符串
	 */

	public static String generateTOTP(String key, String time, String returnDigits) {
		return generateTOTP(key, time, returnDigits, "HmacSHA1");
	}

	/**
	 * 参数生成一个TOTP值
	 * @param key 秘钥（16进制字符串）
	 * @param time 时间的值
	 * @param returnDigits 要返回的位数
	 * @return 以10为底的数字字符串
	 */

	public static String generateTOTP256(String key, String time, String returnDigits) {
		return generateTOTP(key, time, returnDigits, "HmacSHA256");
	}

	/**
	 * 参数生成一个TOTP值
	 * @param key 秘钥（16进制字符串）
	 * @param time 时间的值
	 * @param returnDigits 要返回的位数
	 * @return 以10为底的数字字符串
	 */

	public static String generateTOTP512(String key, String time, String returnDigits) {
		return generateTOTP(key, time, returnDigits, "HmacSHA512");
	}

	/**
	 * 参数生成一个TOTP值
	 * @param key 秘钥（16进制字符串）
	 * @param time 时间的值
	 * @param returnDigits 要返回的位数 返回长度 --6
	 * @param crypto 使用的加密算法
	 * @return 以10为底的数字字符串
	 */

	public static String generateTOTP(String key, String time, String returnDigits, String crypto) {
		int codeDigits = Integer.decode(returnDigits).intValue();

		// 前8个字节用用作系数
		while (time.length() < 16) {
			time = "0" + time;
		}

		byte[] msg = hexStr2Bytes(time);
		byte[] k = hexStr2Bytes(key);
		byte[] hash = hmacSha(crypto, k, msg);

		// 将所选字节放入结果int
		int offset = hash[hash.length - 1] & 0xf;
		int binary = ((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16) | ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);

		int otp = binary % DIGITS_POWER[codeDigits];

		String result = Integer.toString(otp);
		while (result.length() < codeDigits) {
			result = "0" + result;
		}
		return result;
	}

	public static void main(String[] args) {
		// Seed for HMAC-SHA1 - 20 bytes
		String seed = "3132333435363738393031323334353637383930";
		// Seed for HMAC-SHA256 - 32 bytes
		String seed32 = ""//
				+ "3132333435363738393031323334353637383930" //
				+ "313233343536373839303132";
		// Seed for HMAC-SHA512 - 64 bytes
		String seed64 = ""//
				+ "3132333435363738393031323334353637383930" //
				+ "3132333435363738393031323334353637383930"//
				+ "3132333435363738393031323334353637383930" //
				+ "31323334";
		final long T0 = 0;
		final long X = 30;

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {

			long testTime[] = { 59L, 1111111109L, 1111111111L, 1234567890L, 2000000000L, 20000000000L };

			System.out.println("+---------------+-----------------------+------------------+--------+--------+");
			System.out.println("|  Time(sec)    |   Time (UTC format)   | Value of T(Hex)  |  TOTP  | Mode   |");
			System.out.println("+---------------+-----------------------+------------------+--------+--------+");

			for (int i = 0; i < testTime.length; i++) {
				long T = (testTime[i] - T0) / X;
				String steps = Long.toHexString(T).toUpperCase();
				while (steps.length() < 16) {
					steps = "0" + steps;
				}
				String fmtTime = String.format("%1$-11s", testTime[i]);
				String utcTime = df.format(new Date(testTime[i] * 1000));
				// seed
				System.out.print("|  " + fmtTime + "  |  " + utcTime + "  | " + steps + " |");
				System.out.println(generateTOTP(seed, steps, "8", "HmacSHA1") + "| SHA1   |");

				// seed32
				System.out.print("|  " + fmtTime + "  |  " + utcTime + "  | " + steps + " |");
				System.out.println(generateTOTP(seed32, steps, "8", "HmacSHA256") + "| SHA256 |");

				// seed64
				System.out.print("|  " + fmtTime + "  |  " + utcTime + "  | " + steps + " |");
				System.out.println(generateTOTP(seed64, steps, "6", "HmacSHA512") + "| SHA512 |");

				System.out.println("+---------------+-----------------------+------------------+--------+--------+");
			}
		} catch (final Exception e) {
			System.out.println("Error : " + e);
		}
	}
}
