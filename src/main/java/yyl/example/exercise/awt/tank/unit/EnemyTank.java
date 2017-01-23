package yyl.example.exercise.awt.tank.unit;
import java.awt.Image;

import yyl.example.exercise.awt.tank.Constant;
import yyl.example.exercise.awt.tank.Resource;

public class EnemyTank extends Tank {

	protected int type = 1;

	public EnemyTank(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.width = 4;
		this.height = 4;
		this.team = 1;
		this.face = Constant.FACE_DOWN;
		this.clip = 1;
		this.fireSpace = 50;
		this.moveSpace = 20;
		this.init(this.type = type);
	}

	private void init(int type) {
		switch (type) {
			case 1:

			break;
			case 2:

			break;
			case 3:

			break;
			case 4:
				this.clip = 2;
			break;
			default:
				this.moveSpace = 10;
		}
	}

	@Override
	protected Image getImage() {
		if (destroy) {
			return Resource.DESTROY;
		}
		if (type == 1) {
			if (face == Constant.FACE_UP) {
				return Resource.E_TANK1_UP;
			}
			if (face == Constant.FACE_DOWN) {
				return Resource.E_TANK1_DOWN;
			}
			if (face == Constant.FACE_LEFT) {
				return Resource.E_TANK1_LEFT;
			}
			if (face == Constant.FACE_RIGHT) {
				return Resource.E_TANK1_RIGHT;
			}
		} else if (type == 2) {
			if (face == Constant.FACE_UP) {
				return Resource.E_TANK2_UP;
			}
			if (face == Constant.FACE_DOWN) {
				return Resource.E_TANK2_DOWN;
			}
			if (face == Constant.FACE_LEFT) {
				return Resource.E_TANK2_LEFT;
			}
			if (face == Constant.FACE_RIGHT) {
				return Resource.E_TANK2_RIGHT;
			}
		} else if (type == 3) {
			if (face == Constant.FACE_UP) {
				return Resource.E_TANK3_UP;
			}
			if (face == Constant.FACE_DOWN) {
				return Resource.E_TANK3_DOWN;
			}
			if (face == Constant.FACE_LEFT) {
				return Resource.E_TANK3_LEFT;
			}
			if (face == Constant.FACE_RIGHT) {
				return Resource.E_TANK3_RIGHT;
			}
		} else {
			if (face == Constant.FACE_UP) {
				return Resource.E_TANK0_UP;
			}
			if (face == Constant.FACE_DOWN) {
				return Resource.E_TANK0_DOWN;
			}
			if (face == Constant.FACE_LEFT) {
				return Resource.E_TANK0_LEFT;
			}
			if (face == Constant.FACE_RIGHT) {
				return Resource.E_TANK0_RIGHT;
			}
		}
		return Resource.DESTROY;
	}

	public void go() {
		if (!this.destroy) {
			move();
			fire(1);
		}
	}

	protected void hit(Unit unit) {
		if (unit == null || unit instanceof Steel || unit instanceof Block || unit instanceof Tank
				|| unit instanceof Lake) {
			switch ((int) (Math.random() * 5)) {
				case 0:
					face = Constant.FACE_UP;
				break;
				case 1:
					face = Constant.FACE_DOWN;
				break;
				case 2:
					face = Constant.FACE_LEFT;
				break;
				case 3:
					face = Constant.FACE_RIGHT;
				break;
			}
		}

		super.hit(unit);
	}
}
