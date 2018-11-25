package yyl.example.basic.awt.layout;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * 空布局<br>
 * 使用绝对定义的布局方式。<br>
 */
public class NullLayoutExample {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("FlowLayout");
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container container = frame.getContentPane();
        container.setLayout(null);

        
        JButton button1 = new JButton("Button1");
        button1.setBounds(10, 10, 120, 25);
        container.add(button1);

        JButton button2 = new JButton("Button1");
        button2.setBounds(10, 60, 120, 25);
        container.add(button2);

        JButton button3 = new JButton("Button1");
        button3.setBounds(150, 110, 120, 25);
        container.add(button3);

        frame.setVisible(true);
    }
}
