package yyl.example.other.cert;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CertContent3 {

	private Container container;

	private JTextField txtAlias;//别名

	private JTextField txtKeystore;//路径
	private JButton btnKeystore;//路径
	private JTextField txtStorepass;//密码

	private JTextArea txtDebug;
	private JButton btnDisplay;
	private JButton btnExecute;

	private JRadioButton rdoKey;
	private JRadioButton rdoCer;

	private String sCommand;

	/** 构造函数 */
	protected CertContent3() {
		initContent();
		registerListener();
	}

	/** 获得Container */
	protected Container getContainer() {
		return container;
	}

	/** 初始化界面 */
	private void initContent() {
		container = new Container();
		container.setLayout(null);

		JLabel label;

		label = new JLabel("路径：");
		label.setBounds(10, 10, 70, 20);
		container.add(label);
		txtKeystore = new JTextField();
		txtKeystore.setBounds(80, 10, 300, 20);
		container.add(txtKeystore);
		btnKeystore = new JButton("浏览");
		btnKeystore.setBounds(380, 10, 70, 20);
		container.add(btnKeystore);

		label = new JLabel("别名：");
		label.setBounds(10, 35, 70, 20);
		container.add(label);
		txtAlias = new JTextField();
		txtAlias.setBounds(80, 35, 120, 20);
		container.add(txtAlias);

		label = new JLabel("密码：");
		label.setBounds(260, 35, 70, 20);
		container.add(label);
		txtStorepass = new JTextField();
		txtStorepass.setBounds(330, 35, 120, 20);
		container.add(txtStorepass);

		label = new JLabel("文件类型：");
		label.setBounds(10, 60, 70, 20);
		container.add(label);
		ButtonGroup group = new ButtonGroup();
		rdoKey = new JRadioButton("keystore");
		rdoKey.setBounds(80, 60, 80, 20);
		rdoKey.setBackground(new Color(200, 221, 242));
		rdoKey.setSelected(true);
		group.add(rdoKey);
		container.add(rdoKey);
		rdoCer = new JRadioButton("cer");
		rdoCer.setBounds(180, 60, 80, 20);
		rdoCer.setBackground(new Color(200, 221, 242));
		group.add(rdoCer);
		container.add(rdoCer);

		txtDebug = new JTextArea();
		txtDebug.setEditable(false);
		JScrollPane scroller = new JScrollPane(txtDebug);
		scroller.setBorder(BorderFactory.createLineBorder(new Color(0xFF0000)));
		scroller.setBounds(10, 90, 460, 200);
		container.add(scroller);

		btnDisplay = new JButton();
		btnDisplay.setBounds(130, 300, 100, 20);
		btnDisplay.setText("显示脚本");
		container.add(btnDisplay);

		btnExecute = new JButton();
		btnExecute.setBounds(250, 300, 100, 20);
		btnExecute.setText("运行脚本");
		btnExecute.setEnabled(false);
		container.add(btnExecute);

		txtKeystore.setText("");
		txtAlias.setText("");
		txtStorepass.setText("changeit");
		txtDebug.setText("");
	}

	/** 注册监听 */
	private void registerListener() {
		btnKeystore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = chooser.showOpenDialog(btnKeystore);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					txtKeystore.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		btnDisplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sCommand = getCommand();
				txtDebug.setText("");
				txtDebug.append(sCommand);
			}
		});
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtDebug.setText("");
				new ExecuteCommand(txtDebug).execute(sCommand, null);
				btnExecute.setEnabled(false);
			}
		});
	}

	private String getCommand() {
		if (rdoKey.isSelected()) {
			String sKeystore = txtKeystore.getText().trim();//路径
			String sAlias = txtAlias.getText().trim();//别名
			String sStorepass = txtStorepass.getText().trim();//密码

			StringBuilder sbr = new StringBuilder();
			sbr.append(" keytool -list -v ");
			if (sAlias.trim().length() > 0) {
				sbr.append(" -alias ").append(sAlias).append(" ");
			}
			sbr.append(" -keystore \"").append(sKeystore).append("\" ");
			sbr.append(" -storepass ").append(sStorepass).append(" ");

			if (sKeystore.length() == 0 || sStorepass.length() == 0 || !new File(sKeystore).isFile()) {
				btnExecute.setEnabled(false);
			} else {
				btnExecute.setEnabled(true);
			}
			return sbr.toString();
		} else {
			String sKeystore = txtKeystore.getText().trim();
			StringBuilder sbr = new StringBuilder();
			sbr.append(" keytool -printcert ");
			sbr.append(" -file \"").append(txtKeystore.getText()).append("\" ");
			if (sKeystore.length() == 0 || !new File(sKeystore).isFile()) {
				btnExecute.setEnabled(false);
			} else {
				btnExecute.setEnabled(true);
			}
			return sbr.toString();
		}

	}
}
