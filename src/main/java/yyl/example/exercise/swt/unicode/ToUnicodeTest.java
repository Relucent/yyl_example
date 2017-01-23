package yyl.example.exercise.swt.unicode;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ToUnicodeTest {

	static Text txtone;

	static Text txttwo;

	public static void main(String[] arg) {
		Display d = new Display();
		Shell shell = new Shell(d);
		shell.setLayout(new FillLayout());

		Composite com = new Composite(shell, SWT.NONE);
		com.setLayout(new GridLayout(2, false));
		com.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		txtone = new Text(com, SWT.BORDER);
		txtone.setLayoutData(new GridData(150, 15));
		txttwo = new Text(com, SWT.BORDER);
		txttwo.setLayoutData(new GridData(150, 15));

		Button btnone = new Button(com, SWT.PUSH);
		btnone.setText("to utf-8");
		btnone.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent arg0) {
				String iso = CodeConvertion.toISO(CodeConvertion.toUTF(txtone
						.getText()));
				txttwo.setText(iso);
			}
		});

		Button btntwo = new Button(com, SWT.PUSH);
		btntwo.setText("convert");
		btntwo.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent arg0) {

			}
		});

		shell.open();
		while (!shell.isDisposed()) {
			if (!d.readAndDispatch()) {
				d.sleep();
			}
		}
	}
}
