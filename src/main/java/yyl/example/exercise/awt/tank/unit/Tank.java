package yyl.example.exercise.awt.tank.unit;

import yyl.example.exercise.awt.tank.GameMatrix;

public abstract class Tank extends Mover {

	protected int clip = 5;
	protected int fire = 0;
	protected int fireTimer = 0;
	protected int fireSpace = 20;
	protected int team = 0;

	protected void fire(int level) {
		if (fireSpace < (fireTimer++) || fire == 0) {
			if ((fire) < clip) {
				GameMatrix.getInstance().addUnit(new Bullet(x + 1, y + 1, face, this, level));
				fire++;
			}
			fireTimer = 0;
		}
	}

	protected void move(int face) {
		if (this.face != face) {
			this.face = face;
			return;
		}
		move();
	}

	protected void recoverFire() {
		fire--;
	}

	protected boolean checkHit(Unit unit) {
		if (unit instanceof Bullet) {
			return false;
		}
		if (unit instanceof BulletDestroyd) {
			return false;
		}
		if (unit instanceof Forest) {
			return false;
		}
		if (unit instanceof Ice) {
			moveRepeat = true;
			return false;
		}
		return super.checkHit(unit);
	}

	protected void hit(Unit unit) {
		if (unit instanceof BulletDestroyd) {
			if (((BulletDestroyd) unit).team != team) {
				this.destroy();
				GameMatrix.getInstance().addUnit(new TankDestroyd(x, y, width, height));
			}
		}
	}

}
