package yyl.example.exercise.awt.layout;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ShrinkPane extends JPanel {

	private GridBagConstraints gridData = new GridBagConstraints();

	JPanel bpanel = new JPanel();

	int countCollapse = 0;

	public ShrinkPane() {
		super();
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.BLACK);

		gridData.fill = GridBagConstraints.BOTH;
		gridData.gridwidth = GridBagConstraints.REMAINDER;
		gridData.weightx = 1.0;
	}

	public void add(ShrinkItem shrinkItem) {
		gridData.weighty = 1.0;
		shrinkItem.sp = this;
		this.add(shrinkItem.jp, gridData);
		shrinkItem.setCollapse(true);
	}

	public void add(ShrinkItem shrinkItem, int index) {
		gridData.weighty = 1.0;
		this.add(shrinkItem.jp, gridData, index);
	}

	public void remove(ShrinkItem shrinkItem) {
		shrinkItem.sp = null;
		this.remove(shrinkItem.jp);
	}

	public void collapseLayout(ShrinkItem shrinkItem, boolean bCollapse) {
		int index = this.getComponentZOrder(shrinkItem.jp);
		this.remove(shrinkItem.jp);
		gridData.weighty = bCollapse ? 0.0 : 1.0;
		this.add(shrinkItem.jp, gridData, index);
		countCollapse = bCollapse ? countCollapse + 1 : countCollapse - 1;
		int count = this.getComponentCount();
		this.remove(bpanel);
		if (countCollapse == count) {
			gridData.weighty = 1.0;
			this.add(bpanel, gridData, this.getComponentCount());
		}
		this.validate();
	}
}