package yyl.example.exercise.swt.hellolayout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

//���ʽ���� �� SWT2.0 ��
public class MyFormLayout {

	public static void main(String[] args) {
		final Display display = Display.getDefault();
		final Shell shell = new Shell();

		FormLayout formLayout = new FormLayout();

		formLayout.marginWidth = 20;
		formLayout.marginHeight = 20;

		new Button(shell, SWT.NONE).setText("��ť1");

		Button b = new Button(shell, SWT.NONE);
		b.setText("��ť2");

		FormData formData = new FormData(100, 50);
		b.setLayoutData(formData);

		shell.setLayout(formLayout);
		shell.setText("FormLayout");
		shell.setSize(200, 100);
		shell.layout();
		shell.open();
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
	}
}
