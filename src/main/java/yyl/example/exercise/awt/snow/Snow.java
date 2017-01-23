package yyl.example.exercise.awt.snow;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

@SuppressWarnings("serial")
public class Snow extends Applet implements Runnable {
	Thread thread;
	private Image offImg; // 缓冲图象
	private Graphics offG; // 缓冲
	//Image bgimg;

	private final int snow_num = 200; // 雪花数量
	private int[][] snows = new int[snow_num][2];
	// 存放雪花位置的数组，[index][0]为x,[index][1]为y

	public void init() {
		offImg = createImage(getSize().width, getSize().height); // 创建缓冲图象大小
		offG = offImg.getGraphics();
		try {
			//bgimg = getImage(new URL("http://www.delfan.com/images/snow.jpg"));
		} catch (Exception e) {
		}

		setSize(180, 135);
		for (int i = 0; i < snow_num; i++) // 先随机生成雪花的位置
		{
			snows[i][0] = (int) (Math.random() * 10000) % getSize().width;
			snows[i][1] = (int) (Math.random() * 10000) % getSize().height;
		}
	}

	public void paint(Graphics g) {
		offG.setColor(Color.BLACK);
		offG.fillRect(0, 0, getSize().width, getSize().height);
		//offG.drawImage(bgimg, 0, 0, this);
		offG.setColor(Color.white);
		for (int i = 0; i < snow_num; i++)
			// 画雪花
			offG.drawRect(snows[i][0], snows[i][1], 1, 1);

		if (offG != null)
			g.drawImage(offImg, 0, 0, this);
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void run() {
		Thread current = Thread.currentThread();
		while (thread == current) {
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				break;
			}

			for (int i = 0; i < snow_num; i++) // 处理下落过程
			{
				snows[i][0] = snows[i][0] + (int) (Math.random() * 1000) % 5 - 2;
				snows[i][1] = snows[i][1] + (int) (Math.random() * 1000) % 3 + 1;

				if (snows[i][1] > getSize().height)
					snows[i][1] = 0;
				if (snows[i][0] < 0 || snows[i][0] > getSize().width)
					snows[i][0] = (int) (Math.random() * 1000) % getSize().width;
			}
			repaint();
		}
	}

	public void start() {
		thread = new Thread(this); // 启动线程
		thread.start();
	}

	public void stop() {
		thread = null; // 停止线程序
	}
}
