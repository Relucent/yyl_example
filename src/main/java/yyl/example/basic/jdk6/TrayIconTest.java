package yyl.example.basic.jdk6;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import yyl.example.basic.util.Streams;

/**
 * 在JDK1.6中，AWT新增加了两个类：Desktop和SystemTray。<br>
 * Desktop可以用来打开系统默认浏览器浏览指定的URL，打开系统默认邮件客户端给指定的邮箱发邮件，用默认应用程序打开或编辑文件(比如，用记事本打开以txt为后缀名的文件)，用系统默认的打印机打印文档。<br>
 * SystemTray可以用来在系统托盘区创建一个托盘程序。<br>
 */
public class TrayIconTest {
	public static void main(String[] args) {

		// 判断是否支持系统托盘
		if (!SystemTray.isSupported()) {
			JOptionPane.showMessageDialog(null, "SystemTray is not support !");
		}

		// 获得Image对象
		byte[] imagedata = Streams.toByteArray(TrayIconTest.class.getResourceAsStream("ICO128.png"), true);
		Image image = Toolkit.getDefaultToolkit().createImage(imagedata);

		// 创建托盘图标
		TrayIcon trayIcon = new TrayIcon(image);

		//自适应图标大小
		trayIcon.setImageAutoSize(true);

		// 添加工具提示文本
		trayIcon.setToolTip("Hello");

		// 创建弹出菜单
		PopupMenu popupMenu = new PopupMenu();
		popupMenu.add(new MenuItem("Item1"));
		popupMenu.add(new MenuItem("Item2"));
		popupMenu.add(new MenuItem("Item3"));
		popupMenu.addSeparator();
		popupMenu.add(new MenuItem("Item4"));
		popupMenu.add(new MenuItem("Item5"));
		popupMenu.addSeparator();

		MenuItem closeMenuItem = new MenuItem("Close");

		popupMenu.add(closeMenuItem);

		// 为托盘图标加弹出菜弹
		trayIcon.setPopupMenu(popupMenu);

		// 添加事件
		trayIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// 判断是否双击了鼠标
				if (e.getClickCount() == 2) {
					JOptionPane.showMessageDialog(null, "double-click");
				}
			}
		});

		closeMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SystemTray.getSystemTray().remove(trayIcon);
				System.exit(0);
			}
		});

		// 获得系统托盘对象
		SystemTray systemTray = SystemTray.getSystemTray();
		try {
			// 为系统托盘加托盘图标
			systemTray.add(trayIcon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
