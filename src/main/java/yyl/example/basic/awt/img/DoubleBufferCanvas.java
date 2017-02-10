package yyl.example.basic.awt.img;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;

/**
 * 实现双缓存的Canvas<br>
 * 在GDI的绘图系统中，每调用一次区域绘图操作，如FillRect、BitBlt等，图形显示系统就会在屏幕中对指定的区域进行一次刷新操作。<br>
 * 如果频繁的进行区域绘制操作的操作的话，我们就会发现，屏幕会出现闪屏。<br>
 * 双缓存技术就是在内存区先画一个位图，画好后直接将这个图一个点一个点覆盖到屏幕上，这个过程非常快，所以不会出现闪屏。<br>
 */
@SuppressWarnings("serial")
public class DoubleBufferCanvas extends Canvas {

	// ==============================Fields===========================================
	/** 缓存图形图像 */
	private Image canvasBufferImage;

	// ==============================Constructors=====================================

	/**
	 * 构造一个新的 Canvas
	 */
	public DoubleBufferCanvas() {
		super();
	}

	/**
	 * 根据给定 GraphicsConfiguration 对象构造一个新的 Canvas
	 * @param config 图形目标描述
	 */
	public DoubleBufferCanvas(GraphicsConfiguration config) {
		super(config);
	}
	// ==============================Methods==========================================

	/**
	 * 更新
	 * @param g 指定的 Graphics 上下文
	 */
	@Override
	public void update(Graphics g) {
		Graphics bufferGraphics = getBufferGraphics();
		if (bufferGraphics == null) {
			paint(g);
		} else {
			paint(bufferGraphics);
			bufferGraphics.dispose();
			g.drawImage(canvasBufferImage, 0, 0, this);
		}
	}

	/**
	 * 获得缓存 Graphics
	 * @return 缓存Graphics
	 */
	protected Graphics getBufferGraphics() {
		if (canvasBufferImage == null) {
			canvasBufferImage = createImage(getWidth(), getHeight());
		}
		return canvasBufferImage.getGraphics();
	}

	protected synchronized void initializeBuffer() {
		canvasBufferImage = createImage(getWidth(), getHeight());
	}
}
