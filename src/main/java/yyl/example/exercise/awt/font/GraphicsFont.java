//字体类中的三个参数的设置:
//Font(String name, int style, int pointsize)

//当中间的JPanel获得焦点时，可以相应键盘的事件。
//讨论：如何在中间的JPanel中显示所有键入的字符，并且以制定的字体参数显示？

//参考记事本程序的字体设置对话框，自己写一个窗体。

package yyl.example.exercise.awt.font;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings({ "rawtypes", "serial", "unchecked" })
public class GraphicsFont extends JFrame {

	JComboBox fname = new JComboBox();
	JComboBox fstyle = new JComboBox();
	JComboBox fpointsize = new JComboBox();

	myPanel4 p = new myPanel4();

	GraphicsFont() {

		JPanel p2 = new JPanel();
		p2.add(fname);
		p2.add(fstyle);
		p2.add(fpointsize);

		this.getContentPane().add(p2, BorderLayout.NORTH);
		this.getContentPane().add(p, BorderLayout.CENTER);
		p.setComboBox(fname, fstyle, fpointsize);

		p.setFocusable(true);
		p.addKeyListener(p);
		setSize(400, 400);
		setVisible(true);

	}

	public static void main(String[] args) {
		new GraphicsFont();
	}

	static class myPanel4 extends JPanel implements KeyListener {

		char c = ' ';

		public myPanel4() {
			this.setBackground(Color.white);
		}

		JComboBox fname;

		JComboBox fstyle;

		JComboBox fpointsize;

		public void setComboBox(JComboBox ffname, JComboBox ffstyle, JComboBox ffpointsize) {

			fname = ffname;
			fstyle = ffstyle;
			fpointsize = ffpointsize;

			// 设置字体下拉列表框
			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();// 获得本地的图形环境对象
			String[] fontNames = env.getAvailableFontFamilyNames(); // 获得所有可用的字体名称
			for (int i = 0; i < fontNames.length; i++)
				fname.addItem(fontNames[i]);

			// 设置样式下拉列表框
			fstyle.addItem("PLAIN");
			fstyle.addItem("BOLD");
			fstyle.addItem("ITALIC");
			fstyle.addItem("BOLD+ITALIC");

			// 设置尺寸下拉列表框
			for (int i = 16; i < 25; i++)
				fpointsize.addItem(String.valueOf(i));

		}

		public void paint(Graphics g) {

			String a = fname.getSelectedItem().toString();
			String b = fstyle.getSelectedItem().toString();
			int bi = Font.PLAIN;
			if (b.equals("PLAIN")) {

				bi = Font.PLAIN;
			}
			if (b.equals("BOLD")) {
				bi = Font.BOLD;
			}
			if (b.equals("ITALIC")) {
				bi = Font.ITALIC;
			}
			if (b.equals("BOLD+ITALIC")) {
				bi = Font.BOLD + Font.ITALIC;
			}

			String c = fpointsize.getSelectedItem().toString();
			int ci = Integer.parseInt(c);

			g.setFont(new Font(a, bi, ci));
			g.drawString(String.valueOf(c), 100, 100);

		}

		public void keyTyped(KeyEvent e) {
			// TODO: Add your code here
		}

		public void keyPressed(KeyEvent e) {
			// TODO: Add your code here
		}

		public void keyReleased(KeyEvent e) {

			c = e.getKeyChar();
			System.out.println(String.valueOf(c));
			this.repaint();
		}

	}
}