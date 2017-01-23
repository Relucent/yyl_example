package yyl.example.exercise.swt;

import java.awt.Frame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class SWT_TO_AWT {

	/**
	 * Launch the application
	 * SWT to AWT
	 * @param args
	 * 
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			//SWT_TO_AWT shell = new SWT_TO_AWT(display, SWT.SHELL_TRIM);
			Shell shell = new Shell();
			shell.setLayout(new FillLayout());

			shell.setText("SHELL");
			final Composite composite = new Composite(shell, SWT.EMBEDDED);
			final Frame frame = SWT_AWT.new_Frame(composite);

			frame.setLayout(null);

			final java.awt.Button button = new java.awt.Button();
			button.setLabel("AWT");
			button.setBounds(69, 159, 120, 30);
			frame.add(button);

			final Composite composite_1 = new Composite(shell, SWT.NONE);
			final Button button_1 = new Button(composite_1, SWT.NONE);
			button_1.setText("SWT");
			button_1.setBounds(69, 159, 120, 30);

			shell.layout();
			shell.open();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
