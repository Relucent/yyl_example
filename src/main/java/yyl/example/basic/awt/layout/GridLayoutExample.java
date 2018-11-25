package yyl.example.basic.awt.layout;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * 网格布局<br>
 * 以矩形网格形式对容器的组件进行布置。容器被分成大小相等的矩形，一个矩形中放置一个组件。<br>
 */
public class GridLayoutExample {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("FlowLayout");
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container container = frame.getContentPane();
        container.setLayout(new GridLayout(3, 2));// 三行两列

        container.add(new JButton("Button1"));
        container.add(new JButton("Button2"));
        container.add(new JButton("Button3"));
        container.add(new JButton("Button4"));
        container.add(new JButton("Button5"));
        container.add(new JButton("Button6"));

        frame.setVisible(true);
    }
}
