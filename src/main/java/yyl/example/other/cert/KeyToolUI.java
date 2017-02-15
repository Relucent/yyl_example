package yyl.example.other.cert;

import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class KeyToolUI {

	JFrame frame = new JFrame();
	JTabbedPane tabbedPane = null;

	/**
	 * 构造函数
	 */
	public KeyToolUI() {
		initContainer();
		frame.setBounds(100, 100, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Keystore");
		frame.setResizable(false);
		frame.setVisible(true);
	}

	/**
	 * 初始化界面
	 */
	private void initContainer() {
		Container container = frame.getContentPane();
		tabbedPane = new JTabbedPane();
		container.add(tabbedPane);
		tabbedPane.add("产生密钥", new CertContent1().getContainer());
		tabbedPane.add("导出密钥", new CertContent2().getContainer());
		tabbedPane.add("检查密钥", new CertContent3().getContainer());
		tabbedPane.add("导入密钥", new CertContent4().getContainer());
		tabbedPane.add("删除密钥", new CertContent5().getContainer());
		tabbedPane.add("生成私钥", new CertContent6().getContainer());
		tabbedPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (KeyEvent.VK_F1 == e.getKeyCode()) {
					frame.setTitle("Copyright (c) 2009 YaoYiLang  redrainyi@gmail.com");
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (KeyEvent.VK_F1 == e.getKeyCode()) {
					frame.setTitle("Keystore");
				}
			}
		});
	}
}