package yyl.example.other.wx2013;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 一个解析文件的工具类
 * @author YaoYL
 * @version 2013-04-08 10:52
 */
public class ToolMain {

	// 正则表达式(表示逗号)
	private final static Pattern vDelimiterPattern = Pattern.compile("[\\,]");

	/**
	 * 主函数
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Container container = frame.getContentPane();
		JTabbedPane tabbedPane = new JTabbedPane();
		container.add(tabbedPane);
		tabbedPane.add("数据转换1", new Content1().getContainer());
		frame.setBounds(100, 100, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("wxx计算器20030404");
		frame.setResizable(false);
		frame.setVisible(true);
	}

	/**
	 * 功能界面
	 */
	private static class Content1 {
		private Container container;
		private JTextField txtFile;// 文件路径(输入框)
		private JButton btnFile;// 文件路径(按钮)
		private JTextField txtDefined;// 定义列(输入框)
		private JTextField txtTrColumn;// 转换列(输入框)
		private JTextArea txtDebug;// 日志输出(文本域)
		private JButton btnClear;// 清空日志(按钮)
		private JButton btnTransform;// 转换(按钮)

		// 用来记录日志的字符串缓存
		private StringBuilder sBuilder = new StringBuilder();

		/** 构造函数 */
		protected Content1() {
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

			addComponent(new JLabel("数据文件路径："), 10, 10, 100, 20);
			addComponent(txtFile = new JTextField(), 100, 10, 300, 20);
			addComponent(btnFile = new JButton("浏览"), 400, 10, 70, 20);
			addComponent(new JLabel("列定义："), 10, 45, 100, 20);
			addComponent(txtDefined = new JTextField(), 100, 45, 300, 20);

			addComponent(new JLabel("转换列："), 10, 70, 100, 20);
			addComponent(txtTrColumn = new JTextField(), 100, 70, 300, 20);

			addComponent(btnTransform = new JButton("开始进行转换"), 300, 120, 150, 20);
			addComponent(btnClear = new JButton("清空日志"), 20, 120, 150, 20);

			(txtDebug = new JTextArea()).setEditable(false);
			JScrollPane scroller = addComponent(new JScrollPane(txtDebug), 10, 150, 460, 150);
			scroller.setBorder(BorderFactory.createLineBorder(new Color(0xFF0000)));
			container.add(scroller);

			txtFile.setText("");
			txtDebug.setText("");
			txtDefined.setText("11,21,29,39,51,59,69");
			txtTrColumn.setText("0,4");
		}

		/**
		 * 将控件添加到面板上
		 * @param component 控件
		 * @param x 控件X坐标
		 * @param y 控件Y坐标
		 * @param width 控件宽度
		 * @param height 控件高度
		 * @return 控件
		 */
		private <T extends Container> T addComponent(T component, int x, int y, int width, int height) {
			component.setBounds(x, y, width, height);
			container.add(component);
			return component;
		}

		/** 注册监听 */
		private void registerListener() {
			btnFile.addActionListener(new ActionListener() {
				// 点击文件按钮时候触发
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser chooser = new JFileChooser();
					chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					int returnVal = chooser.showOpenDialog(btnFile);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						txtFile.setText(chooser.getSelectedFile().getAbsolutePath());
					}
				}
			});

			btnClear.addActionListener(new ActionListener() {
				// 点击清空日志按钮时候触发
				public void actionPerformed(ActionEvent arg0) {
					clearLog();
				}
			});

			btnTransform.addActionListener(new ActionListener() {
				// 点击转换按钮时候触发
				public void actionPerformed(ActionEvent arg0) {
					btnTransform.setEnabled(false);
					doExecuteTransform(txtFile.getText());
					btnTransform.setEnabled(true);
				}
			});
		}

		/**
		 * 执行数据转换
		 * @param fileName 文件路径
		 */
		private void doExecuteTransform(final String fileName) {

			// 获得文件后缀
			int sp = fileName.lastIndexOf(".");
			final String ext = sp != -1 ? "." + fileName.substring(sp) : "";

			// 获得文件解析器
			final CPart oCPart = CPart.parse(txtDefined.getText());

			// 解析器为空
			if (oCPart == null) {
				appendLog("<=Column definition is incorrectly formatted=>");
			}

			// 获得需要转换的列索引
			final int[] iTrColumns = CPart.parseColumns(txtTrColumn.getText().trim());
			if (iTrColumns == null) {
				appendLog("<=Computed column error=>");
			}

			createStartThread(new Runnable() {

				int rowIndex = 0;

				public void run() {
					try {

						appendLog("==========Run==========\n");

						if (!new File(fileName).isFile()) {
							appendLog("<=File does not exist=>");
							return;
						}

						BufferedReader br = null;
						BufferedWriter bw = null;
						try {
							br = new BufferedReader(new FileReader(fileName));
							bw = new BufferedWriter(new FileWriter(fileName + "_" + System.currentTimeMillis() + ext));

							String line = br.readLine();

							while (line != null) {
								rowIndex++;

								oCPart.compile(line);
								for (int column : iTrColumns) {
									oCPart.calculate(column);
								}

								oCPart.writer(bw);
								bw.append(" \r\n");
								if (rowIndex % 1000 == 0) {
									appendLog(String.valueOf(rowIndex));
								}
								line = br.readLine();
							}

							appendLog(String.valueOf(rowIndex));
							appendLog("==========Done==========\n\n");
						} catch (Exception e) {
							appendLog(toErrString(e));

						} finally {
							close(br);
							close(bw);
						}
					} catch (Exception e) {

					}
				}
			});
		}

		/**
		 * 记录日志
		 * @param log 日志内容
		 */
		private void appendLog(String log) {
			sBuilder.append(log).append("\n");
			txtDebug.setText(sBuilder.toString());
		}

		/**
		 * 清空日志
		 */
		private void clearLog() {
			sBuilder = new StringBuilder();
			txtDebug.setText(sBuilder.toString());
		}

		/**
		 * 创建线程
		 * @param runnable 运行器
		 */
		private static void createStartThread(Runnable runnable) {
			Thread oThread = new Thread(runnable);
			oThread.setDaemon(true);
			oThread.start();
		}

	}

	/**
	 * 字符串分隔解析器(/split/)
	 */
	private static class CPart {
		private int[] splits;
		private String[] parts;

		/**
		 * 分隔的列序号
		 * @param splits 分隔的列序号数组
		 */
		private CPart(int[] splits) {
			this.splits = splits;
			parts = new String[splits.length + 1];
		}

		/**
		 * 预编译一行(将一行的数据写到parts中,按照分隔索引分隔)
		 * @param line 一行字符串
		 */
		public void compile(String line) {
			int begin = 0, index = 0;
			for (int point : splits) {
				parts[index] = substring(line, begin, point);
				begin = point;
				index++;
			}
			parts[index] = substring(line, begin, line.length());
		}

		/**
		 * 计算每段数据(转换数据)
		 * @param column 列索引
		 */
		public void calculate(int column) {
			if (column > parts.length) {
				return;
			}
			String part = parts[column];
			int length = part.length();
			part = doCalculate(part);// 转换数据
			StringBuilder sbr = new StringBuilder();
			for (int i = length - part.length(); i > 0; i--) {
				sbr.append(" ");// 字符串如果不够长度前面补充空格
			}
			sbr.append(part);
			parts[column] = sbr.toString();// 将计算后的结果回写到parts中
		}

		public void writer(BufferedWriter bw) throws IOException {
			for (String part : parts) {
				bw.write(part);
			}
		}

		/**
		 * 转换数据
		 * @param num 一个数字
		 * @return 转换后的
		 */
		private String doCalculate(String num) {
			// 0
			num = num.trim();
			if (num.length() == 1) {
				return num;
			}
			// other
			String part0 = num.substring(0, 1);// 第一个字符
			String part1 = num.substring(1);// 之后的字符
			long value = Long.parseLong(part1, 10);// 将第一个字符之后的数据转换为整形
			value = (value - 2001) * 20 + 200000;//
			return part0 + value;// 添加第一个字符
		}

		/**
		 * 截取字符串
		 * @param str 需要截取的字符串
		 * @param start 截取的开头索引
		 * @param end 截取的结束索引
		 * @return 截取后的字符串
		 */
		private static String substring(String str, int start, int end) {
			if (str == null)
				return "";
			if (end < 0)
				end = str.length() + end;
			if (start < 0)
				start = str.length() + start;
			if (end > str.length())
				end = str.length();
			if (start > end)
				return "";
			if (start < 0)
				start = 0;
			if (end < 0)
				end = 0;
			return str.substring(start, end);
		}

		/**
		 * 获得分隔解析器
		 * @param defined 定义的字符串(用逗号分隔的字符串)
		 * @return 分隔解析器
		 */
		public final static CPart parse(String defined) {
			try {
				String[] cs = vDelimiterPattern.split(defined.trim());// 获得逗号分隔的每段文本
				int[] splits = new int[cs.length];
				for (int i = 0, I = cs.length; i < I; i++) {
					splits[i] = Integer.valueOf(cs[i]);
				}
				return new CPart(splits);
			} catch (Exception e) {
				return null;
			}
		}

		public final static int[] parseColumns(String sColumns) {
			try {
				String[] trColumns = vDelimiterPattern.split(sColumns);
				final int[] iTrColumns = new int[trColumns.length];
				for (int i = 0, I = trColumns.length; i < I; i++) {
					iTrColumns[i] = Integer.parseInt(trColumns[i]);
				}
				return iTrColumns;
			} catch (Exception e) {
				return null;
			}
		}
	}

	private static String toErrString(Exception e) {
		StringWriter err = null;
		try {
			e.printStackTrace(new PrintWriter(err = new StringWriter()));
			return err.toString();
		} catch (Exception x) {
			return e.toString();
		} finally {
			close(err);
		}
	}

	private static void close(Reader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void close(Writer writer) {
		if (writer != null) {
			try {
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
