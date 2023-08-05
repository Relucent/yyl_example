package yyl.example.exercise.awt.other;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class SoundBeep {
    public static void main(String args[]) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("单击按钮事件");
            Container container = frame.getContentPane();
            container.setLayout(new FlowLayout(FlowLayout.CENTER));

            container.add(new JLabel("按一下按钮可听到响声！", JLabel.CENTER));
            JButton button = new JButton("按钮");
            container.add(button);

            // 创建事件监听器对象
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Toolkit.getDefaultToolkit().beep();
                }
            });

            frame.setLocationRelativeTo(null);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
