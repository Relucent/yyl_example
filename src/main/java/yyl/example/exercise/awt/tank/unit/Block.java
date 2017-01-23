package yyl.example.exercise.awt.tank.unit;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import yyl.example.exercise.awt.tank.Resource;

public class Block extends Unit {
	private int style;

	public Block(int x, int y, int style) {
		this.x = x;
		this.y = y;
		this.width = 1;
		this.height = 1;
		this.style = style;
	}

	public Image getImage() {
		switch (style) {
			case 1:
				return Resource.BLOCK_1;
			case 2:
				return Resource.BLOCK_2;
			case 3:
				return Resource.BLOCK_3;
			case 4:
				return Resource.BLOCK_4;
			default:
				return Resource.BLOCK_0;
		}
	}

	@Override
	public void paint(Graphics g, int px, ImageObserver observer) {
		g.drawImage(getImage(), x * px, y * px, width * px, width * px, observer);
	}

	protected void hit(Unit unit) {
		if (unit instanceof BulletDestroyd) {
			BulletDestroyd destroyd = ((BulletDestroyd) unit);
			if (destroyd.level > 0) {
				destroy = true;
			}
		}
	}

}