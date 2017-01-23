package yyl.example.exercise.awt.tank.unit;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import yyl.example.exercise.awt.tank.Resource;

public class Lake extends Unit {

	public Lake(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 4;
		this.height = 4;
	}

	public Image getImage() {
		return Resource.LAKE;
	}

	@Override
	public void paint(Graphics g, int px, ImageObserver observer) {
		g.drawImage(getImage(), x * px, y * px, width * px, width * px, observer);
	}

	protected void hit(Unit unit) {

	}
}