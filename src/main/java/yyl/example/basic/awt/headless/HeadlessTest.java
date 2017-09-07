package yyl.example.basic.awt.headless;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * 1. 什么是 java.awt.headless <br>
 * Headless模式是系统的一种配置模式。在该模式下，系统缺少了显示设备、键盘或鼠标。<br>
 * 2. 何时使用和headless mode <br>
 * Headless模式虽然不是我们愿意见到的，但事实上我们却常常需要在该模式下工作，尤其是服务器端程序开发者。<br>
 * 因为服务器（如提供Web服务的主机）往往可能缺少前述设备，但又需要使用他们提供的功能，生成相应的数据，以提供给客户端（如浏览器所在的配有相关的显示设备、键盘和鼠标的主机）。<br>
 * 3. 如何使用和Headless mode？<br>
 * 一般是在程序开始激活headless模式，告诉程序，现在你要工作在Headless mode下，就不要指望硬件帮忙了，你得自力更生，依靠系统的计算能力模拟出这些特性来:<br>
 * System.setProperty("java.awt.headless", "true");<br>
 */
public class HeadlessTest {
	public static void main(String[] args) throws Exception {

		//设置Headless模式
		System.setProperty("java.awt.headless", "true");
		BufferedImage bi = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);

		//绘制图片
		Graphics g = bi.getGraphics();
		String s = "Headless Mode Test";
		g.drawString(new String(s.getBytes(), "UTF-8"), 50, 50);

		//输出文件
		ImageIO.write(bi, "jpeg", new File("hello_headless.jpg"));
	}
}
