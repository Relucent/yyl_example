package yyl.example.exercise.awt.igo;

import java.awt.Canvas;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class IGOMatrix {

	private Panel panel;

	private Canvas canvasBoard;

	private JFrame frame;

	private JButton button;

	public IGOMatrix() {
		frame = new JFrame();
		createControl();
		frame.setBounds(100, 100, 500, 375);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.validate();
	}

	protected void createControl() {
		frame.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gridData = new GridBagConstraints();

		canvasBoard = new IGOBoard(19);
		gridData.fill = GridBagConstraints.BOTH;
		gridData.weightx = 5;
		gridData.weighty = 1;
		frame.getContentPane().add(canvasBoard, gridData);

		gridData = new GridBagConstraints();
		panel = new Panel();
		gridData.weightx = 1;
		gridData.weighty = 1;
		frame.getContentPane().add(panel, gridData);

		button = new JButton();
		panel.add(button, gridData);

		final JPanel panel_1 = new JPanel();
		panel.add(panel_1, new GridBagConstraints());
	}


}
