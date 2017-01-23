package yyl.example.other.excelparse;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class ExcelBuilderUI {

	JFrame frame = new JFrame();
	JTextField txtExcel;
	JTextField txtOutFolder;
	JTextField txtTemplet;

	JButton btnExcel;
	JButton btnOutFolder;
	JButton btnTemplet;

	JTextArea txtDebug;
	JButton btnConfirm;
	JButton btnCancel;

	/**
	 * 构造函数
	 */
	public ExcelBuilderUI() {
		initContainer();
		registerListener();
		frame.setBounds(100, 100, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("XLS文件导HTML");
		frame.setResizable(false);
		frame.setVisible(true);
	}

	/**
	 * 初始化界面
	 */
	private void initContainer() {

		Container container = frame.getContentPane();
		container.setLayout(null);

		JLabel label;

		label = new JLabel("xls文件夹：");
		label.setBounds(10, 10, 100, 20);
		container.add(label);

		txtExcel = new JTextField();
		txtExcel.setBounds(100, 10, 245, 20);
		txtExcel.setEditable(false);
		container.add(txtExcel);

		btnExcel = new JButton();
		btnExcel.setBounds(350, 10, 120, 20);
		btnExcel.setText("选择xls文件夹");
		container.add(btnExcel);

		label = new JLabel("输出文件：");
		label.setBounds(10, 35, 100, 20);
		container.add(label);

		txtOutFolder = new JTextField();
		txtOutFolder.setBounds(100, 35, 245, 20);
		txtOutFolder.setEditable(false);
		container.add(txtOutFolder);

		btnOutFolder = new JButton();
		btnOutFolder.setBounds(350, 35, 120, 20);
		btnOutFolder.setText("输出文件夹");
		container.add(btnOutFolder);

		label = new JLabel("模版文件：");
		label.setBounds(10, 60, 100, 20);
		container.add(label);

		txtTemplet = new JTextField();
		txtTemplet.setBounds(100, 60, 245, 20);
		txtTemplet.setEditable(false);
		container.add(txtTemplet);

		btnTemplet = new JButton();
		btnTemplet.setBounds(350, 60, 120, 20);
		btnTemplet.setText("选择模版文件");
		container.add(btnTemplet);

		txtDebug = new JTextArea();
		JScrollPane scroller = new JScrollPane(txtDebug);
		//	scroller.setBorder(BorderFactory.createLineBorder(new Color(0xFF0000)));
		scroller.setBounds(10, 85, 460, 250);
		container.add(scroller);

		btnConfirm = new JButton();
		btnConfirm.setBounds(130, 340, 100, 20);
		btnConfirm.setText("开始导出");
		container.add(btnConfirm);

		btnConfirm.setEnabled(false);

		btnCancel = new JButton();
		btnCancel.setBounds(250, 340, 100, 20);
		btnCancel.setText("清空控制台");
		container.add(btnCancel);

	}

	private void registerListener() {

		btnExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

				chooser.setFileFilter(new FileFilter() {
					public boolean accept(File f) {
						return (f.isDirectory() || f.getName().endsWith(".xls"));
					}
					public String getDescription() {
						return null;
					}
				});
				int returnVal = chooser.showOpenDialog(btnExcel);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					txtExcel.setText(chooser.getSelectedFile().getAbsolutePath());
				}
				validate();
			}

		});
		btnOutFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = chooser.showOpenDialog(btnOutFolder);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					txtOutFolder.setText(chooser.getSelectedFile().getAbsolutePath());
				}
				validate();
			}

		});
		btnTemplet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = chooser.showOpenDialog(btnTemplet);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					txtTemplet.setText(chooser.getSelectedFile().getAbsolutePath());
				}
				validate();
			}

		});

		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread thread = new Thread(new Runnable() {
					public void run() {
						btnConfirm.setEnabled(false);
						ExcelBuilderMethod.outExcel(txtExcel.getText(), txtOutFolder.getText(), txtTemplet.getText(), txtDebug);
						btnConfirm.setEnabled(true);
					}
				});
				thread.setDaemon(true);
				thread.start();
			}

		});

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtDebug.setText("");
			}

		});
	}

	private void validate() {
		String templetPath = txtTemplet.getText();
		String outFolderPath = txtOutFolder.getText();
		String excelPath = txtExcel.getText();
		btnConfirm.setEnabled((templetPath != null && templetPath.trim().length() > 0 && outFolderPath != null && outFolderPath.trim().length() > 0 && excelPath != null && excelPath.trim().length() > 0));

	}
}
