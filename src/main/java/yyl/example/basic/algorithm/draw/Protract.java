package yyl.example.basic.algorithm.draw;
public class Protract {
	public static void main(String[] args) {

		int iSIZE = 7;

		//直角三角形
		for (int i = 0; i < iSIZE; i++) {
			System.out.print("   ");
			for (int j = 0; j < i; j++) {
				System.out.print("*");
			}
			System.out.println();
		}
		System.out.println();

		//棱形 
		for (int i = 0; i < iSIZE * 2 - 1; i++) {
			for (int j = 0; j < iSIZE * 2 - 1; j++) {
				int begIndex = 0;
				int endIndex = 0;
				if (i >= iSIZE) {
					begIndex = (i - iSIZE + 1);
					endIndex = (iSIZE * 3 - i - 3);
				} else {
					begIndex = (iSIZE - i - 1);
					endIndex = (iSIZE + i - 1);
				}
				if (j < begIndex) {
					System.out.print(" ");
				} else if (j > endIndex) {
					System.out.print(" ");
				} else {
					System.out.print("*");
				}
			}
			System.out.println();
		}

		//等边三角形
		for (int i = 0; i < iSIZE; i++) {
			for (int j = 0; j < iSIZE - i; j++) {
				System.out.print(" ");
			}
			for (int j = 0; j < i * 2 - 1; j++) {
				System.out.print("*");
			}

			System.out.println();
		}
		System.out.println();
		//3条边组成的三角
		for (int i = 0; i < iSIZE; i++) {
			if (i == iSIZE - 1) {//底
				for (int j = 0; j < iSIZE * 2 - 1; j++) {
					System.out.print("*");
				}
			} else {
				for (int j = 0; j < iSIZE * 2 - 1; j++) {
					if (j == (iSIZE - i - 1) || j == (iSIZE + i - 1)) {
						System.out.print("*");
					} else {
						System.out.print(" ");
					}
				}
			}
			System.out.println();
		}
	}
}