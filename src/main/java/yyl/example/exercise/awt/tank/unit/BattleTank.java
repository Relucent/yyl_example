package yyl.example.exercise.awt.tank.unit;

import java.awt.Image;

import yyl.example.exercise.awt.tank.Constant;
import yyl.example.exercise.awt.tank.Resource;

public class BattleTank extends Tank {

	public BattleTank(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 4;
		this.height = 4;
		this.team = 0;
	}

	private int handle = 0x0;

	public boolean isDestroy() {
		return destroy;
	}

	public void command(int handle) {
		switch (handle) {
			case Constant.HANDLE_MOVE_STEP:
			case Constant.HANDLE_MOVE_UP:
			case Constant.HANDLE_MOVE_DOWN:
			case Constant.HANDLE_MOVE_LEFT:
			case Constant.HANDLE_MOVE_RIGHT:
			case Constant.HANDLE_FIRE_A:
			case Constant.HANDLE_UN_FIRE_A:
			case Constant.HANDLE_FIRE_B:
			case Constant.HANDLE_UN_FIRE_B:
				this.handle = (this.handle) | (handle);
			break;
			case Constant.HANDLE_UN_MOVE_UP:
			case Constant.HANDLE_UN_MOVE_DOWN:
			case Constant.HANDLE_UN_MOVE_LEFT:
			case Constant.HANDLE_UN_MOVE_RIGHT:
				this.handle = (0xFFFFFFF0 & this.handle) | (this.handle & handle);
			break;
			default://
		}
	}

	public void go() {
		if ((handle & Constant.HANDLE_MOVE_UP) == Constant.HANDLE_MOVE_UP) {
			move(Constant.FACE_UP);
		} else if ((handle & Constant.HANDLE_MOVE_DOWN) == Constant.HANDLE_MOVE_DOWN) {
			move(Constant.FACE_DOWN);
		} else if ((handle & Constant.HANDLE_MOVE_LEFT) == Constant.HANDLE_MOVE_LEFT) {
			move(Constant.FACE_LEFT);
		} else if ((handle & Constant.HANDLE_MOVE_RIGHT) == Constant.HANDLE_MOVE_RIGHT) {
			move(Constant.FACE_RIGHT);
		}
		if ((handle & Constant.HANDLE_FIRE_B) == Constant.HANDLE_FIRE_B) {
			if ((handle & Constant.HANDLE_UN_FIRE_B) == Constant.HANDLE_UN_FIRE_B) {
				this.handle = ((0xFFFFFF0F | Constant.HANDLE_FIRE_A | Constant.HANDLE_UN_FIRE_A) & this.handle);
			}
			fire(2);
		} else if ((handle & Constant.HANDLE_FIRE_A) == Constant.HANDLE_FIRE_A) {
			if ((handle & Constant.HANDLE_UN_FIRE_A) == Constant.HANDLE_UN_FIRE_A) {
				this.handle = ((0xFFFFFF0F | Constant.HANDLE_FIRE_B | Constant.HANDLE_UN_FIRE_B) & this.handle);
			}
			fire(1);
		}
	}

	protected Image getImage() {
		if (destroy) {
			return Resource.DESTROY;
		}
		if (face == Constant.FACE_UP) {
			return Resource.TANK_UP;
		}
		if (face == Constant.FACE_DOWN) {
			return Resource.TANK_DOWN;
		}
		if (face == Constant.FACE_LEFT) {
			return Resource.TANK_LEFT;
		}
		if (face == Constant.FACE_RIGHT) {
			return Resource.TANK_RIGHT;
		}
		return Resource.TANK_UP;
	}
}
