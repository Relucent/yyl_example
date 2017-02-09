package yyl.example.basic.awt.font;

import java.awt.GraphicsEnvironment;

/**
 * 获取系统中可用的字体的名字
 */
public class GetLocalFontFamily {

	public static void main(String[] agrs) {
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontNames = e.getAvailableFontFamilyNames();
		for (String fontName : fontNames) {
			System.out.println(fontName);
		}
	}

}
