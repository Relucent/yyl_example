package yyl.example.exercise.swt.hellolayout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * 填充布局
 */
public class MyFillLayout implements MouseListener {

	private Shell shell;
	private Button btnHorizontal;
	private Button btnVertical;
	private FillLayout fillLayout;

	public void open() {
		final Display display = Display.getDefault();
		shell = new Shell();
		fillLayout = new FillLayout();
		//# fillLayout.type=SWT.VERTICAL; 
		//# fillLayout.type=SWT.HORIZONTAL; 
		shell.setLayout(fillLayout);
		btnHorizontal = new Button(shell, SWT.NONE);
		btnHorizontal.setText("HORIZONTAL");
		btnHorizontal.addMouseListener(this);
		btnVertical = new Button(shell, SWT.NONE);
		btnVertical.setText("VERTICAL");
		btnVertical.addMouseListener(this);
		shell.setText("FillLayout");
		shell.setSize(200, 200);
		shell.layout();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void mouseDoubleClick(MouseEvent arg0) {
	}

	public void mouseDown(MouseEvent arg0) {
	}

	public void mouseUp(MouseEvent arg0) {
		if (arg0.getSource() == btnHorizontal) {
			fillLayout.type = SWT.HORIZONTAL;
		} else if (arg0.getSource() == btnVertical) {
			fillLayout.type = SWT.VERTICAL;
		}
		shell.layout();
	}

	public static void main(String[] args) {
		new MyFillLayout().open();
	}
}
