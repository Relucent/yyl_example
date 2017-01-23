package yyl.example.demo.nativeswing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

public class JWebBrowserTest {

	public static void main(String[] args) throws Exception {
		UIUtils.setPreferredLookAndFeel();
		NativeInterface.open();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					final JFrame frame = new JFrame("Browser");

					final JWebBrowser webBrowser = new JWebBrowser();
					webBrowser.setBarsVisible(true); //显示前进返回刷新按钮

					final Container container = frame.getContentPane();
					container.setLayout(new BorderLayout());
					container.add(webBrowser, BorderLayout.CENTER);

					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setSize(800, 600);
					frame.setLocationByPlatform(true);
					frame.setVisible(true);

					webBrowser.navigate("http://www.360.com");
				} catch (HeadlessException e) {
					e.printStackTrace();
				}
			}
		});
		NativeInterface.runEventPump();
	}

}