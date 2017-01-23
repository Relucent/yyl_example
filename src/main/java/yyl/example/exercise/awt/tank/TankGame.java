package yyl.example.exercise.awt.tank;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class TankGame {

	public TankGame() {
		JFrame frame = new JFrame();
		GameCanvas game = new GameCanvas();
		frame.setLayout(null);
		game.setBounds(0, 0, Constant.Canvas_WIDTH, Constant.Canvas_HEIGHT);
		frame.add(game);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.addKeyListener(game);
		frame.setTitle("Tank");
		initBounds(frame);
		frame.setVisible(true);
		game.init();
	}

	private void initBounds(JFrame frame) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int fw = Constant.Canvas_WIDTH + 8;
		int fh = Constant.Canvas_HEIGHT + 34;
		int fx = (screenSize.width - fw) / 2;
		int fy = (screenSize.height - fh) / 2;
		frame.setBounds(fx, fy, fw, fh);
	}

	public static void main(String args[]) {
		new TankGame();
	}
}