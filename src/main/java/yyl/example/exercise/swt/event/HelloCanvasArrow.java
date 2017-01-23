package yyl.example.exercise.swt.event;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import yyl.example.exercise.swt.SWTResourceManager;


public class HelloCanvasArrow {

	public static void main(String[] args) {
		new HelloCanvasArrow().open();
	}

	int[] p = new int[4];
	

	boolean b = false;


	public void open() {
		final Display display = Display.getDefault();
		final Shell shell = new Shell();
		shell.setLayout(new FillLayout());

		final Canvas canvas = new Canvas(shell, SWT.NONE);
		canvas.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_ARROW));

		canvas.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent arg0) {
			}

			public void mouseDown(MouseEvent e) {
				p[0] = e.x;
				p[1] = e.y;
				b = true;
			}

			public void mouseUp(MouseEvent arg0) {
				if (b) {

					int[] tp = new int[4];
					tp[0] = p[0];
					tp[1] = p[0];
					tp[2] = p[0];
					tp[3] = p[3];
				}
				b = false;
			}
		});

		canvas.addMouseMoveListener(new MouseMoveListener() {

			public void mouseMove(MouseEvent e) {
				p[2] = e.x;
				p[3] = e.y;
				canvas.redraw();
			}
		});

		canvas.addPaintListener(new PaintListener() {

			public void paintControl(PaintEvent paintevent) {
				
				if (b){
					paintArrow(paintevent.gc, new Point(p[0], p[1]), new Point(p[2], p[3]));
				}
			}
		});

		shell.setText("test");
		shell.setSize(300, 300);
		shell.layout();
		shell.open();
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
	}

	private void paintArrow(GC gc, Point startpoint, Point endpoint){
		int arrow = 60; //��ͷת��
		int lenth = 40; //��ͷ���

		double angle = 0;
		if (Math.abs(endpoint.x - startpoint.x) < 0.1 && endpoint.y < startpoint.y)
			angle = -90;
		else if (Math.abs(endpoint.x - startpoint.x) < 0.1 && endpoint.y > startpoint.y)
			angle = 90;
		else if(startpoint.x == endpoint.x)
			angle = 180;
		else{
			angle = Math.atan((double)(startpoint.y - endpoint.y) / (startpoint.x - endpoint.x)) * 180 / Math.PI;
		}
			
		System.out.println(angle);
		
		if (startpoint.x > endpoint.x)
			angle = 180 + angle;

		double left = 270 - angle - arrow / 2;
		double right = 270 - angle + arrow / 2;

		double rx = lenth * Math.sin(right * Math.PI / 180);
		double ry = lenth * Math.cos(right * Math.PI / 180);
		double lx = lenth * Math.sin(left * Math.PI / 180);
		double ly = lenth * Math.cos(left * Math.PI / 180);

		gc.drawLine(startpoint.x, startpoint.y, endpoint.x, endpoint.y);

		gc.drawLine(endpoint.x, endpoint.y, endpoint.x + (int) lx,
				endpoint.y + (int) ly);
		gc.drawLine(endpoint.x, endpoint.y, endpoint.x + (int) rx,
				endpoint.y
				+ (int) ry);
	}
}
