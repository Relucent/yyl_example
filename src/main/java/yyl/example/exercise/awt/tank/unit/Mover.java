package yyl.example.exercise.awt.tank.unit;

import java.awt.Rectangle;
import java.util.Collection;

import yyl.example.exercise.awt.tank.Constant;
import yyl.example.exercise.awt.tank.GameMatrix;

public abstract class Mover extends Unit {

	protected int face = Constant.FACE_UP;
	protected int moveSpace = 5;
	protected boolean locked = false;
	protected boolean stepLocked = false;
	protected boolean moveRepeat = false;

	public void move() {
		if (GameMatrix.getInstance().getTimer() % moveSpace != 0) {
			return;
		}
		if (this.locked || stepLocked) {
			return;
		}

		int _x = x;
		int _y = y;
		if (face == Constant.FACE_UP) {
			_y--;
		} else if (face == Constant.FACE_DOWN) {
			_y++;
		} else if (face == Constant.FACE_LEFT) {
			_x--;
		} else if (face == Constant.FACE_RIGHT) {
			_x++;
		}
		if (hitBorder(_x, _y)) {
			hit(null);
			return;
		}
		Collection<Unit> newProbes = probe(_x, _y);
		Collection<Unit> oldProbes = probe(x, y);

		int newHitCount = 0;
		int oldHitCount = 0;
		for (Unit unit : newProbes) {
			if (checkHit(unit)) {
				newHitCount++;
			}
			hit(unit);
		}
		for (Unit unit : oldProbes) {
			if (checkHit(unit)) {
				oldHitCount++;
			}
		}
		if (newHitCount > oldHitCount) {
			return;
		}
		x = _x;
		y = _y;
	}

	protected boolean hitBorder(int _x, int _y) {//
		GameMatrix matrix = GameMatrix.getInstance();
		Rectangle bounds = matrix.getBounds();
		return !bounds.contains(_x, _y, width, height);
	}

	protected Collection<Unit> probe(int _x, int _y) {
		GameMatrix matrix = GameMatrix.getInstance();
		Collection<Unit> units = matrix.inBlock(_x, _y, width, height);
		units.remove(this);
		return units;
	}
}
