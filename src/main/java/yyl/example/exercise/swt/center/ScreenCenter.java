package yyl.example.exercise.swt.center;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * 获得屏幕中心
 */
public class ScreenCenter {

	public static void main(String arg[]) {
		Dimension di = Toolkit.getDefaultToolkit().getScreenSize();

		System.out.println(di.height + " " + di.width);
		final Display display = Display.getDefault();
		final Shell shell = new Shell();
		Rectangle r = display.getClientArea();
		System.out.println(r);
		shell.layout();
		shell.setLocation(di.width / 2 - shell.getSize().x / 2, di.height / 2 - shell.getSize().y / 2);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
