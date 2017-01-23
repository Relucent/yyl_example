package yyl.example.exercise.awt.tank.unit;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Collection;

import yyl.example.exercise.awt.tank.GameMatrix;

public abstract class Unit {

	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected boolean destroy = false;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean intersects(int x, int y, int width, int height) {
		int i = width;
		int j = height;
		int k = this.width;
		int l = this.height;
		if (k <= 0 || l <= 0 || i <= 0 || j <= 0) {
			return false;
		} else {
			int i1 = x;
			int j1 = y;
			int k1 = this.x;
			int l1 = this.y;
			k += k1;
			l += l1;
			i += i1;
			j += j1;
			return (k < k1 || k > i1) && (l < l1 || l > j1) && (i < i1 || i > k1) && (j < j1 || j > l1);
		}
	}

	public boolean checkBorderHit(int x, int y) {
		Rectangle bounds = GameMatrix.getInstance().getBounds();
		return !bounds.contains(x, y, this.width, this.height);
	}

	public Collection<Point> getGrids() {
		return GameMatrix.getInstance().getGrids(x, y, width, height);
	}

	protected Collection<Point> getGrids(int _x, int _y) {
		return GameMatrix.getInstance().getGrids(_x, _y, width, height);
	}

	public void paint(Graphics g, int px, ImageObserver observer) {
		g.drawImage(getImage(), (x * px), (y * px), (width * px), (height * px), observer);
	}

	public void go() {
		if (destroy == true) {
			destroy();
		}
	}

	protected abstract Image getImage();

	protected void destroy() {
		GameMatrix.getInstance().removeUnit(this);
	}

	protected void afterHit() {

	}

	protected void hit(Unit unit) {

	}

	protected boolean checkHit(Unit unit) {
		return !(unit == null || unit.destroy == true || unit == this);
	}

}
