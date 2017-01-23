package yyl.example.exercise.awt.other;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SoundBeep {
	public static void main(String args[]) {
		JFrame jFrame = new JFrame("单击按钮事件");
		jFrame.setSize(new Dimension(200, 180));
		JPanel jPanel = new JPanel();
		jFrame.add(jPanel); // 将面板P添加到窗口f中
		jPanel.add(new Label("按一下按钮可听到响声！", Label.CENTER));
		Button button = new Button("按钮");
		jPanel.add(button);
		// 创建事件监听器对象
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Toolkit.getDefaultToolkit().beep();
			}
		}); // 注册事件监听器对象
		jFrame.setVisible(true);
	}
}
