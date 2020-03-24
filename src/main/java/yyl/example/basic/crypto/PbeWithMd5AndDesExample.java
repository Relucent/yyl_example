package yyl.example.basic.crypto;

import java.util.Random;
import java.util.StringTokenizer;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * PBEWithMD5AndDES算法加密解密
 */
public class PbeWithMd5AndDesExample {

	private final String password = "e_f_codd";
	private final String encoding = "UTF-8";

	public PbeWithMd5AndDesExample() {
	}

	/**
	 * 将加密文本进行解密
	 * @param encryptText String
	 * @return String
	 */
	public String decrypt(String encryptText) throws Exception {
		if (encryptText == null || encryptText.length() == 0) {
			return "";
		}
		PBEKeySpec pbks = new PBEKeySpec((password).toCharArray());

		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		SecretKey k = skf.generateSecret(pbks);

		StringTokenizer st = new StringTokenizer(hex2string(encryptText), " ");
		int num = 0;
		byte[] salt = new byte[8];
		while (st.hasMoreTokens() && (num < 8)) {
			salt[num] = (byte) (Integer.parseInt(st.nextToken()));
			num++;
		}

		int count = 0;
		byte[] cbtemp = new byte[2000];
		while (st.hasMoreTokens()) {
			cbtemp[count] = (byte) (Integer.parseInt(st.nextToken()));
			count++;
		}
		byte[] cb = new byte[count];
		for (int i = 0; i < cb.length; i++) {
			cb[i] = cbtemp[i];
		}
		Cipher cp = Cipher.getInstance("PBEWithMD5AndDES");
		PBEParameterSpec ps = new PBEParameterSpec(salt, 1000);
		cp.init(Cipher.DECRYPT_MODE, k, ps);

		byte[] ptext = cp.doFinal(cb);

		return new String(ptext,encoding);

	}

	/**
	 * 将传进来的明文以PBEWithMD5AndDES算法进行加密
	 * 
	 * @param text String
	 * @return String
	 */
	public String encrypt(String text) throws Exception {
		if (text == null || text.length() == 0) {
			return "";
		}
		PBEKeySpec pbks = new PBEKeySpec(password.toCharArray());
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		SecretKey k = skf.generateSecret(pbks);
		byte[] salt = new byte[8];
		Random r = new Random();
		r.nextBytes(salt);
		Cipher cp = Cipher.getInstance("PBEWithMD5AndDES");
		PBEParameterSpec ps = new PBEParameterSpec(salt, 1000);
		cp.init(Cipher.ENCRYPT_MODE, k, ps);
		byte[] ptext = text.getBytes(encoding);
		byte[] ctext = cp.doFinal(ptext);

		String result = "";
		for (int i = 0; i < salt.length; i++) {
			result += salt[i] + " ";
		}

		for (int i = 0; i < ctext.length; i++) {
			result += ctext[i] + " ";
		}
		return string2hex(result);
	}

	/**
	 * 将16进制编码的字符串转换为带有空格分隔的字符串 比如:F89ADFCA2AE9719817D3575A9540600C ==> -8 -102 -33 -54 42 -23 113 -104 23 -45 87 90 -107 64 96 12
	 * @param s
	 * @return
	 */
	private String hex2string(String s) {
		String ret = "";
		for (int i = 0; i < s.length() / 2; i++) {
			ret += String.valueOf(Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16)) + " ";

		}
		if (ret.endsWith(" "))
			return ret.substring(0, ret.length() - 1);
		return ret;
	}

	/**
	 * 将加密的带有空格分隔的字符转换为16进制编码的字符串. 比如:-8 -102 -33 -54 42 -23 113 -104 23 -45 87 90 -107 64 96 12 ==> F89ADFCA2AE9719817D3575A9540600C
	 * @param str
	 * @return
	 */
	private String string2hex(String str) {
		String[] split = str.split(" ");
		byte[] b = new byte[split.length];
		for (int i = 0; i < split.length; i++) {
			b[i] = Byte.parseByte(split[i]);
		}

		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;

		}
		return hs.toUpperCase();
	}
	public static void main(String[] args) {
		try {
			PbeWithMd5AndDesExample en = new PbeWithMd5AndDesExample();
			String str = "password";
			System.out.println("PBEWithMD5AndDES加密后为：" + en.encrypt(str));
			System.out.println("PBEWithMD5AndDES解密后为：" + en.decrypt(en.encrypt(str)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}