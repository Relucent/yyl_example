package yyl.example.other.cert;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CertContent1 {

	private Container container;

	private JTextField txtAlias;//别名
	private JTextField txtKeypass;//密钥
	private JTextField txtDnameCN;//名称
	private JTextField txtDnameOU;//组织
	private JTextField txtDnameO;//单位
	private JTextField txtDnameL;//城市
	private JTextField txtDnameBJ;//省份
	private JTextField txtDnameC;//国家
	private JTextField txtKeystore;//路径
	private JTextField txtStorepass;//密码
	private JTextField txtValidity;//时限

	private JTextField txtKeyalg;//算法
	private JTextField txtKeysize;//长度

	private JTextArea txtDebug;
	private JButton btnDisplay;
	private JButton btnExecute;

	private String sCommand;

	/**构造函数*/
	protected CertContent1() {
		initContent();
		registerListener();
	}

	/**获得Container*/
	protected Container getContainer() {
		return container;
	}

	/**初始化界面*/
	private void initContent() {
		container = new Container();
		container.setLayout(null);

		JLabel label;

		label = new JLabel("别名：");
		label.setBounds(10, 10, 70, 20);
		container.add(label);
		txtAlias = new JTextField();
		txtAlias.setBounds(80, 10, 120, 20);
		container.add(txtAlias);

		label = new JLabel("密匙：");
		label.setBounds(260, 10, 70, 20);
		container.add(label);
		txtKeypass = new JTextField();
		txtKeypass.setBounds(330, 10, 120, 20);
		container.add(txtKeypass);

		label = new JLabel("名称：");
		label.setBounds(10, 35, 70, 20);
		container.add(label);
		txtDnameCN = new JTextField();
		txtDnameCN.setBounds(80, 35, 120, 20);
		container.add(txtDnameCN);

		label = new JLabel("组织：");
		label.setBounds(260, 35, 70, 20);
		container.add(label);
		txtDnameOU = new JTextField();
		txtDnameOU.setBounds(330, 35, 120, 20);
		container.add(txtDnameOU);

		label = new JLabel("单位：");
		label.setBounds(10, 60, 70, 20);
		container.add(label);
		txtDnameO = new JTextField();
		txtDnameO.setBounds(80, 60, 120, 20);
		container.add(txtDnameO);

		label = new JLabel("城市：");
		label.setBounds(260, 60, 70, 20);
		container.add(label);
		txtDnameL = new JTextField();
		txtDnameL.setBounds(330, 60, 120, 20);
		container.add(txtDnameL);

		label = new JLabel("省份：");
		label.setBounds(10, 85, 70, 20);
		container.add(label);
		txtDnameBJ = new JTextField();
		txtDnameBJ.setBounds(80, 85, 120, 20);
		container.add(txtDnameBJ);

		label = new JLabel("国家：");
		label.setBounds(260, 85, 70, 20);
		container.add(label);
		txtDnameC = new JTextField();
		txtDnameC.setBounds(330, 85, 120, 20);
		container.add(txtDnameC);

		label = new JLabel("密码：");
		label.setBounds(10, 110, 70, 20);
		container.add(label);
		txtStorepass = new JTextField();
		txtStorepass.setBounds(80, 110, 120, 20);
		container.add(txtStorepass);

		label = new JLabel("时限：");
		label.setBounds(260, 110, 70, 20);
		container.add(label);
		txtValidity = new JTextField();
		txtValidity.setBounds(330, 110, 120, 20);
		container.add(txtValidity);

		label = new JLabel("算法：");
		label.setBounds(10, 135, 70, 20);
		container.add(label);
		txtKeyalg = new JTextField();
		txtKeyalg.setBounds(80, 135, 120, 20);
		container.add(txtKeyalg);

		label = new JLabel("长度：");
		label.setBounds(260, 135, 70, 20);
		container.add(label);
		txtKeysize = new JTextField();
		txtKeysize.setBounds(330, 135, 120, 20);
		container.add(txtKeysize);

		label = new JLabel("路径：");
		label.setBounds(10, 160, 70, 20);
		container.add(label);
		txtKeystore = new JTextField();
		txtKeystore.setBounds(80, 160, 373, 20);
		container.add(txtKeystore);

		txtDebug = new JTextArea();
		txtDebug.setEditable(false);
		JScrollPane scroller = new JScrollPane(txtDebug);
		scroller.setBorder(BorderFactory.createLineBorder(new Color(0xFF0000)));
		scroller.setBounds(10, 185, 460, 105);
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

		txtAlias.setText("myKeystore");//别名
		txtKeypass.setText("changeit");//密钥
		txtDnameCN.setText("localhost");//名称
		txtDnameOU.setText("Inc");//组织
		txtDnameO.setText("dept");//单位
		txtDnameL.setText("bj");//城市
		txtDnameBJ.setText("bj");//省份
		txtDnameC.setText("cn");//国家
		txtStorepass.setText("changeit");//密码
		txtKeystore.setText("C:/my.keystore");//路径
		txtValidity.setText("360");//时限
		txtValidity.setToolTipText("天数");
		txtKeyalg.setText("RSA");//算法
		txtKeysize.setText("1024");//长度
		txtKeysize.setToolTipText("512-1024");
	}

	/**注册监听*/
	private void registerListener() {
		btnDisplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sCommand = getCommand();
				txtDebug.setText("");
				txtDebug.append(sCommand);
			}
		});
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnExecute.setEnabled(false);
				txtDebug.setText("");
				new ExecuteCommand(txtDebug).execute(sCommand,null);
			}
		});
	}

	private String getCommand() {
		String sAlias = txtAlias.getText().trim();//别名
		String sKeypass = txtKeypass.getText().trim();//密钥
		String sDnameCN = txtDnameCN.getText().trim();//名称
		String sDnameOU = txtDnameOU.getText().trim();//组织
		String sDnameO = txtDnameO.getText().trim();//单位
		String sDnameL = txtDnameL.getText().trim();//城市
		String sDnameBJ = txtDnameBJ.getText().trim();//省份
		String sDnameC = txtDnameC.getText().trim();//国家
		String sKeystore = txtKeystore.getText().trim();//路径
		String sStorepass = txtStorepass.getText().trim();//密码
		String sValidity = txtValidity.getText().trim();//时限

		String sKeyalg = txtKeyalg.getText().trim();//
		String sKeysize = txtKeysize.getText().trim();//长度

		StringBuilder sbr = new StringBuilder();
		sbr.append(" keytool -genkey ");
		if (sKeysize.length() > 0) {
			sbr.append(" -keysize ").append(sKeysize).append(" ");
		}
		sbr.append(" -keyalg ").append(sKeyalg).append(" ");
		sbr.append(" -dname ").append("\"");
		sbr.append("CN=").append(sDnameCN).append(",");
		sbr.append("OU=").append(sDnameOU).append(",");
		sbr.append("O=").append(sDnameO).append(",");
		sbr.append("L=").append(sDnameL).append(",");
		sbr.append("ST=").append(sDnameBJ).append(",");
		sbr.append("C=").append(sDnameC).append("\" ");
		sbr.append(" -alias ").append(sAlias).append(" ");
		sbr.append(" -keypass ").append(sKeypass).append(" ");
		sbr.append(" -keystore \"").append(sKeystore).append("\" ");
		sbr.append(" -storepass ").append(sStorepass).append(" ");
		sbr.append(" -validity ").append(sValidity).append(" ");

		if (sKeyalg.length() == 0 || sAlias.length() == 0 || sKeypass.length() == 0 || sDnameCN.length() == 0
				|| sDnameOU.length() == 0 || sDnameO.length() == 0 || sDnameL.length() == 0 || sDnameBJ.length() == 0
				|| sDnameC.length() == 0 || sKeystore.length() == 0 || sStorepass.length() == 0
				|| sValidity.length() == 0) {
			btnExecute.setEnabled(false);
		} else {
			btnExecute.setEnabled(true);
		}

		return sbr.toString();
	}
}
