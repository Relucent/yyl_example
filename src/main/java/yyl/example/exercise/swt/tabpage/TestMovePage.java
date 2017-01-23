package yyl.example.exercise.swt.tabpage;

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

public class TestMovePage {

	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setLayout(new FillLayout());

		final Composite TabFolderComp = new Composite(shell, SWT.NONE);
		GridLayout layout = new GridLayout(1, true);
		layout.verticalSpacing = 1;
		TabFolderComp.setLayout(layout);

		for (int i = 0; i < 3; i++) {
			final Composite TabPageComp = new Composite(TabFolderComp, SWT.NONE);
			TabPageComp.setLayoutData(new GridData(GridData.FILL_BOTH));
			final GridLayout gridLayout = new GridLayout(1, true);
			gridLayout.verticalSpacing = 1;
			gridLayout.marginHeight = 1;
			gridLayout.marginRight = 1;
			gridLayout.marginWidth = 1;
			TabPageComp.setLayout(gridLayout);
			Button TabItemButton = new Button(TabPageComp, SWT.FLAT);
			GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
			gd.heightHint = 20;
			TabItemButton.setLayoutData(gd);
			final Composite TabItemComp = new Composite(TabPageComp, SWT.FLAT
					| SWT.CENTER | SWT.BORDER);
			TabItemComp.setLayoutData(new GridData(GridData.FILL_BOTH));
			TabItemButton.addSelectionListener(new SelectionAdapter() {

				public void widgetSelected(SelectionEvent arg0) {
					boolean b = TabItemComp.getVisible();
					if (!b) {
						TabPageComp.setLayoutData(new GridData(
								GridData.FILL_BOTH));
					}
					else {
						GridData gd = new GridData(SWT.FILL, SWT.CENTER, true,
								false);
						gd.heightHint = 20;
						TabPageComp.setLayoutData(gd);
					}
					TabItemComp.setVisible(!b);
					TabFolderComp.layout();
				}
			});
		}
		shell.setSize(50, 300);
		shell.layout();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
