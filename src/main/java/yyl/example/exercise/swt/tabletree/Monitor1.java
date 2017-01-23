package yyl.example.exercise.swt.tabletree;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

public class Monitor1 {

	private Text text_3;

	private Tree tableTree;

	private Text text_2;

	private Text text_1;

	private Text text;

	protected Shell shell;

	private static final int NUM = 4;

	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Monitor1 window = new Monitor1();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window
	 */
	public void open() {
		final Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	/**
	 * Create contents of the window
	 */
	protected void createContents() {
		shell = new Shell();
		//	shell.setRedraw(true);
		//		shell.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		//		shell.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
		shell.setSize(772, 663);
		shell.setText("Shell");

		final Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 10, 339, 609);

		text = new Text(composite, SWT.BORDER);
		text.setBounds(5, 25, 329, 286);

		final Label label = new Label(composite, SWT.NONE);
		label.setText("Label:1");
		label.setBounds(5, 5, 84, 19);

		final Label label_5 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_5.setBounds(5, 315, 324, 12);

		final Label label_6 = new Label(composite, SWT.NONE);
		label_6.setText("Label:2");
		label_6.setBounds(15, 333, 59, 12);

		final Button button = new Button(composite, SWT.NONE);
		button.setText("Button");
		button.setBounds(5, 577, 76, 22);

		text_3 = new Text(composite, SWT.BORDER);
		text_3.setBounds(5, 351, 329, 220);

		final Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(368, 10, 386, 609);

		final Label label_1 = new Label(composite_1, SWT.NONE);
		label_1.setText("label1");
		label_1.setBounds(10, 10, 268, 12);

		final Label label_2 = new Label(composite_1, SWT.NONE);
		label_2.setText("label2");
		label_2.setBounds(10, 35, 56, 20);

		text_1 = new Text(composite_1, SWT.BORDER);
		text_1.setBounds(80, 30, 168, 23);

		final Label label_3 = new Label(composite_1, SWT.NONE);
		label_3.setText("label3");
		label_3.setBounds(10, 65, 56, 20);

		text_2 = new Text(composite_1, SWT.BORDER);
		text_2.setBounds(80, 65, 168, 25);

		final Label label_4 = new Label(composite_1, SWT.NONE);
		label_4.setText("label4");
		label_4.setBounds(10, 100, 70, 20);

		tableTree = new Tree(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
		tableTree.setBounds(10, 126, 366, 473);
		freshTableTree(tableTree);

		new DispelTreeEditorBug(tableTree, new String[] { "EDITOR_3" }).refurbish();

		final Button button_1 = new Button(composite_1, SWT.CHECK);
		button_1.setText("CHECK");
		button_1.setBounds(284, 8, 93, 16);
	}

	public void freshTableTree(Tree tableTree) {
		tableTree.setHeaderVisible(true);
		tableTree.setLinesVisible(true);

		new TreeColumn(tableTree, SWT.LEFT).setText("Column-0");
		new TreeColumn(tableTree, SWT.LEFT).setText("Column-1");
		new TreeColumn(tableTree, SWT.LEFT).setText("Column-2");
		new TreeColumn(tableTree, SWT.LEFT).setText("Column-3");
		for (int i = 0; i < NUM; i++) {
			TreeItem parent = new TreeItem(tableTree, SWT.NONE);
			parent.setText(0, "#" + (i + 1));
			parent.setText(1, "");
			parent.setText(2, "");
			for (int j = 0; j < 10; j++) {
				TreeItem child = new TreeItem(parent, SWT.NONE);
				child.setText(0, "0");
				child.setText(1, "1");
				child.setText(2, "2");
				child.setText(3, "3");
				TreeEditor editor2 = createTreeEditor(tableTree, SWT.RIGHT);
				Button button2 = new Button(tableTree, SWT.CHECK);
				child.setData("EDITOR_3", editor2);
				editor2.setEditor(button2, child, 3);
				button2.setSelection(true);
				button2.setEnabled(true);
			}
			parent.setExpanded(true);
			// tableTree.setTopItem(parent);
		}

		TreeColumn[] columns = tableTree.getColumns();
		for (int i = 0, n = columns.length; i < n; i++) {
			columns[i].pack();
		}

	}

	private TreeEditor createTreeEditor(Tree tree, int horizontalAlignment) {
		TreeEditor oTreeEditor = new TreeEditor(tree);
		oTreeEditor.horizontalAlignment = horizontalAlignment;
		oTreeEditor.grabHorizontal = false;
		oTreeEditor.minimumWidth = 12;
		oTreeEditor.minimumHeight = 10;
		return oTreeEditor;
	}
}

class DispelTreeEditorBug {

	private Tree tree;

	private String[] sEditorNames = {};;

	public DispelTreeEditorBug(Tree tree, String[] sEditorNames) {
		this.tree = tree;
		this.sEditorNames = sEditorNames;
		tree.addTreeListener(new TreeListener() {

			public void treeCollapsed(TreeEvent e) {
				TreeItem treeItem = (TreeItem) e.item;
				for (int len = treeItem.getItemCount(), i = 0; i < len; i++) {
					hideControlInTableTreeItem(treeItem.getItem(i), false);
				}
			}

			public void treeExpanded(TreeEvent e) {
				TreeItem treeItem = (TreeItem) e.item;
				setVisibilityTreeEditor(treeItem, true);
				for (int len = treeItem.getItemCount(), i = 0; i < len; i++) {
					TreeItem cTreeItem = treeItem.getItem(i);
					setVisibilityTreeEditor(cTreeItem, true);
					for (int j = 0; j < cTreeItem.getItemCount(); j++) {
						hideControlInTableTreeItem(cTreeItem.getItem(j), true);
					}
				}
			}
		});
	}

	public void refurbish() {
		hideControlInTableTreeItem();
	}

	private void setVisibilityTreeEditor(TreeItem treeItem, boolean visible) {
		for (String sEditorName : sEditorNames) {
			Object obj = treeItem.getData(sEditorName);
			if (obj instanceof TreeEditor) {
				TreeEditor oTreeEditor = (TreeEditor) obj;
				oTreeEditor.minimumWidth = visible ? 12 : 0;
				oTreeEditor.layout();
			}
		}
	}

	private void hideControlInTableTreeItem() {
		for (int i = 0; i < tree.getItemCount(); i++) {
			hideControlInTableTreeItem(tree.getItem(i), true);
		}
	}

	private void hideControlInTableTreeItem(TreeItem treeItem, boolean pVisible) {
		TreeItem parentItem = treeItem.getParentItem();
		boolean visible = pVisible && (parentItem == null || parentItem.getExpanded());
		setVisibilityTreeEditor(treeItem, visible);
		for (int len = treeItem.getItemCount(), i = 0; i < len; i++) {
			hideControlInTableTreeItem(treeItem.getItem(i), visible);
		}
	}
}