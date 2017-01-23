package yyl.example.exercise.awt.other;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;

public class SelectionBox {

	private Canvas canvasBoard;

	private JFrame frame;

	int[] iRect = new int[] { 1, 1, 1, 1 };

	@SuppressWarnings("serial")
	public SelectionBox() {
		frame = new JFrame();
		canvasBoard = new Canvas() {

			public void paint(Graphics g) {
				if (iRect[0] != -1)
					g.drawRect(iRect[0], iRect[1], iRect[2] - iRect[0], iRect[3] - iRect[1]);
			}
		};

		canvasBoard.addMouseMotionListener(new MouseMotionAdapter() {

			public void mouseDragged(MouseEvent event) {
				iRect[2] = event.getX();
				iRect[3] = event.getY();
				canvasBoard.repaint();
			}
		});
		canvasBoard.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent event) {
				iRect[0] = event.getX();
				iRect[1] = event.getY();
			}

			public void mouseReleased(MouseEvent event) {
				iRect[0] = -1;
			}
		});

		frame.getContentPane().add(canvasBoard);
		frame.setBounds(100, 100, 500, 375);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.validate();
	}

	public static void main(String args[]) {
		try {
			new SelectionBox();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
