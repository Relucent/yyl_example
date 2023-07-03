package yyl.example.other.cert;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

public class KeyToolUI {

    /**
     * 构造函数
     */
    public KeyToolUI() {

        initLookAndFeel();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Keystore");
        frame.setResizable(false);
        frame.setSize(500, 400);

        initContainer(frame);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2);
        frame.setVisible(true);
    }

    /**
     * 初始化外观
     */
    private void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化界面
     * @param frame 顶级窗口
     */
    private void initContainer(final JFrame frame) {
        Container container = frame.getContentPane();
        JTabbedPane tabbedPane = new JTabbedPane();
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