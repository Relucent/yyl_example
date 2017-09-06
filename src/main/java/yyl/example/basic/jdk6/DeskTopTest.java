package yyl.example.basic.jdk6;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 在JDK1.6中，AWT新增加了两个类：Desktop和SystemTray。<br>
 * Desktop可以用来打开系统默认浏览器浏览指定的URL，打开系统默认邮件客户端给指定的邮箱发邮件，用默认应用程序打开或编辑文件(比如，用记事本打开以txt为后缀名的文件)，用系统默认的打印机打印文档。<br>
 * SystemTray可以用来在系统托盘区创建一个托盘程序。<br>
 */
public class DeskTopTest {

	public static void main(String[] args) throws IOException, URISyntaxException {

		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();

			//使用默认的浏览器打开网页  
			desktop.browse(new URI("https://www.baidu.com/"));

			File tempTxt = new File("C:/_temp" + System.currentTimeMillis() + ".txt");
			tempTxt.createNewFile();

			//编辑文件(如果系统没有没有默认的应用程序能打开此文件会抛出异常)
			//文本文件默认会用记事本打开

			desktop.edit(tempTxt);

			//打开文件(如果是可执行文件会执行)
			//文本文件默认会用记事本打开
			desktop.open(tempTxt);
			//打印指定的文件  
			desktop.print(tempTxt);

		}
	}
}
