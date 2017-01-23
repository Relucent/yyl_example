package yyl.example.exercise.swt.browser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class BrowserTest {

	public static void main(String[] args) {

		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setLayout(new FillLayout());

		Composite compCen = new Composite(shell, SWT.NONE);
		compCen.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		compCen.setLayout(new FillLayout());
		{
			Browser browser = new Browser(compCen, SWT.NONE);
			browser.setUrl("http://www.baidu.com/");// 显示浏览器首页
			//ScrolledComposite scrolledComposite = new ScrolledComposite(compCen, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
			//scrolledComposite.setExpandHorizontal(true);
			//scrolledComposite.setExpandVertical(true);
			//scrolledComposite.setContent(widGraphPanel);
		}

		shell.layout();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
