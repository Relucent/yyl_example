package yyl.example.exercise.awt.tank.unit;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import yyl.example.exercise.awt.tank.Resource;

public class TankDestroyd extends Unit {
	private int destroyCount = 5;

	public TankDestroyd(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	protected Image getImage() {
		return Resource.TANK_DESTROY;
	}

	@Override
	public void paint(Graphics g, int px, ImageObserver observer) {
		g.drawImage(getImage(), x * px, y * px, width * px, width * px, observer);
	}

	@Override
	public void go() {
		if ((destroyCount--) < 0) {
			destroy = true;
			destroy();
		}
	}
}
