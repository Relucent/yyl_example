package yyl.example.exercise.awt.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class GameCanvas extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;

	GameMatrix matrix = GameMatrix.getInstance();

	private int canvasWidth = 52;
	private int canvasHeight = 52;
	private int unitPx = 1;

	private Image bufferImage = null;//��ͼ��˫������

	public GameCanvas() {
		//MediaTracker tracker = new MediaTracker(this);
		//{
		//	int imageId = 0;
		//	tracker.addImage(Resource.TANK_UP, imageId++);
		//	tracker.addImage(Resource.TANK_DOWN, imageId++);
		//	tracker.addImage(Resource.TANK_LEFT, imageId++);
		//	tracker.addImage(Resource.TANK_RIGHT, imageId++);
		//}
	}

	public void init() {
		setBackground(Color.BLACK);
		canvasWidth = this.getWidth();
		canvasHeight = this.getHeight();
		unitPx = canvasWidth / 52;
		bufferImage = this.createImage(canvasWidth, canvasHeight);
		while (true) {
			go();
			try {
				Thread.sleep(10);
			} catch (Exception e) {
			}
		}
	}

	public void go() {
		matrix.go();
		matrix.paint(bufferImage.getGraphics(), canvasWidth, canvasHeight, unitPx);
		repaint();
	}

	public void paint(Graphics graphics) {
		super.paint(graphics);
		graphics.drawImage(bufferImage, 0, 0, this);
	}

	//���
	public void keyPressed(KeyEvent keyevent) {
		switch (keyevent.getKeyCode()) {
			case KeyEvent.VK_W:
				matrix.command1P(Constant.HANDLE_MOVE_UP);
			break;
			case KeyEvent.VK_S:
				matrix.command1P(Constant.HANDLE_MOVE_DOWN);
			break;
			case KeyEvent.VK_A:
				matrix.command1P(Constant.HANDLE_MOVE_LEFT);
			break;
			case KeyEvent.VK_D:
				matrix.command1P(Constant.HANDLE_MOVE_RIGHT);
			break;
			case KeyEvent.VK_U:
				matrix.command1P(Constant.HANDLE_FIRE_A);
			break;
			case KeyEvent.VK_I:
				matrix.command1P(Constant.HANDLE_FIRE_B);
			break;

			case KeyEvent.VK_UP:
				matrix.command2P(Constant.HANDLE_MOVE_UP);
			break;
			case KeyEvent.VK_DOWN:
				matrix.command2P(Constant.HANDLE_MOVE_DOWN);
			break;
			case KeyEvent.VK_LEFT:
				matrix.command2P(Constant.HANDLE_MOVE_LEFT);
			break;
			case KeyEvent.VK_RIGHT:
				matrix.command2P(Constant.HANDLE_MOVE_RIGHT);
			break;
			case KeyEvent.VK_NUMPAD5:
				matrix.command2P(Constant.HANDLE_FIRE_A);
			break;
			case KeyEvent.VK_NUMPAD6:
				matrix.command2P(Constant.HANDLE_FIRE_B);
			break;
		}

	}

	public void keyReleased(KeyEvent keyevent) {
		switch (keyevent.getKeyCode()) {
			case KeyEvent.VK_W:
				matrix.command1P(Constant.HANDLE_UN_MOVE_UP);
			break;
			case KeyEvent.VK_S:
				matrix.command1P(Constant.HANDLE_UN_MOVE_DOWN);
			break;
			case KeyEvent.VK_A:
				matrix.command1P(Constant.HANDLE_UN_MOVE_LEFT);
			break;
			case KeyEvent.VK_D:
				matrix.command1P(Constant.HANDLE_UN_MOVE_RIGHT);
			break;
			case KeyEvent.VK_U:
				matrix.command1P(Constant.HANDLE_UN_FIRE_A);
			break;
			case KeyEvent.VK_I:
				matrix.command1P(Constant.HANDLE_UN_FIRE_B);
			break;

			case KeyEvent.VK_UP:
				matrix.command2P(Constant.HANDLE_UN_MOVE_UP);
			break;
			case KeyEvent.VK_DOWN:
				matrix.command2P(Constant.HANDLE_UN_MOVE_DOWN);
			break;
			case KeyEvent.VK_LEFT:
				matrix.command2P(Constant.HANDLE_UN_MOVE_LEFT);
			break;
			case KeyEvent.VK_RIGHT:
				matrix.command2P(Constant.HANDLE_UN_MOVE_RIGHT);
			break;
			case KeyEvent.VK_NUMPAD5:
				matrix.command2P(Constant.HANDLE_UN_FIRE_A);
			break;
			case KeyEvent.VK_NUMPAD6:
				matrix.command2P(Constant.HANDLE_UN_FIRE_B);
			break;
		}
	}

	public void keyTyped(KeyEvent keyevent) {

	}
}
