package yyl.example.basic.awt.lookandfeel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import yyl.example.basic.awt.util.AwtUtil;

/**
 * 界面显示风格
 * @see javax.swing.LookAndFeel
 * @see javax.swing.UIManager
 */
public class LookAndFeelDemo {

    @SuppressWarnings({ "unchecked", "serial" })
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            final JFrame frame = new JFrame();
            frame.setTitle("LookAndFeel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setSize(512, 256);
            AwtUtil.setCenterLocation(frame);

            Container container = frame.getContentPane();

            final JComboBox<LookAndFeelInfo> combobox = new JComboBox<>();
            final JTextArea textArea = new JTextArea();
            container.add(combobox, BorderLayout.NORTH);
            container.add(new JScrollPane(textArea), BorderLayout.CENTER);

            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                combobox.addItem(info);
            }

            combobox.setRenderer(new BasicComboBoxRenderer() {
                @SuppressWarnings("rawtypes")
                @Override
                public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    if (value instanceof LookAndFeelInfo) {
                        value = ((LookAndFeelInfo) value).getName();
                    }
                    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                }
            });

            combobox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    LookAndFeelInfo info = (LookAndFeelInfo) combobox.getSelectedItem();
                    System.out.println("Selected option: " + info.getName());
                    try {
                        // 设置风格样式
                        UIManager.setLookAndFeel(info.getClassName());

                        textArea.setText(info.getClassName());

                        // 界面的刷新
                        SwingUtilities.updateComponentTreeUI(frame);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            frame.setVisible(true);
        });
    }
}
