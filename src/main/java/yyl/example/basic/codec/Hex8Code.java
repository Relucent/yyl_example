package yyl.example.basic.codec;

import java.io.IOException;

public class Hex8Code {
	public static void main(String[] args) throws IOException {
		System.out.println(0xFFFFFFFF);
		System.out.println((int) 0xEF);
		char c = '\7'; // 8进制
		System.out.println((int) c);
		System.out.println((int) '\b');
		System.out.println(0 ^ 0);
		while (true) {
			System.out.print((char) System.in.read());
		}
	}
}
