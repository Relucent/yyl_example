package yyl.example.exercise.swt.hellolayout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

//���ʽ����
public class MyGridLayout {

	public static void main(String[] args) {
		final Display display = Display.getDefault();
		final Shell shell = new Shell();
		// ����ʽ����
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2; // ����
		gridLayout.makeColumnsEqualWidth = false; // �Ƿ�Ⱦ�ָ�

		// GridLayout gridLayout = new GridLayout(2,false); //��һ�ֹ���

		// �߾����ò��� �� RowLayout����
		gridLayout.marginTop = 10; // ����������ϱ�Ե10�����ؼ����Ĭ��0��
		gridLayout.marginLeft = 5; // ������������Ե5�����ؼ�� ��Ĭ��0��
		gridLayout.verticalSpacing = 2; // �������2�����ؼ�� ��Ĭ��0��

		new Button(shell, SWT.NONE).setText("b1");
		new Button(shell, SWT.NONE).setText("button2");
		Button b = new Button(shell, SWT.NONE);
		b.setText("b3");
		new Button(shell, SWT.NONE).setText("button5");
		new Button(shell, SWT.NONE).setText("btn6");

		b.addHelpListener(new HelpListener() {

			public void helpRequested(HelpEvent arg0) {
				System.out.println("�����˰�����Ϣ");
			}
		});

		// GridData > ���Ƹ��Ӳ���

		GridData gridData = new GridData();// ����һ��GridData

		// /*GridData ���죺*/
		// new GridData(GridData.HORIZONTAL_ALIGN_FILL);//ˮƽ�������ģʽ
		// new GridData(GridData.FILL_HORIZONTAL);//ˮƽ������ռģʽ
		// new GridData(GridData.FILL_VERTICAL);//��ֱ������ռģʽ
		// new GridData(GridData.FILL_BOTH);//˫����ռģʽ

		gridData.horizontalSpan = 2;// �������ռ�����еĿռ�

		// /*���뷽ʽ��*/
		// gridData.horizontalAlignment = GridData.BEGINNING; //����루Ĭ�ϣ�
		// gridData.horizontalAlignment = GridData.CENTER; //���ж���
		// gridData.horizontalAlignment = GridData.END; //���Ҷ���
		// gridData.horizontalAlignment = GridData.FILL; //�������пռ�

		// gridDate.horizontalAlignment = 10;// ʹ��������ƶ�10������
		gridData.grabExcessHorizontalSpace = false;// ����Ƿ���������С�ı��ı䣨Ĭ��false��

		// /*���������С��*/
		// gridData.widthHint = 80;
		// gridData.heightHint = 20;

		b.setLayoutData(gridData);
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
