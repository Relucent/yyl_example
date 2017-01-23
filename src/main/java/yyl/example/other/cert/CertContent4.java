package yyl.example.other.cert;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CertContent4 {

	private Container container;

	private JTextField txtAlias;//别名
	private JTextField txtKeystore;//路径
	private JTextField txtCerFile;//路径
	private JTextField txtStorepass;//密码

	private JTextArea txtDebug;

	private JButton btnKeystore;//路径
	private JButton btnCerFile;//路径
	private JButton btnDisplay;
	private JButton btnExecute;

	private String sCommand;

	/** 构造函数 */
	protected CertContent4() {
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

		label = new JLabel("证书[cer]：");
		label.setBounds(10, 10, 70, 20);
		container.add(label);
		txtCerFile = new JTextField();
		txtCerFile.setBounds(80, 10, 300, 20);
		container.add(txtCerFile);
		btnCerFile = new JButton("浏览");
		btnCerFile.setBounds(380, 10, 70, 20);
		container.add(btnCerFile);

		label = new JLabel("证书库：");
		label.setBounds(10, 35, 70, 20);
		container.add(label);
		txtKeystore = new JTextField();
		txtKeystore.setBounds(80, 35, 300, 20);
		container.add(txtKeystore);
		btnKeystore = new JButton("浏览");
		btnKeystore.setBounds(380, 35, 70, 20);
		container.add(btnKeystore);

		label = new JLabel("别　名：");
		label.setBounds(10, 60, 70, 20);
		container.add(label);
		txtAlias = new JTextField();
		txtAlias.setBounds(80, 60, 120, 20);
		container.add(txtAlias);

		label = new JLabel("密　码：");
		label.setBounds(260, 60, 70, 20);
		container.add(label);
		txtStorepass = new JTextField();
		txtStorepass.setBounds(330, 60, 120, 20);
		container.add(txtStorepass);

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
		txtCerFile.setText("");
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
		btnCerFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
					public boolean accept(File f) {
						return (f.isFile() && f.getPath().endsWith(".cer"));
					}

					public String getDescription() {
						return ".cer";
					}
				});
				int returnVal = chooser.showOpenDialog(btnCerFile);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					txtCerFile.setText(chooser.getSelectedFile().getAbsolutePath());
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
				new ExecuteCommand(txtDebug).execute(sCommand, new String[] { "y" });
				btnExecute.setEnabled(false);
			}
		});
	}

	private String getCommand() {
		String sCerFile = txtCerFile.getText().trim();//证书路径
		String sKeystore = txtKeystore.getText().trim();//证书库路径
		String sAlias = txtAlias.getText().trim();//别名
		String sStorepass = txtStorepass.getText().trim();//密码

		StringBuilder sbr = new StringBuilder();
		sbr.append(" keytool -import -trustcacerts");
		sbr.append(" -alias ").append(sAlias).append(" ");
		sbr.append(" -file \"").append(sCerFile).append("\" ");
		sbr.append(" -keystore \"").append(sKeystore).append("\" ");
		sbr.append(" -storepass ").append(sStorepass).append(" ");

		if (sCerFile.length() == 0 || sKeystore.length() == 0 || sAlias.length() == 0 || sStorepass.length() == 0) {
			btnExecute.setEnabled(false);
		} else {
			btnExecute.setEnabled(true);
			FileInputStream fis = null;
			try {
				KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
				char[] password = sStorepass.toCharArray();
				fis = new FileInputStream(sKeystore);
				ks.load(fis, password);
				for (Enumeration<?> en = ks.aliases(); en.hasMoreElements();) {
					if (sAlias.equals(String.valueOf(en.nextElement()))) {
						btnExecute.setEnabled(false);
						sbr.append("\n别名 ").append(sAlias).append(" 已存在\n");
					}
				}
			} catch (Exception e) {
				btnExecute.setEnabled(false);
				sbr.append("\n KeyStore文件错误,该脚本无法执行\n").append(e.toString());
			} finally {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sbr.toString();
	}
}
