package yyl.example.exercise.awt.other;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;


public class FunErrorBarTest extends JFrame implements Runnable, WindowListener,
		KeyListener {

	private static final long serialVersionUID = 1L;

	JProgressBar progressBar = null;

	int iRun = 0;

	public static void main(String args[]) {
		try {
			Thread thread = new Thread(new FunErrorBarTest());
			thread.setDaemon(true);
			thread.start();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		passFileName();
		while (iRun < 10000) {
			try {
				Thread.sleep(20);
				Thread.sleep((long) Math.random() * 10);
				if (iRun > 8000) {
					Thread.sleep((long) Math.random() * 100);
				}
				progressBar.setValue(iRun);
				progressBar.setString(iRun / 100 + "%");
				labelGallow.setText(filename);
				iRun++;
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public FunErrorBarTest() {
		super();
		getContentPane().setLayout(null);
		progressBar = new JProgressBar();
		progressBar.setBounds(22, 55, 312, 24);
		progressBar.setMaximum(10000);
		progressBar.setMinimum(0);
		progressBar.setStringPainted(true);
		getContentPane().add(progressBar);
		setVisible(true);
		setBounds(100, 100, 366, 152);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		JLabel label = new JLabel();
		label.setBounds(22, 22, 312, 27);
		label.setText("ϵͳ������C�����ڱ�ɾ��");
		getContentPane().add(label);

		//		setIconImage(Toolkit.getDefaultToolkit().createImage(getClass().getResource("e_synch_nav.gif")));

		setTitle("����");
		setResizable(false);
		addWindowListener(this);
		addKeyListener(this);
		setAlwaysOnTop(true);
		setVisible(true);

		labelGallow = new JLabel();
		labelGallow.setText("");
		labelGallow.setBounds(22, 85, 312, 15);
		getContentPane().add(labelGallow);
	}

	private String filename;

	private JLabel labelGallow;

	private void passFileName() {
		try {
			Thread thread = new Thread() {

				public void run() {
					pass(new File("C:\\"));
				}

				private void pass(File f) {
					ArrayList<File> list, temp;
					list = new ArrayList<File>();
					list.add(f);
					while (list.size() > 0) {
						temp = new ArrayList<File>();
						for (int i = 0; i < list.size(); i++) {
							f = (File) list.get(i);
							File[] cs = f.listFiles();
							for (int j = 0; j < cs.length; j++) {
								File fc = cs[j];
								if (fc.isDirectory()) {
									temp.add(fc);
								}
								filename = fc.getAbsolutePath();
							}
						}
						list = temp;
					}
				}
			};
			thread.setDaemon(true);
			thread.start();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void windowIconified(WindowEvent arg0) {
		setExtendedState(JFrame.NORMAL);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_K) {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}

	public void windowActivated(WindowEvent windowevent) {
	}

	public void windowClosed(WindowEvent windowevent) {
	}

	public void windowClosing(WindowEvent windowevent) {
	}

	public void windowDeactivated(WindowEvent windowevent) {
	}

	public void windowDeiconified(WindowEvent windowevent) {
	}

	public void windowOpened(WindowEvent windowevent) {
	}

	public void keyReleased(KeyEvent keyevent) {
	}

	public void keyTyped(KeyEvent keyevent) {
	}
}
