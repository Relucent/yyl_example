package yyl.example.basic.awt.screen;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

/**
 * 获取屏幕缩放比例
 */
public class ScreenInfoDemo {
	public static void main(String[] args) {
		// 显示器尺寸
		Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = defaultToolkit.getScreenSize();
		double width2 = screenSize.getWidth();
		double height2 = screenSize.getHeight();
		System.out.println(String.format("显示器尺寸：%s x %s", width2, height2));

		// 显示器分辨率
		GraphicsDevice graphDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		DisplayMode disMode = graphDevice.getDisplayMode();
		int width = disMode.getWidth();
		int height = disMode.getHeight();
		System.out.println(String.format("显示器分辨率：%s x %s", width, height));

		// 显示器分辨率缩放比例
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		AffineTransform tx = gc.getNormalizingTransform();
		double uiScaleX = tx.getScaleX();
		double uiScaleY = tx.getScaleY();
		System.out.println(String.format("显示器分辨率缩放比例，X：%s，Y：%s", uiScaleX, uiScaleY));
	}
}
