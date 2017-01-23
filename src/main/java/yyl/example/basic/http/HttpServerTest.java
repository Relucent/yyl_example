package yyl.example.basic.http;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class HttpServerTest {

	static AtomicBoolean stop = new AtomicBoolean(false);

	public static void runHttpServer() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(1234);
			while (!stop.get()) {
				try (Socket socket = serverSocket.accept()) {
					try (Scanner scanner = new Scanner(socket.getInputStream()); //
							Writer response = new OutputStreamWriter(socket.getOutputStream())) {
						String line = scanner.nextLine();
						System.out.println(line);

						if (line.equals("GET / HTTP/1.1")) {
							response.append("HTTP/1.0 200 OK\r\n");
							response.append("Last-Modified: Sat, 10 Mar 2012 14:42:12 GMT\r\n");
							response.append("Accept-Ranges: bytes\r\n");
							response.append("Content-Type: text/html\r\n");
							response.append("Server: TestServer\r\n");
							response.append("Expires: Sat, 10 Mar 1900 00:00:00 GMT\r\n");
							//response.append(String.format("Content-Length: %d\r\n", nLen));
							response.append("Connection: close\r\n");
							response.append("\r\n");
							response.append("<html>");
							response.append("<head>");
							response.append("<meta charset=\"utf-8\">");
							response.append("<title>hello</title>");
							response.append("</head>");
							response.append("<body>");
							response.append("<h1>hello world</h1>");
							response.append("</body>");
							response.append("</html>");
						} else {
							response.append("HTTP/1.0 404 Not Found\r\n");
							response.append("Connection: close\r\n");
							response.append("\r\n");
						}
						response.flush();
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Container container = frame.getContentPane();
		container.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("hello http");
		JTextArea txtResult = new JTextArea();
		txtResult.setBounds(5, 5, 180, 180);
		txtResult.setEditable(false);
		txtResult.setBackground(Color.BLACK);
		txtResult.setForeground(Color.WHITE);
		container.add(txtResult);
		frame.setSize(new Dimension(200, 200));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("shutdown");
				stop.set(true);
			}
		});
		txtResult.setText("http://localhost:1234");
		runHttpServer();
	}
}
