package yyl.example.other.cert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.util.Base64;

/**
 * Keystore是一个密码保护的文件，存放私钥和证书。可以通过JDK自带的keytool工具生成。<br>
 * 但是keytool工具，并没有提供方便的方法，从keystore文件中到处私钥和证书。<br>
 * 所以可以通过JDK提供的java.security.KeyStore 类来编码完成相关工作。<br>
 * 测试环境 JDK8
 */
public class ExportPrivateKey {

	private File keystoreFile;
	private String keyStoreType;
	private char[] password;
	private String alias;
	private File exportedFile;

	public ExportPrivateKey(File keystoreFile, String keyStoreType, char[] password, String alias, File exportedFile) {
		this.keystoreFile = keystoreFile;
		this.keyStoreType = keyStoreType;
		this.password = password;
		this.alias = alias;
		this.exportedFile = exportedFile;
	}

	public void export() throws Exception {
		KeyStore keystore = KeyStore.getInstance(keyStoreType);
		keystore.load(new FileInputStream(keystoreFile), password);
		KeyPair keyPair = getPrivateKey(keystore, alias, password);
		PrivateKey privateKey = keyPair.getPrivate();
		String encoded = new String(Base64.getEncoder().encode(privateKey.getEncoded()), "UTF-8");
		FileWriter fw = new FileWriter(exportedFile);
		fw.write("—–BEGIN PRIVATE KEY—–\n");
		fw.write(encoded);
		fw.write("\n—–END PRIVATE KEY—–");
		fw.close();
	}

	public static KeyPair getPrivateKey(KeyStore keystore, String alias, char[] password) {
		try {
			Key key = keystore.getKey(alias, password);
			if (key instanceof PrivateKey) {
				Certificate cert = keystore.getCertificate(alias);
				PublicKey publicKey = cert.getPublicKey();
				return new KeyPair(publicKey, (PrivateKey) key);
			}
		} catch (UnrecoverableKeyException e) {
		} catch (NoSuchAlgorithmException e) {
		} catch (KeyStoreException e) {
		}
		return null;
	}
}
