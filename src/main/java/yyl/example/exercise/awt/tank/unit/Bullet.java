package yyl.example.exercise.awt.tank.unit;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import yyl.example.exercise.awt.tank.Constant;
import yyl.example.exercise.awt.tank.GameMatrix;
import yyl.example.exercise.awt.tank.Resource;

public class Bullet extends Mover {

	protected Tank master;
	protected int level;

	public Bullet(int x, int y, int face, Tank master, int level) {
		this.x = x;
		this.y = y;
		this.width = 2;
		this.height = 2;
		this.face = face;
		this.master = master;
		this.level = level;
		this.moveSpace = 3;
	}

	@Override
	protected Image getImage() {
		if (destroy) {
			return Resource.DESTROY;
		}
		if (face == Constant.FACE_UP) {
			return Resource.BULLET_UP;
		}
		if (face == Constant.FACE_DOWN) {
			return Resource.BULLET_DOWN;
		}
		if (face == Constant.FACE_LEFT) {
			return Resource.BULLET_LEFT;
		}
		if (face == Constant.FACE_RIGHT) {
			return Resource.BULLET_RIGHT;
		}
		return Resource.TANK_UP;
	}

	public void go() {
		if (destroy) {
			master.recoverFire();
			if (face == Constant.FACE_UP || face == Constant.FACE_DOWN) {
				GameMatrix.getInstance().addUnit(new BulletDestroyd(x - 1, y, 4, 2, level, master.team));
			} else {
				GameMatrix.getInstance().addUnit(new BulletDestroyd(x, y - 1, 2, 4, level, master.team));
			}
			this.destroy();
		} else {
			move();
		}
	}

	public void paint(Graphics g, int px, ImageObserver observer) {
		g.drawImage(getImage(), (int) ((x + 0.5) * px), (int) ((y + 0.5) * px), (px), (px), observer);
	}

	protected void afterHit(Unit unit) {
		destroy = true;
	}

	@Override
	protected void hit(Unit unit) {

		if (unit instanceof BulletDestroyd) {
			return;
		}
		if (unit == this || unit == master) {
			return;
		}
		if ((unit instanceof Bullet) && ((Bullet) unit).master == master) {
			return;
		}
		if (unit instanceof Forest) {
			return;
		}
		if (unit instanceof Lake) {
			return;
		}
		if (unit instanceof Ice) {
			return;
		}
		if (unit instanceof Tank) {
			Tank tank = (Tank) unit;
			if (tank.team == this.master.team) {
				return;
			}
		}
		afterHit(unit);
	}

	protected boolean checkHit(Unit unit) {
		return false;
	}

}
