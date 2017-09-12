package yyl.example.exercise.swt;

import java.awt.Toolkit;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class SWT_HelloWorld {

	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setText("Hello");
		shell.setSize(300, 200);
		setCenter(shell);
		shell.layout();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	private static void setCenter(Shell shell) {
		int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
		int screenW = Toolkit.getDefaultToolkit().getScreenSize().width;
		int shellH = shell.getBounds().height;
		int shellW = shell.getBounds().width;
		if (shellH > screenH) {
			shellH = screenH;
		}
		if (shellW > screenW) {
			shellW = screenW;
		}
		shell.setLocation(((screenW - shellW) / 2), ((screenH - shellH) / 2));
	}
}
