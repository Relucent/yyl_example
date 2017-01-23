package yyl.example.basic.nio.readpage;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 用于对数据文件部分数据片段进行转换
 * @author YaoYL
 * @version 2013-04-08 10:52
 */
public class SocketHttp1_0Main {

	/**
	 * 主函数
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Container container = frame.getContentPane();
		container.add(new Content1().getContainer());
		frame.setBounds(100, 100, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("NIO-TEST");
		frame.setResizable(false);
		frame.setVisible(true);
	};

	/**
	 * 功能界面
	 */
	private static class Content1 {

		private Container container;
		private JTextField txtIp;
		private JTextField txtPort;
		private JTextField txtCharset;
		private JTextArea txtWrite;
		private JTextArea txtRead;
		private JButton btnSend;

		/** 构造函数 */
		protected Content1() {
			initContent();
			//			registerListener();
		}

		/** 获得Container */
		protected Container getContainer() {
			return container;
		}

		/** 初始化界面 */
		private void initContent() {
			container = new Container();
			container.setLayout(null);

			addComponent(new JLabel("服务器IP："), 10, 10, 80, 20);
			addComponent(txtIp = new JTextField(), 80, 10, 100, 20);
			addComponent(new JLabel("端口："), 190, 10, 50, 20);
			addComponent(txtPort = new JTextField(), 250, 10, 100, 20);

			addComponent(new JLabel("字符集："), 10, 40, 80, 20);
			addComponent(txtCharset = new JTextField(), 80, 40, 100, 20);

			addComponent(btnSend = new JButton("发送"), 370, 10, 100, 20);

			{
				addComponent(new JLabel("发送内容："), 10, 65, 80, 20);
				(txtWrite = new JTextArea()).setEditable(true);
				JScrollPane scroller = addComponent(new JScrollPane(txtWrite), 10, 90, 460, 100);
				scroller.setBorder(BorderFactory.createLineBorder(new Color(0x0000FF)));
				container.add(scroller);
			}
			{
				addComponent(new JLabel("接受内容："), 10, 200, 80, 20);
				(txtRead = new JTextArea()).setEditable(false);
				JScrollPane scroller = addComponent(new JScrollPane(txtRead), 10, 220, 460, 100);
				scroller.setBorder(BorderFactory.createLineBorder(new Color(0xFF0000)));
				container.add(scroller);
			}

			txtIp.setText("www.google.com");
			txtPort.setText("80");
			txtWrite.setText("GET " + "/ HTTP/1.0" + "\r\n\r\n");
			txtCharset.setText("UTF-8");

			btnSend.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					SocketChannel channel = null;
					try {
						String charsetMame = txtCharset.getText();
						String ip = txtIp.getText();
						int port = Integer.parseInt(txtPort.getText());
						String write = txtWrite.getText();
						String responseText = request(ip, port, write, charsetMame);
						txtRead.setText(responseText);
					} catch (Exception e) {
						e.printStackTrace();
						txtRead.setText(e.toString());
					} finally {
						if (channel != null) {
							try {
								channel.close();
							} catch (IOException e) {
							}
						}
					}
				}
			});
		}

		private <T extends Container> T addComponent(T component, int x, int y, int width, int height) {
			component.setBounds(x, y, width, height);
			container.add(component);
			return component;
		}
	}

	//XXX
	public static String request(String ip, int port, String write, String charsetMame) throws Exception {
		SocketChannel channel = null;
		try {
			Charset charset = Charset.forName(charsetMame);
			InetSocketAddress socketAddress = new InetSocketAddress(ip, port);
			channel = SocketChannel.open(socketAddress);
			channel.write(charset.encode(write));
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			StringBuilder builder = new StringBuilder();
			while (channel.read(buffer) != -1) {
				buffer.flip();
				builder.append(charset.decode(buffer).toString());
				buffer.clear();
			}
			return builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (channel != null) {
				try {
					channel.close();
				} catch (IOException e) {
				}
			}
		}
	}

}
