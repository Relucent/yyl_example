package yyl.example.exercise.swt.hellolayout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * 表格布局
 */
public class MyGridLayout {

	public static void main(String[] args) {
		final Display display = Display.getDefault();
		final Shell shell = new Shell();

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.makeColumnsEqualWidth = false;
		//# GridLayout gridLayout = new GridLayout(2,false);

		gridLayout.marginTop = 10;
		gridLayout.marginLeft = 5;
		gridLayout.verticalSpacing = 2;

		new Button(shell, SWT.NONE).setText("b1");
		new Button(shell, SWT.NONE).setText("button2");

		Button b3 = new Button(shell, SWT.NONE);
		b3.setText("b3");

		new Button(shell, SWT.NONE).setText("button5");
		new Button(shell, SWT.NONE).setText("btn6");

		//选中按钮，点击F1帮助 执行该事件
		b3.addHelpListener(new HelpListener() {
			public void helpRequested(HelpEvent arg0) {
				System.out.println("点击了帮助");
			}
		});

		GridData gridData = new GridData();

		//# new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		//# new GridData(GridData.FILL_HORIZONTAL);
		//# new GridData(GridData.FILL_VERTICAL);
		//# new GridData(GridData.FILL_BOTH);

		gridData.horizontalSpan = 2;

		//# gridData.horizontalAlignment = GridData.BEGINNING; 
		//# gridData.horizontalAlignment = GridData.CENTER;
		//# gridData.horizontalAlignment = GridData.END; 
		//# gridData.horizontalAlignment = GridData.FILL; 

		// gridDate.horizontalAlignment = 10; 
		gridData.grabExcessHorizontalSpace = false;

		// gridData.widthHint = 80;
		// gridData.heightHint = 20;

		b3.setLayoutData(gridData);
		shell.setLayout(gridLayout);
		shell.setText("GridLayout");
		shell.setSize(200, 200);
		shell.layout();
		shell.open();
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
	}
}
