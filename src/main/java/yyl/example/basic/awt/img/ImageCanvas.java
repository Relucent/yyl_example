package yyl.example.basic.awt.img;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

/**
 * 图片画布
 */
@SuppressWarnings("serial")
public class ImageCanvas extends Canvas {

	// ==============================Fields===========================================
	private Image image;
	private int angel = 0;
	private AffineTransform transform;
	private Image canvasBuffer;

	// ==============================Constructors=====================================
	public ImageCanvas(Image image) {
		this.image = image;
		this.transform = new AffineTransform();
	}

	// ==============================Methods==========================================
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		angel = (angel + 1) % 360;

		int width = image.getWidth(null);
		int height = image.getHeight(null);

		if (g instanceof Graphics2D) {
			transform.setToTranslation(0, 0);
			transform.rotate(Math.toRadians(angel), width / 2, height / 2);
			((Graphics2D) g).drawImage(image, transform, this);
		} else {
			g.drawImage(image, 0, 0, this);
		}
	}

	@Override
	public void update(Graphics g) {
		Graphics bufferGraphics = getBufferGraphics();
		if (bufferGraphics == null) {
			paint(g);
		} else {
			paint(bufferGraphics);
			bufferGraphics.dispose();
			g.drawImage(canvasBuffer, 0, 0, this);
		}
	}

	private Graphics getBufferGraphics() {
		if (canvasBuffer == null) {
			canvasBuffer = createImage(getWidth(), getHeight());
		}
		return canvasBuffer.getGraphics();
	}

	public static void main(String[] args) throws Exception {

		JFrame frame = new JFrame();
		ImageCanvas canvas = new ImageCanvas(Helper.getImage());
		frame.getContentPane().add(canvas);
		frame.setBounds(100, 100, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("image");
		frame.setResizable(false);
		frame.setVisible(true);

		new Thread(() -> {
			while (true) {
				try {
					TimeUnit.MILLISECONDS.sleep(200);
					canvas.repaint();
				} catch (Exception e) {
					return;
				}
			}
		}).start();
	}
}
