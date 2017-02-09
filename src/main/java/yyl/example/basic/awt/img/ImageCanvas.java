package yyl.example.basic.awt.img;

import java.awt.Canvas;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ImageCanvas extends Canvas {

	private Image image;

	public ImageCanvas(Image image) {
		this.image = image;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(image, 0, 10, null);
	}

	public static void main(String[] args) throws Exception {
		JFrame frame = new JFrame();
		Container container = frame.getContentPane();
		container.add(new ImageCanvas(Helper.getImage()));
		frame.setBounds(100, 100, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("image");
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
