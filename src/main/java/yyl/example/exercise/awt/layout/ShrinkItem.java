package yyl.example.exercise.awt.layout;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ShrinkItem {

	ShrinkPane sp;

	GridBagConstraints gridData = new GridBagConstraints();

	GridBagLayout gridBagLayout = new GridBagLayout();

	JPanel jp = new JPanel();

	JButton button = new JButton();

	JPanel container = new JPanel();

	boolean bCollapse;

	public ShrinkItem() {
		super();

		gridData = new GridBagConstraints();
		gridData.weightx = 1.0;
		gridData.fill = GridBagConstraints.BOTH;
		gridData.gridwidth = GridBagConstraints.REMAINDER;

		jp.setLayout(gridBagLayout);
		container.setBackground(Color.RED);

		button.setText("0");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				setCollapse(!bCollapse);
			}
		});

		jp.setBackground(Color.BLACK);
		gridData.weighty = 0.0;

		gridBagLayout.setConstraints(button, gridData);
		jp.add(button);

		gridData.weighty = 1.0;
		gridData.fill = GridBagConstraints.BOTH;
		jp.add(container, gridData);
	}

	public void setText(String text) {
		button.setText(text);
	}

	public void setCollapse(boolean bCollapse) {
		this.bCollapse = bCollapse;
		if (bCollapse) {
			jp.remove(container);
			gridData.weighty = 0.0;
			sp.collapseLayout(this, bCollapse);
		}
		else {
			gridData.weighty = 1.0;
			jp.add(container, gridData);
			sp.collapseLayout(this, bCollapse);
		}
	}
}