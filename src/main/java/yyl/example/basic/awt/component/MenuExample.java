package yyl.example.basic.awt.component;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

/**
 * 菜单栏和菜单项
 */
public class MenuExample {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Menu Example");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setPreferredSize(new Dimension(800, 600));

                // 创建菜单条
                JMenuBar menuBar = new JMenuBar();

                // 创建菜单
                JMenu fileMenu = new JMenu("File");

                // 创建菜单项
                JMenuItem newItem = new JMenuItem("New");
                JMenuItem openItem = new JMenuItem("Open");
                JMenuItem saveItem = new JMenuItem("Save");
                JMenuItem exitItem = new JMenuItem("Exit");

                // 添加菜单项到菜单
                fileMenu.add(newItem);
                fileMenu.add(openItem);
                fileMenu.add(saveItem);
                fileMenu.addSeparator();// 分隔符
                fileMenu.add(exitItem);

                // 添加菜单到菜单条
                menuBar.add(fileMenu);

                // 设置主窗体的菜单条
                frame.setJMenuBar(menuBar);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
