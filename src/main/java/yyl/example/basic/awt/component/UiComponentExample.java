package yyl.example.basic.awt.component;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * 常用的UI组件<br>
 */
public class UiComponentExample {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        // 创建主窗口
        JFrame frame = new JFrame("UiComponentExample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建主面板并设置为GridBagLayout布局
        JPanel mainPanel = new JPanel(new GridBagLayout());

        frame.add(mainPanel);

        // 创建GridBagConstraints对象，用于设置组件的约束
        GridBagConstraints constraints = new GridBagConstraints();
        // 设置组件之间的间隔
        constraints.insets = new Insets(5, 5, 5, 5);
        // 设置第一个组件的位置
        constraints.gridx = 0;
        constraints.gridy = 0;

        // 添加组件和标签
        addComponentWithLabel(mainPanel, "Name:", new JTextField(20), constraints);
        addComponentWithLabel(mainPanel, "Age:", new JTextField(5), constraints);
        addComponentWithLabel(mainPanel, "Gender:", createComboBox(), constraints);

        ButtonGroup statusButtonGroup = new ButtonGroup();
        JRadioButton singleRadioBtn = new JRadioButton("Single");
        JRadioButton marriedRadioBtn = new JRadioButton("Married");
        statusButtonGroup.add(singleRadioBtn);
        statusButtonGroup.add(marriedRadioBtn);

        addComponentWithLabel(mainPanel, "Status:", singleRadioBtn, constraints);
        addComponentWithLabel(mainPanel, "", marriedRadioBtn, constraints);
        addComponentWithLabel(mainPanel, "Hobbies:", createCheckBoxes(), constraints);
        addComponentWithLabel(mainPanel, "Address:", createTextArea(), constraints);

        // 绘制按钮
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton confirmButton = new JButton("Confirm");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(buttonPanel, constraints);

        // 调整主窗口的大小适应内容
        frame.pack();

        // 设置尺寸，设置固定的宽度，默认的高度
        int preferredHeight = frame.getPreferredSize().height;
        frame.setSize(new Dimension(500, preferredHeight));

        // 主窗口居中
        frame.setLocationRelativeTo(null);

        // 显示窗口
        frame.setVisible(true);
    }

    private static void addComponentWithLabel(JPanel container, String label, JComponent component, GridBagConstraints constraints) {

        constraints.anchor = GridBagConstraints.NORTH; // 顶端对齐

        // 添加标签
        JLabel jLabel = new JLabel(label);
        container.add(jLabel, constraints);

        // 添加组件
        constraints.gridx = 1;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        container.add(component, constraints);

        // 重置约束参数
        constraints.gridx = 0;
        constraints.gridy++;
        constraints.weightx = 0.0;
        constraints.fill = GridBagConstraints.NONE;
    }

    private static JComboBox<String> createComboBox() {
        String[] options = { "Male", "Female" };
        return new JComboBox<>(options);
    }

    private static JPanel createCheckBoxes() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JCheckBox checkbox1 = new JCheckBox("Reading");
        JCheckBox checkbox2 = new JCheckBox("Swimming");
        JCheckBox checkbox3 = new JCheckBox("Playing Games");

        panel.add(checkbox1);
        panel.add(checkbox2);
        panel.add(checkbox3);

        return panel;
    }

    private static JScrollPane createTextArea() {
        JTextArea textArea = new JTextArea(5, 20);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        return new JScrollPane(textArea);
    }
}
