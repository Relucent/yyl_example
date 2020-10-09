package yyl.example.exercise.swt.browser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * SWT内置浏览器
 */
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
            // ScrolledComposite scrolledComposite = new ScrolledComposite(compCen, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
            // scrolledComposite.setExpandHorizontal(true);
            // scrolledComposite.setExpandVertical(true);
            // scrolledComposite.setContent(widGraphPanel);
        }

        shell.layout();
        shell.open();

        // 如果当前窗口未被销毁
        while (!shell.isDisposed()) {
            // display要一直监听窗体事件，没有窗体事件发生时
            if (!display.readAndDispatch()) {
                // 显示对象处于休眠状态
                display.sleep();
            }
        }
        // 销毁当前对象
        display.dispose();
    }
}
