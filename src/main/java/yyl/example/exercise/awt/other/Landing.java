package yyl.example.exercise.awt.other;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Landing extends JFrame {
	JLabel jLabel1 = new JLabel();
	JTextField txtName = new JTextField();
	JLabel jLabel2 = new JLabel();
	JPasswordField txtPass = new JPasswordField();
	JButton jButton1 = new JButton();
	JButton jButton2 = new JButton();

	public Landing() {
		try {
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		getContentPane().setLayout(null);
		Draw d = new Draw();

		jLabel1.setText("用户名");
		jLabel1.setBounds(new Rectangle(40, 55, 82, 40));
		txtName.setBounds(new Rectangle(103, 55, 131, 27));

		jLabel2.setText("密码");
		jLabel2.setBounds(new Rectangle(40, 88, 51, 30));
		txtPass.setBounds(new Rectangle(103, 93, 133, 27));

		jButton1.setText("登陆");
		jButton1.setBounds(new Rectangle(24, 150, 112, 37));

		jButton2.setText("退出");
		jButton2.setBounds(new Rectangle(146, 151, 112, 37));

		setTitle("管理系统");
		setSize(300, 250);
		setLocation(100, 100);
		setResizable(false);

		d.setBounds(new Rectangle(0, 0, 300, 50));
		getContentPane().add(d);
		getContentPane().add(jLabel1);
		getContentPane().add(jLabel2);
		getContentPane().add(jButton1);
		getContentPane().add(jButton2);
		getContentPane().add(txtName);
		getContentPane().add(txtPass);

		// 退出按扭
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// 登陆按钮
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pwd = String.copyValueOf(txtPass.getPassword());
				if (txtName.getText() == null || txtName.getText().trim().equals("") || pwd.equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "用户名或密码不能为空!", "错误", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
		});

	}

	public static void main(String[] args) {
		new Landing().setVisible(true);
	}

	static class Draw extends JPanel {
		public void paintComponent(Graphics g) {
			Image image = new ImageIcon("2.JPG").getImage();
			g.drawImage(image, 0, 0, this);
		}
	}
}
