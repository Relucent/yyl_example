package yyl.example.exercise.swt.hellolayout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

// ����ʽ����
public class MyFillLayout implements MouseListener {

	private Shell shell;

	private Button b0;

	private Button b1;

	private FillLayout fillLayout;

	public static void main(String[] args) {
		new MyFillLayout().open();
	}

	public void open() {
		final Display display = Display.getDefault();
		shell = new Shell();
		fillLayout = new FillLayout();
		// fillLayout.type=SWT.VERTICAL; //��ֱ
		// fillLayout.type=SWT.HORIZONTAL; //ˮƽ(Ĭ��)
		shell.setLayout(fillLayout);
		b0 = new Button(shell, SWT.NONE);
		b0.setText("ˮƽ");
		b0.addMouseListener(this);
		b1 = new Button(shell, SWT.NONE);
		b1.setText("��ֱ");
		b1.addMouseListener(this);
		shell.setText("FillLayout");
		shell.setSize(100, 100);
		shell.layout();
		shell.open();
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
	}

	public void mouseDoubleClick(MouseEvent arg0) {
	}

	public void mouseDown(MouseEvent arg0) {
	}

	public void mouseUp(MouseEvent arg0) {
		if (arg0.getSource() == b0)
			fillLayout.type = SWT.HORIZONTAL;
		else if (arg0.getSource() == b1)
			fillLayout.type = SWT.VERTICAL;
		shell.layout();
	}
}
