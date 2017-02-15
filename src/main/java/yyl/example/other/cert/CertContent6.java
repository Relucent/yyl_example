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
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CertContent6 {

	private Container container;

	private JTextField txtKeystore;//路径
	private JTextField txtStorepass;//密码
	private JTextField txtKeyFile;//路径

	private JComboBox<String> cboAlias;//别名

	private JTextArea txtDebug;

	private JButton btnKeystore;//路径
	private JButton btnExecute;

	/** 构造函数 */
	protected CertContent6() {
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

		label = new JLabel("证书库：");
		label.setBounds(10, 10, 70, 20);
		container.add(label);
		txtKeystore = new JTextField();
		txtKeystore.setBounds(80, 10, 300, 20);
		container.add(txtKeystore);
		btnKeystore = new JButton("浏览");
		btnKeystore.setBounds(380, 10, 70, 20);
		container.add(btnKeystore);

		label = new JLabel("别　名：");
		label.setBounds(10, 35, 70, 20);
		container.add(label);
		cboAlias = new JComboBox<String>();
		cboAlias.setBounds(80, 35, 120, 20);
		container.add(cboAlias);

		label = new JLabel("密　码：");
		label.setBounds(260, 35, 70, 20);
		container.add(label);
		txtStorepass = new JTextField();
		txtStorepass.setBounds(330, 35, 120, 20);
		container.add(txtStorepass);

		label = new JLabel("密匙[key]：");
		label.setBounds(10, 60, 70, 20);
		container.add(label);
		txtKeyFile = new JTextField();
		txtKeyFile.setBounds(80, 60, 370, 20);
		container.add(txtKeyFile);

		txtDebug = new JTextArea();
		txtDebug.setEditable(false);
		JScrollPane scroller = new JScrollPane(txtDebug);
		scroller.setBorder(BorderFactory.createLineBorder(new Color(0xFF0000)));
		scroller.setBounds(10, 90, 460, 200);
		container.add(scroller);

		btnExecute = new JButton();
		btnExecute.setBounds(250, 300, 100, 20);
		btnExecute.setText("导出私钥");
		btnExecute.setEnabled(false);
		container.add(btnExecute);

		txtKeystore.setText("");
		cboAlias.addItem("");
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
				fillJComboBox();
				btnExecute.setEnabled(true);
				txtKeyFile.setText(txtKeystore.getText().trim() + ".key");
			}
		});
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtDebug.setText("");

				String sKeystore = txtKeystore.getText().trim();//证书库路径
				String sStorepass = txtStorepass.getText().trim();//密码
				String sKey = txtKeyFile.getText().trim();//密钥

				Object oItem = cboAlias.getSelectedItem();
				String sAlias = oItem == null ? "" : String.valueOf(oItem);
				try {
					new ExportPrivateKey(new File(sKeystore), //keystore.jks
							"JKS", //
							sStorepass.toCharArray(), //
							sAlias, //
							new File(sKey)//
					).export();
				} catch (Exception e) {
					txtDebug.setText(e.toString());
				}
				btnExecute.setEnabled(false);
				fillJComboBox();
			}
		});
	}

	private void fillJComboBox() {
		cboAlias.removeAllItems();
		cboAlias.addItem("");
		String sKeystore = txtKeystore.getText().trim();//证书库路径
		String sStorepass = txtStorepass.getText().trim();//密码
		if (sKeystore.length() == 0) {
			return;
		}
		FileInputStream fis = null;
		try {
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			char[] password = sStorepass.toCharArray();
			fis = new FileInputStream(sKeystore);
			ks.load(fis, password);
			for (Enumeration<?> en = ks.aliases(); en.hasMoreElements();) {
				cboAlias.addItem(String.valueOf(en.nextElement()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			btnExecute.setEnabled(false);
			txtDebug.setText("\n KeyStore文件错误 \n");
			txtDebug.append(e.toString());
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		cboAlias.setSelectedIndex(0);
	}
}
