package yyl.example.basic.awt.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * 表格组件
 */
public class TableExample {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    public static void createAndShowGUI() {

        // Create a JFrame
        // 定义一个顶级窗口
        JFrame frame = new JFrame("Tree And Table Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(800, 600));

        // Create a tree on the left side
        // 在左侧创建一个树组件
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Root");
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("Node 1");
        DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("Node 2");
        rootNode.add(node1);
        rootNode.add(node2);
        JTree tree = new JTree(rootNode);
        JScrollPane treeScrollPane = new JScrollPane(tree);
        treeScrollPane.setMinimumSize(new Dimension(200, treeScrollPane.getPreferredSize().height));

        // Create a table on the right side
        // 在右侧创建一个表格组件
        Object[][] data = { //
                { "John", 25, "Engineer" }, //
                { "Jane", 30, "Teacher" }, //
                { "Mike", 22, "Student" }, //
                { "Sarah", 28, "Doctor" } //
        };
        String[] columnNames = { "Name", "Age", "Career" };
        JTable table = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Create buttons panel above the table
        // 在表格上方创建按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Create a panel for the right side with buttons above the table
        // 在右侧创建一个面板，并将按钮面板和表格放入其中
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(buttonPanel, BorderLayout.NORTH);
        rightPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Create a split pane for left and right side
        // 创建一个JSplitPane来放置左侧和右侧的组件
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScrollPane, rightPanel);
        splitPane.setDividerLocation(200);
        splitPane.setResizeWeight(0.3);

        // Add components to the main frame
        // 将组件添加到主窗口
        frame.add(splitPane, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on the screen | 将窗口居中显示在屏幕上
        frame.setVisible(true);
    }
}