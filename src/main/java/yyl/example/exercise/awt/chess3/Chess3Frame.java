package yyl.example.exercise.awt.chess3;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

class Chess {

	int a;

	int b;

	int c; // 空闲位置

	int[] w = new int[8];

	int[] p = new int[9];

	public Chess() {

		a = 0;
		b = 0;
		c = ~(a | b);

		// 赢的情况数组
		w[0] = 1 << 0 | 1 << 1 | 1 << 2;
		w[1] = 1 << 3 | 1 << 4 | 1 << 5;
		w[2] = 1 << 6 | 1 << 7 | 1 << 8;
		w[3] = 1 << 0 | 1 << 3 | 1 << 6;
		w[4] = 1 << 1 | 1 << 4 | 1 << 7;
		w[5] = 1 << 2 | 1 << 5 | 1 << 8;
		w[6] = 1 << 0 | 1 << 4 | 1 << 8;
		w[7] = 1 << 2 | 1 << 4 | 1 << 6;

		// 优先级数组
		p[0] = 1 << 4;
		p[1] = 1 << 0;
		p[2] = 1 << 2;
		p[3] = 1 << 6;
		p[4] = 1 << 8;
		p[5] = 1 << 1;
		p[6] = 1 << 3;
		p[7] = 1 << 5;
		p[8] = 1 << 7;

	}

	public void reset() {
		a = 0;
		b = 0;
		c = ~(a | b);
	}

	public void Amove(int m) {
		a = a | m;
		c = ~(a | b);
	}

	public void Bmove(int m) {
		b = b | m;
		c = ~(a | b);
	}

	public boolean isAwon() {
		for (int i = 0; i < 8; i++) {

			if ((a & w[i]) == w[i])
				return true;
		}
		return false;
	}

	public boolean isBwon() {
		for (int i = 0; i < 8; i++) {

			if ((b & w[i]) == w[i])
				return true;
		}
		return false;

	}

	public boolean isHeQi() {

		if (!isAwon() && !isBwon()) {
			if ((a | b) == (Math.pow(2.0, 9.0) - 1)) { // A,b都没有赢,且棋盘已满,就是和棋了
				return true;
			} else
				return false;

		} else
			return false;
	}

	// 如果找到了B能能赢的下一步，返回下一步棋，否则返回-1.
	public int BnextWon() {

		for (int i = 0; i < 9; i++) {

			if ((c & (1 << i)) == 1 << i) {
				for (int j = 0; j < 8; j++)
					if ((((1 << i) | b) & w[j]) == w[j])
						return 1 << i;

			}

		}
		return -1;
	}

	// 如果找到了A能能赢的下一步，返回下一步棋，否则返回-1.
	public int AnextWon() {

		for (int i = 0; i < 9; i++) {

			if ((c & (1 << i)) == 1 << i) {
				for (int j = 0; j < 8; j++)
					if ((((1 << i) | a) & w[j]) == w[j])
						return 1 << i;

			}

		}
		return -1;

	}

	// 产生B的下一步
	public int Bnext() {

		int bnext = BnextWon();
		int anext = AnextWon();

		if (bnext != -1) {
			// 如果B能赢，去赢
			return (bnext);
		} else {

			if (anext != -1) {
				// 如果A能赢，去堵
				return (anext);
			} else {

				// 都不能赢，找优先级最高的位置
				for (int i = 0; i < 9; i++) {
					if ((p[i] & c) == p[i]) {
						return p[i];
					}
				}

			}
		}
		return -1;

	}

	// m:只有一个位置为1
	// 函数: 找一个位移表达式m中为1的位置(0-8)
	public int getIndex(int m) {

		for (int i = 0; i < 9; i++) {
			if (Math.pow(2.0, i) == m)
				return i;
		}
		return -1;
	}

}

public class Chess3Frame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	JButton btn[] = new JButton[9];

	boolean flag = true;

	Chess chess = new Chess(); // 裁判

	public Chess3Frame() {
		Container con = this.getContentPane();
		con.setLayout(new GridLayout(3, 3));
		for (int i = 0; i < 9; i++) {
			btn[i] = new JButton();
			btn[i].setActionCommand(String.valueOf(i));
			con.add(btn[i]);
			btn[i].addActionListener(this);
			btn[i].setFont(new Font("Courier", Font.BOLD, 20));
		}
	}

	public void reset() {
		// 表示层的重置
		for (int i = 0; i < 9; i++) {
			btn[i].setText("");
		}
		// 罗基层的重置
		chess.reset();
	}

	// 总是A(人)先走
	public void actionPerformed(ActionEvent e) {

		// A先走
		String s = e.getActionCommand();
		int i = Integer.parseInt(s);

		if (btn[i].getText().equals("")) {

			btn[i].setText("x");
			chess.Amove(1 << i);
			if (chess.isAwon()) {
				JOptionPane.showMessageDialog(this, "A赢了!");
				this.reset();
				return;
			} else {
				if (chess.isHeQi()) {
					JOptionPane.showMessageDialog(this, "和棋!");
					this.reset();
					return;
				}
			}

			// B走
			int bi = chess.Bnext(); // 机器人产生下一步
			chess.Bmove(bi); // 逻辑层

			int index = chess.getIndex(bi);
			btn[index].setText("o"); // 表示层

			if (chess.isBwon()) {
				JOptionPane.showMessageDialog(this, "B赢了!");
				this.reset();
				return;
			} else {
				if (chess.isHeQi()) {
					JOptionPane.showMessageDialog(this, "和棋!");
					this.reset();
					return;
				}
			}
		}
	}

	public static void main(String[] args) {
		Chess3Frame f = new Chess3Frame();
		f.setSize(400, 400);
		f.setVisible(true);
	}

}