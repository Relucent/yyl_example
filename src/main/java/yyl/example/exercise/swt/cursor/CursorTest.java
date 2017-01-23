package yyl.example.exercise.swt.cursor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class CursorTest {

	private static enum CURSOR {
		CURSOR_ARROW, CURSOR_WAIT, CURSOR_CROSS, CURSOR_APPSTARTING, CURSOR_HELP, CURSOR_SIZEALL, CURSOR_SIZENESW, CURSOR_SIZENS, CURSOR_SIZENWSE, CURSOR_SIZEWE, CURSOR_SIZEN, CURSOR_SIZES, CURSOR_SIZEE, CURSOR_SIZEW, CURSOR_SIZENE, CURSOR_SIZESE, CURSOR_SIZESW, CURSOR_SIZENW, CURSOR_UPARROW, CURSOR_IBEAM, CURSOR_NO, CURSOR_HAND,
	}

	public static void main(String args[]) {
		try {
			final Display display = Display.getDefault();
			final Shell shell = new Shell(display, SWT.SHELL_TRIM);
			shell.setLayout(new FillLayout());
			final List list = new List(shell, SWT.BORDER);
			final Composite composite = new Composite(shell, SWT.NONE);

			CURSOR[] CURSORS = CURSOR.values();
			String[] itemnames = new String[CURSORS.length];
			for (int i = 0; i < CURSORS.length; i++) {
				itemnames[i] = CURSORS[i].name();
			}
			list.setItems(itemnames);

			list.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					composite.setCursor(new Cursor(display, list.getSelectionIndex()));
				}
			});

			shell.setText("SWT Cursor");
			shell.setSize(500, 320);
			shell.open();

			shell.layout();
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
