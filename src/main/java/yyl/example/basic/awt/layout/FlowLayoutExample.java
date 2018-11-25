package yyl.example.basic.awt.layout;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * 流式布局<br>
 * 用于安排有向流中的组件，这非常类似于段落中的文本行。<br>
 * 流的方向取决于容器的 componentOrientation 属性，它可能是以下两个值中的一个： <br>
 * ComponentOrientation.LEFT_TO_RIGHT <br>
 * ComponentOrientation.RIGHT_TO_LEFT <br>
 * 流布局一般用来安排面板中的按钮。它使得按钮呈水平放置，直到一行上再也没有适合的按钮。<br>
 */
public class FlowLayoutExample {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("FlowLayout");
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container container = frame.getContentPane();
        container.setLayout(new FlowLayout(FlowLayout.LEFT));

        container.add(new JButton("Button1"));
        container.add(new JButton("Button2"));
        container.add(new JButton("Button3"));
        container.add(new JButton("Button4"));
        container.add(new JButton("Button5"));
        container.add(new JButton("Button6"));
        container.add(new JButton("Button7"));
        container.add(new JButton("Button8"));
        container.add(new JButton("Button9"));

        frame.setVisible(true);
    }
}
