package yyl.example.exercise.awt.igo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

public class IGOBoard extends Canvas {

	private static final long serialVersionUID = 1L;

	private int matrixSize;

	private int matrix[][];

	protected final static int VOID = 0;
	protected final static int YIN = -1;
	protected final static int YANG = -2;
	protected final static int TAO_1 = 1;
	protected final static int TAO_2 = 2;
	protected final static int TAO_3 = 3;
	protected final static int TAO_4 = 4;
	protected final static int TAO_5 = 5;
	protected final static int TAO_6 = 6;
	protected final static int TAO_7 = 7;
	protected final static int TAO_8 = 8;
	protected final static int TAO_9 = 9;

	private Point lastStarPoint = new Point(-255, -255);
	private Point preMousePoint = new Point(-255, -255);

	private int iDoSignType = YIN;

	protected IGOBoard(int size) {
		matrix = new int[matrixSize = size][matrixSize];
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBackground(new Color(255, 128, 0));
		addControlListener();
	}

	protected void addControlListener() {
		this.addMouseMotionListener(new MouseMotionAdapter() {

			public void mouseMoved(MouseEvent event) {
				int x = event.getX();
				int y = event.getY();
				if (((preMousePoint.x - x) > 2) || ((preMousePoint.x - x) < 2)
						&& ((preMousePoint.y - y) > 2)
						|| ((preMousePoint.y - y) < 2)) {
					int cside = accountCellSide();
					repaint(preMousePoint.x - cside, preMousePoint.y - cside,
							cside * 2, cside * 2);
					preMousePoint = new Point(x, y);
				}
			}
		});
		addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent event) {
				int event_x = event.getX();
				int event_y = event.getY();
				Point point = getToPoint(event_x, event_y);
				if (point == null)
					return;
				switch (event.getButton())
				{
					case MouseEvent.BUTTON1: {
						gogo(point.y, point.x, YIN);
					}
						break;
					case MouseEvent.BUTTON2: {
						gogo(point.y, point.x, VOID);
					}
						break;
					case MouseEvent.BUTTON3: {
						gogo(point.y, point.x, YANG);
					}
						break;
					default: {
					}
						break;
				}
				repaint();
			}

			public void mouseExited(MouseEvent event) {
				int cside = accountCellSide();
				repaint(preMousePoint.x - cside, preMousePoint.y - cside,
						cside * 2, cside * 2);
				preMousePoint = new Point(-255, -255);
				repaint(event.getX() - cside, event.getY() - cside, cside * 2,
						cside * 2);
				System.out.println(event.getX());
			}

		});
	}

	public boolean gogo(int row, int col, int type) {
		if (type == VOID) {
			matrix[row][col] = VOID;
			return true;
		}

		if (matrix[row][col] != VOID)
			return false;

		matrix[row][col] = type;

		// top:
		if (row > 0 && matrix[row - 1][col] != type) {
			destroy(row - 1, col);
		}
		// bottom:
		if (row < matrixSize - 1 && matrix[row + 1][col] != type) {
			destroy(row + 1, col);
		}
		// left:
		if (col > 0 && matrix[row][col - 1] != type) {
			destroy(row, col - 1);
		}
		// right:
		if (col < matrixSize - 1 && matrix[row][col + 1] != type) {
			destroy(row, col + 1);
		}

		List<Point> passPoints = new ArrayList<Point>();
		int energy = getEnergy(row, col, passPoints, null, type);
		if (energy == 0) {
			matrix[row][col] = VOID;
			return false;
		}
		lastStarPoint = new Point(col, row);
		return true;
	}

	protected Point getToPoint(int iMouseX, int iMouseY) {
		int cside = accountCellSide();
		for (int row = 0; row < matrixSize; row++) {
			for (int col = 0; col < matrixSize; col++) {
				int x0 = cside * col;
				int y0 = cside * row;
				int x1 = cside * col + cside - 1;
				int y1 = cside * row + cside - 1;
				if (x0 < iMouseX && iMouseX < x1 && y0 < iMouseY
						&& iMouseY < y1) {
					return new Point(col, row);
				}
			}
		}
		return null;
	}

	public void paint(Graphics gc) {
		int cside = accountCellSide();

		for (int row = 0; row < matrixSize; row++) {
			for (int col = 0; col < matrixSize; col++) {
				int x = cside * col;
				int y = cside * row;

				int cellType = getCellPaintType(row, col);
				gc.setColor(new Color(0, 0, 0));
				switch (cellType)
				{
					case YIN: {// ��
						gc.setColor(new Color(0, 0, 0));
						gc.fillOval(x, y, cside, cside);
					}
						break;
					case YANG: {// ��
						gc.setColor(new Color(255, 255, 255));
						gc.fillOval(x, y, cside, cside);
						gc.setColor(new Color(0, 0, 0));
						gc.drawOval(x, y, cside, cside);
					}
						break;
					case TAO_1: {// ��
						gc.drawLine(x + cside / 2, y + cside / 2, x + cside, y
								+ cside / 2);
						gc.drawLine(x + cside / 2, y + cside / 2,
								x + cside / 2, y + cside);
					}
						break;
					case TAO_2: {// ��
						gc.drawLine(x, y + cside / 2, x + cside, y + cside / 2);
						gc.drawLine(x + cside / 2, y + cside / 2,
								x + cside / 2, y + cside);
					}
						break;
					case TAO_3: {// ��
						gc.drawLine(x, y + cside / 2, x + cside / 2, y + cside
								/ 2);
						gc.drawLine(x + cside / 2, y + cside / 2,
								x + cside / 2, y + cside);
					}
						break;
					case TAO_4: {// ��
						gc.drawLine(x + cside / 2, y + cside / 2, x + cside, y
								+ cside / 2);
						gc.drawLine(x + cside / 2, y, x + cside / 2, y + cside);
					}
						break;
					case TAO_5: {// ��
						gc.drawLine(x, y + cside / 2, x + cside, y + cside / 2);
						gc.drawLine(x + cside / 2, y, x + cside / 2, y + cside);
					}
						break;
					case TAO_6: {// ��
						gc.drawLine(x, y + cside / 2, x + cside / 2, y + cside
								/ 2);
						gc.drawLine(x + cside / 2, y, x + cside / 2, y + cside);
					}
						break;
					case TAO_7: {// ��
						gc.drawLine(x + cside / 2, y + cside / 2, x + cside, y
								+ cside / 2);
						gc.drawLine(x + cside / 2, y, x + cside / 2, y + cside
								/ 2);
					}
						break;
					case TAO_8: {// ��
						gc.drawLine(x, y + cside / 2, x + cside, y + cside / 2);
						gc.drawLine(x + cside / 2, y, x + cside / 2, y + cside
								/ 2);
					}
						break;
					case TAO_9: {// ��
						gc.drawLine(x, y + cside / 2, x + cside / 2, y + cside
								/ 2);
						gc.drawLine(x + cside / 2, y, x + cside / 2, y + cside
								/ 2);
					}
						break;
					default: {// ��
						gc.drawLine(x, y + cside / 2, x + cside, y + cside / 2);
						gc.drawLine(x + cside / 2, y, x + cside / 2, y + cside);
					}
						break;
				}
			}
		}
		if (iDoSignType == YIN) {
			gc.setColor(new Color(0, 0, 0));
			gc.fillOval(preMousePoint.x - cside / 2, preMousePoint.y - cside
					/ 2, cside, cside);
		}
		else if (iDoSignType == YANG) {
			gc.setColor(new Color(255, 255, 255));
			gc.fillOval(preMousePoint.x - cside / 2, preMousePoint.y - cside
					/ 2, cside, cside);
			gc.setColor(new Color(60, 60, 60));
			gc.drawOval(preMousePoint.x - cside / 2, preMousePoint.y - cside
					/ 2, cside, cside);
		}

	}

	public int getCellPaintType(int row, int col) {
		int flag = matrix[row][col];
		if (flag == VOID)
			return (row == 0 ? col == 0 ? TAO_1 : col == matrixSize - 1 ? TAO_3
					: TAO_2 : row == matrixSize - 1 ? col == 0 ? TAO_7
					: col == matrixSize - 1 ? TAO_9 : TAO_8 : col == 0 ? TAO_4
					: col == matrixSize - 1 ? TAO_6 : TAO_5);
		else
			return flag;
	}

	public int accountCellSide() {
		int h = this.getBounds().height;
		int w = this.getBounds().width;
		int side = h > w ? w : h;
		return (int) side / matrixSize;
	}

	public void destroy(int row, int col) {
		List<Point> passIlkPoints = new ArrayList<Point>();
		int energy = getEnergy(row, col, new ArrayList<Point>(), passIlkPoints,
				matrix[row][col]);
		if (energy == 0
				&& !(passIlkPoints.size() == 1 && passIlkPoints.get(0).equals(
						lastStarPoint))) {
			for (Point point : passIlkPoints) {
				matrix[point.y][point.x] = VOID;
			}
		}
	}

	private int getEnergy(int row, int col, List<Point> passPoints,
			List<Point> passIlkPoints, int iSignType) {
		if (passPoints == null)
			passPoints = new ArrayList<Point>();

		Point p = new Point(col, row);
		if (passPoints.contains(p)) {
			return 0;
		}

		passPoints.add(p);
		int iPointType = matrix[row][col];
		if (passIlkPoints != null && iPointType == iSignType) {
			passIlkPoints.add(p);
		}

		if (iPointType == 0) {
			return 1;
		}
		if (iPointType == iSignType) {
			int resultVis = 0;
			// top:
			if (row > 0) {
				resultVis += getEnergy(row - 1, col, passPoints, passIlkPoints,
						iSignType);
			}
			// bottom:
			if (row < matrixSize - 1) {
				resultVis += getEnergy(row + 1, col, passPoints, passIlkPoints,
						iSignType);
			}
			// left:
			if (col > 0) {
				resultVis += getEnergy(row, col - 1, passPoints, passIlkPoints,
						iSignType);
			}
			// right:
			if (col < matrixSize - 1) {
				resultVis += getEnergy(row, col + 1, passPoints, passIlkPoints,
						iSignType);
			}
			return resultVis;
		}
		return 0;
	}
}
