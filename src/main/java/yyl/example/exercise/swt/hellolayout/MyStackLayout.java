package yyl.example.exercise.swt.hellolayout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Stack 布局
 */
public class MyStackLayout {

	public static void main(String[] args) {
		new MyStackLayout().open();
	}

	void open() {
		final Display display = Display.getDefault();
		final Shell shell = new Shell();
		final Composite comp1 = new Composite(shell, SWT.NONE);
		final StackLayout stackLayout = new StackLayout();
		final Text txt1 = new Text(comp1, SWT.BORDER);
		txt1.setText("T1");
		final Text txt2 = new Text(comp1, SWT.BORDER);
		txt2.setText("T2");

		Composite comp2 = new Composite(shell, SWT.NONE);
		comp2.setLayout(new RowLayout());
		Button btn1 = new Button(comp2, SWT.NONE);
		Button btn2 = new Button(comp2, SWT.NONE);
		btn1.setText("T1");
		btn2.setText("T2");

		comp1.setLayout(stackLayout);
		stackLayout.topControl = txt1;

		btn1.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				stackLayout.topControl = txt1;
				comp1.layout();
			}
		});
		btn2.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				stackLayout.topControl = txt2;
				comp1.layout();
			}
		});

		shell.setLayout(new FillLayout());
		shell.setText("StackLayout");
		shell.setSize(200, 200);
		//		shell.setMaximized(true);
		shell.layout();
		shell.open();
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
	}
}
