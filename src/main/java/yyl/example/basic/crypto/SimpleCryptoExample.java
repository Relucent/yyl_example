package yyl.example.basic.crypto;

import java.util.Random;

/**
 * 简化版非对称加密示例
 */
public class SimpleCryptoExample {

    // 公钥
    static class PublicKey {
        int n;
        int e;

        PublicKey(int n, int e) {
            this.n = n;
            this.e = e;
        }
    }

    // 私钥
    static class PrivateKey {
        int n;
        int d;

        PrivateKey(int n, int d) {
            this.n = n;
            this.d = d;
        }
    }

    // 计算最大公约数（欧几里得算法）
    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    // 求模逆元 d * e ≡ 1 mod phi
    private static int modInverse(int e, int phi) {
        // 用暴力法找 d
        for (int d = 1; d < phi; d++) {
            if ((d * e) % phi == 1) {
                return d;
            }
        }
        return -1; // 没找到（小数字才可能）
    }

    // 计算幂 mod n
    private static int modPow(int base, int exponent, int mod) {
        int result = 1;
        base = base % mod;
        for (int i = 0; i < exponent; i++) {
            result = (result * base) % mod;
        }
        return result;
    }

    // 加密
    public static int encrypt(int message, PublicKey pub) {
        return modPow(message, pub.e, pub.n);
    }

    // 解密
    public static int decrypt(int cipher, PrivateKey priv) {
        return modPow(cipher, priv.d, priv.n);
    }

    public static void main(String[] args) {

        // 1️、选两个质数
        int p = 3;
        int q = 11;

        // 2、计算 n 和 φ(n)
        int n = p * q;
        int phi = (p - 1) * (q - 1);

        // 3、选择 e，1<e<phi 且 gcd(e,phi)=1
        int e = 0;
        for (int i = 2; i < phi; i++) {
            if (gcd(i, phi) == 1) {
                e = i;
                break; // 找到第一个满足条件的就用
            }
        }

        // 4、计算 d，使 d*e ≡ 1 mod phi
        int d = modInverse(e, phi);

        PublicKey pub = new PublicKey(n, e);
        PrivateKey priv = new PrivateKey(n, d);

        int message = new Random().nextInt(10); // 要加密的数字
        int cipher = encrypt(message, pub);
        int decrypted = decrypt(cipher, priv);

        System.out.println("原始数字: " + message);
        System.out.println("加密后: " + cipher);
        System.out.println("解密后: " + decrypted);
    }
}
