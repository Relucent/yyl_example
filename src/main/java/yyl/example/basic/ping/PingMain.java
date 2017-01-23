package yyl.example.basic.ping;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PingMain {

	private static JTextField beginText;
	private static JTextField endText;
	private static JButton btnExecute;
	private static JButton btnCancel;
	private static JTextArea resultText;
	private static volatile Thread thread;
	public static void main(String[] args) throws Throwable {

		JFrame frame = new JFrame();
		Container container = frame.getContentPane();
		container.setLayout(null);
		{
			container.add(addComponent(new JLabel("开始IP："), 10, 10, 60, 20));
			container.add(addComponent(beginText = new JTextField(), 60, 10, 90, 20));
			beginText.setText("192.168.1.1");
		}
		{
			container.add(addComponent(new JLabel("结束IP："), 160, 10, 60, 20));
			container.add(addComponent(endText = new JTextField(), 210, 10, 90, 20));
			endText.setText("192.168.1.254");
		}
		{
			container.add(addComponent(btnExecute = new JButton("PING"), 310, 10, 75, 20));
		}
		{
			container.add(addComponent(btnCancel = new JButton("取消"), 390, 10, 75, 20));
			btnCancel.setEnabled(true);
		}

		{
			container.add(addComponent(new JLabel("结果JSON："), 10, 40, 100, 20));
			JScrollPane scroller = addComponent(new JScrollPane(resultText = new JTextArea()), 10, 60, 460, 350);
			scroller.setBorder(BorderFactory.createLineBorder(new Color(0xFF0000)));
			resultText.setEditable(false);
			container.add(scroller);
		}
		{
			btnExecute.addActionListener(new ActionListener() {
				//点击转换按钮时候触发
				public void actionPerformed(ActionEvent event) {
					(thread = new Thread() {
						@Override
						public void run() {
							btnExecute.setEnabled(false);
							beginText.setEnabled(false);
							endText.setEnabled(false);
							btnCancel.setEnabled(true);
							try {
								long begin = 0;
								long end = 0;
								try {
									begin = getHexIp(beginText.getText());
									end = getHexIp(endText.getText());
								} catch (Exception e) {
									e.printStackTrace();
									resultText.setText("IP地址输出错误，只支持IP4!");
									return;
								}
								if (begin < 0 || end < 0 || begin >= 4294967295L || end >= 4294967295L) {
									resultText.setText("IP地址输出错误，只支持IP4.");
									return;
								}
								if (begin > end) {
									long temp = begin;
									begin = end;
									end = temp;
								}
								if (end - begin > 500) {
									resultText.setText("IP地址区间过大，一次只支持500个IP");
									return;
								}
								final ConcurrentMap<String, Boolean> map = new ConcurrentHashMap<String, Boolean>();
								ExecutorService es = Executors.newFixedThreadPool(20);
								final StringBuilder string = new StringBuilder();

								resultText.setText("PING:");

								final Lock lock = new ReentrantLock();
								for (long i = begin; i <= end; i++) {
									String value = Long.toHexString(i);
									for (; 8 > value.length();) {
										value = "0" + value;
									}
									final String ip = value = new StringBuilder()//
											.append(Integer.parseInt(value.substring(0, 2), 16)).append(".")//
											.append(Integer.parseInt(value.substring(2, 4), 16)).append(".")//
											.append(Integer.parseInt(value.substring(4, 6), 16)).append(".")//
											.append(Integer.parseInt(value.substring(6, 8), 16)).toString();//
									es.execute(new Runnable() {
										@Override
										public void run() {
											Boolean result = ping(ip);
											map.put(ip, result);
											try {
												lock.lock();
												string.append("ping:").append(ip).append("|").append(result).append("\n");
												resultText.setText(string.toString());
											} finally {
												lock.unlock();
											}
										}
									});
								}
								es.shutdown();
								try {
									es.awaitTermination(((end - begin) * 5) / 10 + 20, TimeUnit.SECONDS);
								} catch (InterruptedException e) {
									Thread.currentThread().interrupt();
								}

								TreeMap<String, Boolean> tmap = new TreeMap<String, Boolean>();
								tmap.putAll(map);
								string.delete(0, string.length());
								for (Map.Entry<String, Boolean> entry : map.entrySet()) {
									string.append(entry.getKey()).append("	|").append(entry.getValue()).append("\n");
									resultText.setText(string.toString());
								}
							} catch (Exception e) {
								resultText.setText(e.toString());
							} finally {
								btnExecute.setEnabled(true);
								beginText.setEnabled(true);
								endText.setEnabled(true);
								btnCancel.setEnabled(true);
								thread = null;
							}
						}
					}).start();
				};
			});
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					Thread vThread = thread;
					if (vThread != null) {
						vThread.interrupt();
					}
				}
			});
		}

		frame.setBounds(100, 100, 485, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Ping");
		frame.setResizable(false);
		frame.setVisible(true);
	}
	private static <T extends Container> T addComponent(T component, int x, int y, int width, int height) {
		component.setBounds(x, y, width, height);
		return component;
	}
	private static long getHexIp(String value) {
		String[] array = value.split("\\.");
		StringBuilder sbr = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			String part = Integer.toHexString(Integer.parseInt(array[i], 10));
			if (part.length() < 2) {
				sbr.append("0");
			}
			sbr.append(part);
		}
		return Long.parseLong(sbr.toString(), 16);
	}

	private static boolean ping(String ip) {
		boolean result = false;// 结果
		Runtime runtime = Runtime.getRuntime(); // 获取当前程序的运行进对象
		Process process = null; // 声明处理类对象
		String line = null; // 返回行信息
		InputStream is = null; // 输入流
		BufferedReader br = null;
		try {
			process = runtime.exec("ping -w 3000 " + ip); // PING
			is = process.getInputStream(); // 实例化输入流
			InputStreamReader isr = new InputStreamReader(is);// 把输入流转换成字节流
			br = new BufferedReader(isr);// 从字节中读取文本
			while ((line = br.readLine()) != null) {
				if (line.contains("TTL")) {
					result = true;
					break;
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			try {
				is.close();
			} catch (Exception e) {

			}
			try {
				br.close();
			} catch (Exception e) {

			}
		}
		return result;
	}
}
