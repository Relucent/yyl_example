package yyl.example.exercise.awt.other;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

/**
 * 模拟的命令提示窗口
 */
public class Command {

	private JTextArea txtDebug = null;
	private Caret caret = null;
	private Document doc = null;
	private OutputStream outputStream;
	private int execOffset = 0;

	public Command() throws IOException {
		initContainer();
		initCommand();
		registerListener();
	}

	private void initContainer() {
		JFrame frame = new JFrame();
		Container container = frame.getContentPane();
		container.setLayout(new BorderLayout());
		txtDebug = new JTextArea();
		txtDebug.setEditable(true);
		JScrollPane scroller = new JScrollPane(txtDebug);
		scroller.setBorder(BorderFactory.createLineBorder(new Color(0xFF0000)));
		txtDebug.setBackground(new Color(0x000000));
		txtDebug.setSelectedTextColor(new Color(0x000000));
		txtDebug.setSelectionColor(new Color(0xFFFFFF));
		txtDebug.setCaretColor(new Color(0xFFCC00));
		txtDebug.setForeground(new Color(0xFFFFFF));
		doc = txtDebug.getDocument();
		caret = txtDebug.getCaret();
		container.add(scroller, BorderLayout.CENTER);
		frame.setBounds(100, 100, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Command");
		frame.setResizable(true);
		frame.setVisible(true);

	}

	private void registerListener() {
		txtDebug.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 8 && execOffset >= doc.getLength()) {
					e.setKeyCode(27);
				}
				if (e.getKeyCode() == 10 && execOffset < doc.getLength()) {
					try {
						String str = doc.getText(execOffset, doc.getLength() - execOffset);
						exec(str + "\n");
					} catch (BadLocationException ex) {
						ex.printStackTrace();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}

			public void keyTyped(KeyEvent e) {
				// System.out.println(e.getKeyChar());
			}
		});
		doc.addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
			}

			public void insertUpdate(DocumentEvent e) {
			}

			public void removeUpdate(DocumentEvent e) {
			}
		});

		txtDebug.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				if (execOffset > caret.getMark() || execOffset > caret.getDot()) {
					caret.setDot(execOffset);
				}
			}
		});

	}

	private void print(String str, boolean flag) {
		try {
			execOffset = doc.getLength() + str.length();
			doc.insertString(doc.getLength(), str, null);
			txtDebug.setCaretPosition(doc.getLength());
		} catch (BadLocationException e) {
		}
	}

	private void exec(String sCommand) throws IOException {
		outputStream.write(sCommand.getBytes());
		outputStream.flush();
	}

	private void initCommand() throws IOException {
		Process process = Runtime.getRuntime().exec("cmd");
		final InputStream inputStream = process.getInputStream();
		final InputStream errorStream = process.getErrorStream();
		outputStream = process.getOutputStream();
		Thread oInputThread = new Thread() {
			public void run() {
				try {
					byte[] buffer = new byte[1024];
					int count = 0;
					while ((count = inputStream.read(buffer)) > -1) {
						print(new String(buffer, 0, count), true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		oInputThread.setDaemon(true);
		oInputThread.start();

		Thread oErrorThread = new Thread() {
			public void run() {
				try {
					byte[] buffer = new byte[1024];
					int count = 0;
					while ((count = errorStream.read(buffer)) > -1) {
						print(new String(buffer, 0, count), false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		oErrorThread.setDaemon(true);
		oErrorThread.start();
	}

	public static void main(String[] args) {
		try {
			new Command();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}