package yyl.example.exercise.swt.tabletree;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

//表格树
public class TableTreeTest {

	private Tree tree;

	TableTreeTest() {
		final Display display = Display.getDefault();
		final Shell shell = new Shell();
		shell.setLayout(new FillLayout());
		shell.setText("TableTreeTest");
		shell.setSize(500, 500);
		tree = new Tree(shell, SWT.BORDER | SWT.FULL_SELECTION);
		initTableColumn();
		testFreshTableTree();//填充数据
		hideControlInTableTreeItem();
		removeBug(tree);
		shell.layout();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * <p>
	 * 功能描述:设置表头
	 * </p>
	 */
	public void initTableColumn() {
		String[] sColumnNames = new String[] { "第一列", "第二列", "第三列", "第四列" };
		int[] iWidthColumns = new int[] { 100, 100, 100, 100 };

		for (int i = 0; i < sColumnNames.length; i++) {
			TreeColumn treeColumn = new TreeColumn(tree, SWT.NONE);
			treeColumn.setWidth(iWidthColumns[i]);
			treeColumn.setText(sColumnNames[i]);
		}
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		//# TreeColumn.pack();		
	}

	public void testFreshTableTree() {
		for (int i = 0; i < 5; i++) {
			TreeItem parent = new TreeItem(tree, SWT.NONE);
			parent.setText(0, "类型" + (i + 1));

			for (int j = 0; j < 3; j++) {
				TreeItem child = new TreeItem(parent, SWT.NONE);
				child.setText(0, "");
				child.setText(1, "状态1");
				child.setText(2, "状态2");

				TreeEditor editor = createTreeEditor(tree, SWT.LEFT);
				Button button = new Button(tree, SWT.CHECK);
				child.setData("EDITOR_3", editor);
				editor.setEditor(button, child, 3);
				button.setSelection(true);
				button.setEnabled(true);

				for (int k = 0; k < 3; k++) {
					TreeItem grandchild = new TreeItem(child, SWT.NONE);
					grandchild.setText(0, "");
					grandchild.setText(1, "数据1");
					grandchild.setText(2, "数据2");

					TreeEditor editor2 = createTreeEditor(tree, SWT.LEFT);
					Button button2 = new Button(tree, SWT.CHECK);
					grandchild.setData("EDITOR_3", editor2);
					editor2.setEditor(button2, grandchild, 3);
					button2.setSelection(true);
					button2.setEnabled(true);
				}
			}
			parent.setExpanded(false);
		}
	}

	/**
	 * <p>
	 * 功能描述:生成一个TreeEditor控件
	 * </p>
	 * @param tree TreeEditor控件所属的Tree
	 * @param horizontalAlignment 水平位置(SWT.CANCEL | SWT.RIGHT | SWT.LEFT)
	 */
	private TreeEditor createTreeEditor(Tree tree, int horizontalAlignment) {
		TreeEditor oTreeEditor = new TreeEditor(tree);
		oTreeEditor.horizontalAlignment = horizontalAlignment;
		oTreeEditor.grabHorizontal = false;
		oTreeEditor.minimumWidth = 12;
		oTreeEditor.minimumHeight = 10;
		return oTreeEditor;
	}

	public static void main(String[] args) {
		new TableTreeTest();
	}

	/*
	 * 注：以下代码用于处理SWT树节点折叠时时的一个问题 当树节点折叠时，应该隐藏节点中的控件(TreeEditor) 而当前SWT版本,Tree控件不会自动隐藏，疑为SWT的BUG
	 */
	public void removeBug(final Tree tree) {

		//		new Object(){
		//			public void init(){
		//
		//		}.init();

		tree.addTreeListener(new TreeListener() {

			public void treeCollapsed(TreeEvent e) {
				//隐藏子节点控件
				TreeItem treeItem = (TreeItem) e.item;
				for (int len = treeItem.getItemCount(), i = 0; i < len; i++) {
					hideControlInTableTreeItem(treeItem.getItem(i), false);
				}
			}

			public void treeExpanded(TreeEvent e) {
				TreeItem treeItem = (TreeItem) e.item;
				//显示子节点控件
				setVisibilityTreeEditor(treeItem, true);
				for (int len = treeItem.getItemCount(), i = 0; i < len; i++) {
					TreeItem cTreeItem = treeItem.getItem(i);
					setVisibilityTreeEditor(cTreeItem, true);
					//显示(隐藏)孙节点控件
					for (int j = 0; j < cTreeItem.getItemCount(); j++) {
						hideControlInTableTreeItem(cTreeItem.getItem(j), true);
					}
				}
			}
		});
	}

	private void setVisibilityTreeEditor(TreeItem treeItem, boolean visible) {
		//		for(String sEditorName:{"EDITOR_3"}){
		String sEditorName = "EDITOR_3";
		Object obj = treeItem.getData(sEditorName);
		if (obj instanceof TreeEditor) {
			TreeEditor oTreeEditor = (TreeEditor) obj;
			oTreeEditor.minimumWidth = visible ? 12 : 0;
			oTreeEditor.layout();
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

	/**
	 * <p>
	 * 功能描述:清空
	 * </p>
	 * @param tableTree Tree对象
	 */
	protected void clearTableTreeItem(Tree tableTree) {
		for (int i = 0; i < tableTree.getItemCount(); i++) {
			clearControlInTableTreeItem(tableTree.getItem(i));
		}
		tableTree.removeAll();
	}

	/**
	 * <p>
	 * 功能描述:销毁批核列表中的所有复选框按钮
	 * </p>
	 * @param treeItem TreeItem对象
	 * @update YYL
	 */
	private void clearControlInTableTreeItem(TreeItem treeItem) {
		//销毁内部控件
		Object obj = treeItem.getData("EDITOR_3");
		if (obj instanceof TreeEditor) {
			TreeEditor editor = (TreeEditor) obj;
			Control control = editor.getEditor();
			if (control != null)
				control.dispose();
			editor.dispose();
		}
		for (int len = treeItem.getItemCount(), i = 0; i < len; i++) {
			clearControlInTableTreeItem(treeItem.getItem(i));
		}
	}
}
