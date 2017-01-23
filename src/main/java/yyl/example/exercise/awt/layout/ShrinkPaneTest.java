package yyl.example.exercise.awt.layout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ShrinkPaneTest extends JFrame {

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

			ShrinkPaneTest frame = new ShrinkPaneTest();

			frame.setSize(150, 300);
			frame.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame
	 */

	public ShrinkPaneTest() {
		super();
		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ShrinkPane shrinkPane = new ShrinkPane();
		getContentPane().add(shrinkPane);

		shrinkPane.add(new ShrinkItem());
		shrinkPane.add(new ShrinkItem());
		shrinkPane.add(new ShrinkItem());
		shrinkPane.add(new ShrinkItem());
		shrinkPane.add(new ShrinkItem());
	}
}