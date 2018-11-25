package yyl.example.basic.awt.layout;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * 边框布局<br>
 * 对容器组件进行安排，并调整其大小，使其符合下列五个区域：北、南、东、西、中。<br>
 * 每个区域最多只能包含一个组件，并通过相应的常量进行标识：NORTH、SOUTH、EAST、WEST、CENTER。<br>
 */
public class BorderLayoutExample {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setTitle("FlowLayout");
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout());

        container.add(new JButton("NORTH"), BorderLayout.NORTH);// (北)[↑]
        container.add(new JButton("EAST"), BorderLayout.EAST);// (东)[→]
        container.add(new JButton("SOUTH"), BorderLayout.SOUTH);// (南)[↓]
        container.add(new JButton("WEST"), BorderLayout.WEST);// (西)[←]
        container.add(new JButton("CENTER"), BorderLayout.CENTER);// (中)[□]

        frame.setVisible(true);
    }
}
