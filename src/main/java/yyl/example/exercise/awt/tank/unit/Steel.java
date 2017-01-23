package yyl.example.exercise.awt.tank.unit;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.util.Collection;
import java.util.HashSet;

import yyl.example.exercise.awt.tank.Resource;

public class Steel extends Unit {
	public Steel(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 2;
		this.height = 2;
	}

	public Image getImage() {
		return Resource.STEEL;
	}

	@Override
	public void paint(Graphics g, int px, ImageObserver observer) {
		g.drawImage(getImage(), x * px, y * px, width * px, width * px, observer);
	}

	public Collection<Point> getGrids(int _x, int _y) {
		Collection<Point> grids = new HashSet<Point>();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				grids.add(new Point(_x + i, _y + j));
			}
		}
		return grids;
	}

	protected void hit(Unit unit) {
		if (unit instanceof BulletDestroyd) {
			BulletDestroyd destroyd = ((BulletDestroyd) unit);
			if (destroyd.level > 1) {
				destroy = true;
			}
		}
	}
}
