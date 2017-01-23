package yyl.example.exercise.awt.tank.unit;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Collection;

import yyl.example.exercise.awt.tank.GameMatrix;
import yyl.example.exercise.awt.tank.Resource;

public class BulletDestroyd extends Unit {
	private int destroyCount = 5;
	protected int level = 0;
	protected int team;

	public BulletDestroyd(int x, int y, int width, int height, int level, int team) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.level = level;
		this.team = team;
	}

	@Override
	protected Image getImage() {
		return Resource.DESTROY;
	}

	@Override
	public void paint(Graphics g, int px, ImageObserver observer) {
		if (width > height) {
			g.drawImage(getImage(), (x * px), ((y - 1) * px), (width * px), (width * px), observer);
		} else {
			g.drawImage(getImage(), ((x - 1) * px), (y * px), (height * px), (height * px), observer);
		}
	}

	/**
	 * ����غ��ϰ�
	 */
	protected Collection<Unit> probe() {
		GameMatrix matrix = GameMatrix.getInstance();
		Collection<Unit> units = matrix.inBlock(x, y, width, height);
		units.remove(this);
		return units;
	}

	public void go() {
		Collection<Unit> probes = probe();
		for (Unit unit : probes) {
			unit.hit(this);
		}

		if ((destroyCount--) < 0) {
			destroy = true;
			destroy();
		}
	}

}
