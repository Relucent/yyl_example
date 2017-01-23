package yyl.example.exercise.swt.hellolayout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

//����ʽ����
public class MyRowLayout {

	public static void main(String[] args) {
		final Display display = Display.getDefault();
		final Shell shell = new Shell();

		RowLayout rowLayout = new RowLayout();

		rowLayout.marginTop = 10; // ����������ϱ�Ե10�����ؼ����Ĭ��0��
		rowLayout.marginLeft = 5; // ������������Ե5�����ؼ�� ��Ĭ��0��
		rowLayout.spacing = 2; // �������2�����ؼ�� ��Ĭ��0��

		rowLayout.wrap = true; // true or false �����Ƿ�����Ĭ��true
		rowLayout.type = SWT.HORIZONTAL; // ˮƽ���У�Ĭ�ϣ�
		// rowLayout.type = SWT.VERTICAL; //��ֱ����

		rowLayout.pack = true; // ���������С �������Ϊfalse�����������С��ͬ(Ĭ��true)
		rowLayout.justify = false; // true ����Ƿ���Ը�ݿռ��С�Զ���չ��Ĭ��false��

		new Button(shell, SWT.NONE).setText("b1");
		new Button(shell, SWT.NONE).setText("button2");
		Button b = new Button(shell, SWT.NONE);
		b.setText("btn3");

		// RowData���ڿ��������С��� ��
		RowData rowData = new RowData(50, 50);
		b.setLayoutData(rowData);

		shell.setLayout(rowLayout);
		shell.setText("RowLayout");
		shell.setSize(200, 200);
		shell.layout();
		shell.open();
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
	}

}
