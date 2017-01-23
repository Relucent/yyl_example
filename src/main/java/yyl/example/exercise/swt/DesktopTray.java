package yyl.example.exercise.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

public class DesktopTray {

	private Shell shell;
	private Tray tray;//ϵͳ��ͼ��ؼ�
	private Display display;

	public DesktopTray() {
		display = new Display();
		shell = new Shell(display, SWT.SHELL_TRIM ^ SWT.MAX);//������󻯰�ť
		shell.setText("TrayExample");
		//ȡϵͳ��Ԥ�õ�ͼ��
		shell.setImage(display.getSystemImage(SWT.ICON_WORKING));

		tray = display.getSystemTray();
		
		initTrayItem();
		registerListener();

		shell.setSize(320, 240);
		Monitor monitor = shell.getMonitor();
		Rectangle bounds = monitor.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
//initContainer
	
	private void initTrayItem() {
		TrayItem trayItem = new TrayItem(tray, SWT.NONE);
		trayItem.setVisible(true);
		trayItem.setToolTipText(shell.getText());


		final Menu trayMenu = new Menu(shell, SWT.POP_UP);

		
		MenuItem showMenuItem = new MenuItem(trayMenu, SWT.PUSH);
		showMenuItem.setText("��ʾ����");
		trayMenu.setDefaultItem(showMenuItem);

		new MenuItem(trayMenu, SWT.SEPARATOR);
  
		MenuItem item;
		item = new MenuItem(trayMenu, SWT.PUSH);
		item.setText("��ťA");
		item = new MenuItem(trayMenu, SWT.PUSH);
		item.setText("��ťB");
		item = new MenuItem(trayMenu, SWT.PUSH);
		item.setText("��ťC");
		item = new MenuItem(trayMenu, SWT.PUSH);
		item.setText("��ťD");
		item = new MenuItem(trayMenu, SWT.PUSH);
		item.setText("�ر�");
		item.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO �Զ���ɷ������
				
			}

			public void widgetSelected(SelectionEvent arg0) {
				close();
			}
			
		});

		
		trayItem.addListener(SWT.MenuDetect, new Listener()
        {
            public void handleEvent(Event event)
            {
            	trayMenu.setVisible(true);
            }
        });

		//��ϵͳ��ͼ��������Ҽ�ʱ���¼�������ϵͳ���˵�   
		trayItem.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {
	

			}
			public void widgetSelected(SelectionEvent event) {
				trayMenu.setVisible(true);
			}
		});

		
		
		trayItem.setImage(shell.getImage());
	}
	
	private void registerListener() {
		//ע�ᴰ���¼�������   
		shell.addShellListener(new ShellAdapter() {

			//���������С����ťʱ���������أ�ϵͳ����ʾͼ��   
			public void shellIconified(ShellEvent e) {
				toggleDisplay();
			}

			//������ڹرհ�ťʱ��������ֹ���򣬶�ʱ���ش��ڣ�ͬʱϵͳ����ʾͼ��   
			public void shellClosed(ShellEvent e) {
				e.doit = false; //��ĵ�ԭ��ϵͳ��������¼�   
				toggleDisplay();
			}
		});
	}

	public static void main(String[] args) {
		new DesktopTray();
	}

	/**  
	 * �����ǿɼ�״̬ʱ�������ش��ڣ�ͬʱ��ϵͳ����ͼ��ɾ��  
	 * ����������״̬ʱ������ʾ���ڣ�������ϵͳ������ʾͼ��  
	 */
	private void toggleDisplay() {
		try {
			shell.setVisible(!shell.isVisible());
			tray.getItem(0).setVisible(!shell.isVisible());
			if (shell.getVisible()) {
				shell.setMinimized(false);
				shell.setActive();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		shell.close();
		display.dispose();
	}

}