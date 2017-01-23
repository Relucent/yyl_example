package yyl.example.exercise.awt.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import yyl.example.exercise.awt.tank.unit.BattleTank;
import yyl.example.exercise.awt.tank.unit.Block;
import yyl.example.exercise.awt.tank.unit.EnemyTank;
import yyl.example.exercise.awt.tank.unit.Forest;
import yyl.example.exercise.awt.tank.unit.Ice;
import yyl.example.exercise.awt.tank.unit.Lake;
import yyl.example.exercise.awt.tank.unit.Steel;
import yyl.example.exercise.awt.tank.unit.Unit;

public class GameMatrix {

	private static GameMatrix instance;
	private BattleTank tank1P = null;
	private BattleTank tank2P = null;
	private int timer = 0;
	private Collection<EnemyTank> eTanks = new ArrayList<EnemyTank>();

	/** 1P **/
	public void command1P(int handle) {
		if (tank1P != null) {
			tank1P.command(handle);
		}
	}

	/** 2P **/
	public void command2P(int handle) {
		if (tank2P != null) {
			tank2P.command(handle);
		}
	}

	public int getTimer() {
		return timer;
	}

	public Collection<Unit> inBlock(int x, int y, int width, int height) {
		Collection<Unit> units = new HashSet<Unit>();
		for (int i = 0; i < elements.size(); i++) {
			Unit unit = elements.get(i);
			if (unit.intersects(x, y, width, height)) {
				units.add(unit);
			}
		}
		return units;
	}

	public Collection<Point> getGrids(int _x, int _y, int _width, int _height) {
		Collection<Point> grids = new HashSet<Point>();
		for (int i = 0; i < _width; i++) {
			for (int j = 0; j < _height; j++) {
				Point point = new Point(_x + i, _y + j);
				if (bounds.contains(point)) {
					grids.add(point);
				}
			}
		}
		return grids;
	}

	public static final GameMatrix getInstance() {
		if (instance == null) {
			instance = new GameMatrix();
			instance.readys();
		}
		return instance;
	}

	public void go() {
		repeatTimer();

		if (timer % 500 == 0) {
			relive();
		}
		if (timer % 100 == 0) {
			repeatEnemyTank();
		}

		List<Unit> units = getUnits();
		for (int i = 0; i < units.size(); i++) {
			units.get(i).go();
		}
	}

	private void repeatTimer() {
		timer = (timer == Integer.MAX_VALUE) ? 0 : timer + 1;
	}

	public void relive() {
		if (tank1P == null) {
			addUnit(tank1P = new BattleTank(4 * 4, 12 * 4));
		}
		if (tank2P == null) {
			addUnit(tank2P = new BattleTank(8 * 4, 12 * 4));
		}
	}

	public void repeatEnemyTank() {
		if (eTanks.size() < 5) {
			int random = (int) (Math.random() * 3);
			int type = (int) (Math.random() * 3);
			if (random == 0) {
				addUnit(new EnemyTank(0, 0, type));
			} else if (random == 1) {
				addUnit(new EnemyTank(6 * 4, 0, type));
			} else {
				addUnit(new EnemyTank(12 * 4, 0, type));
			}
		}
	}

	public void paint(Graphics graphics, int width, int height, int unitPx) {
		Color preColor = graphics.getColor();
		graphics.clearRect(0, 0, width, height);
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, width, height);
		List<Unit> units = getUnits();
		for (int i = 0; i < units.size(); i++) {
			units.get(i).paint(graphics, unitPx, null);
		}
		graphics.setColor(preColor);
	}

	private Rectangle bounds = new Rectangle(0, 0, 13 * 4, 13 * 4);
	private List<Unit> elements = new ArrayList<Unit>();

	public Rectangle getBounds() {
		return bounds;
	}

	public List<Unit> getUnits() {
		return elements;
	}

	public void addUnit(Unit unit) {
		if (unit instanceof EnemyTank) {
			eTanks.add((EnemyTank) unit);
		}
		elements.add(unit);
	}

	public void removeUnit(Unit unit) {
		if (unit == tank1P) {
			tank1P = null;
		} else if (unit == tank2P) {
			tank2P = null;
		}
		if (unit instanceof EnemyTank) {
			eTanks.remove((EnemyTank) unit);
		}
		elements.remove(unit);
	}

	public void readys() {
		char[][] map = Resource.readys();
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				Collection<Unit> units = createUnit(map[x][y], x, y);
				for (Unit unit : units) {
					addUnit(unit);
				}

			}
		}
		relive();
	}

	private Collection<Unit> createUnit(int code, int x, int y) {
		Collection<Unit> units = new HashSet<Unit>();
		if ('B' == code) {
			for (int k = 0; k < 16; k++) {
				if ((0xFFFF & (1 << k)) == (1 << k)) {
					if (k == 0 | k == 2 | k == 8 | k == 10)
						units.add(new Block(x * 4 + k % 4, y * 4 + k / 4, 1));
					else if (k == 1 | k == 3 | k == 9 | k == 11)
						units.add(new Block(x * 4 + k % 4, y * 4 + k / 4, 2));
					else if (k == 4 | k == 6 | k == 12 | k == 14)
						units.add(new Block(x * 4 + k % 4, y * 4 + k / 4, 3));
					else
						units.add(new Block(x * 4 + k % 4, y * 4 + k / 4, 4));
				}
			}
			return units;
		}

		if ('S' == code) {
			for (int k = 0; k < 4; k++) {
				units.add(new Steel(x * 4 + 2 * (k % 2), y * 4 + 2 * (k / 2)));
			}
			return units;
		}
		if ('F' == code) {
			units.add(new Forest(x * 4, y * 4));
			return units;
		}
		if ('L' == code) {
			units.add(new Lake(x * 4, y * 4));
			return units;
		}
		if ('I' == code) {
			units.add(new Ice(x * 4, y * 4));
			return units;
		}
		return units;
	}
}
