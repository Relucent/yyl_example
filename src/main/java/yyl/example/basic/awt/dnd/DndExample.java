package yyl.example.basic.awt.dnd;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

/**
 * 拖放（Drag-and-Drop）是一种用户界面交互技术，允许用户通过在屏幕上拖动（拖拽）一个对象，并将其释放（放置）到另一个位置或目标区域来执行某些操作。<br>
 * 这项技术使得数据和对象可以在应用程序内或不同应用程序之间轻松地移动、复制或转移。<br>
 * 在拖放操作中，通常涉及两个主要角色：<br>
 * 1、拖动源（Drag Source）：是数据或对象的起始位置。用户从拖动源开始拖动并移动它。<br>
 * 2、放置目标（Drop Target）：是数据或对象的目标位置。用户将拖动的内容释放到放置目标上。<br>
 * 拖放可以用于多种用途，包括但不限于：<br>
 * 1、文件管理：用户可以从文件浏览器中拖动文件到其他文件夹或应用程序中，实现文件的复制或移动操作。<br>
 * 2、图像处理：用户可以从图像编辑器中拖动图像或图形到另一个图像编辑器或文档中，实现图像的复制、合并或导出操作。<br>
 * 3、数据交换：在应用程序之间，用户可以拖放文本、图像或其他数据，从一个应用程序共享到另一个应用程序。<br>
 * 4、界面定制：某些应用程序允许用户自定义界面，通过拖动和放置工具栏按钮或窗口部件来改变布局。<br>
 * 5、游戏设计：游戏开发者可以使用拖放来设置游戏场景、精灵、物体等元素。<br>
 * Java 提供了 Drag-and-Drop API，使得在图形用户界面（GUI）应用程序中实现拖放功能变得简单。<br>
 * 这允许开发者将拖放功能轻松集成到 Java 程序中，并为用户提供更直观、更友好的交互体验。<br>
 */
public class DndExample {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {

        // 设置风格样式
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 定义主窗口
        JFrame frame = new JFrame("DropExample");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setResizable(false);

        JPanel dndPanel = createLabelPanel();
        setDropTarget(dndPanel);

        frame.add(dndPanel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * 创建一个带文字的面板
     * @return 面板
     */
    private static JPanel createLabelPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Drag and drop files here!");
        label.setFont(new Font("Arial", Font.PLAIN, 24));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());
        panel.add(label);
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    /**
     * 为组件设置拖拽功能
     * @param component 用于拖拽的组件
     */
    private static void setDropTarget(JComponent component) {

        // 创建一个拖拽事件的监听
        DropTargetListener listener = new DropTargetListener() {
            @Override
            public void dragEnter(DropTargetDragEvent event) {
                // 当拖拽操作开始并且光标进入放置目标区域时，执行此方法
            }

            @Override
            public void dragOver(DropTargetDragEvent event) {
                // 在拖拽操作期间，只要光标在放置目标区域内，该方法将会被重复调用
            }

            @Override
            public void dropActionChanged(DropTargetDragEvent event) {
                // 当用户更改拖放操作的动作时，执行此方法
            }

            @Override
            public void dragExit(DropTargetEvent event) {
                // 当光标离开放置目标区域时，执行此方法
            }

            @SuppressWarnings("rawtypes")
            @Override
            public void drop(DropTargetDropEvent event) {
                // 当用户释放鼠标按钮以完成拖放操作时，执行此方法

                try {
                    // 获取拖放的传输数据
                    Transferable transferable = event.getTransferable();
                    // 判断数据是否包含文件类型
                    if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {

                        // 告诉系统该拖放操作应该采取什么行为
                        event.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

                        // 获取拖放的文件列表
                        List<?> fileList = (List) transferable.getTransferData(DataFlavor.javaFileListFlavor);

                        if (fileList.size() > 0) {
                            // 获取第一个文件对象
                            File file = (File) fileList.get(0);
                            // 显示文件名称
                            JOptionPane.showMessageDialog(component, file.getAbsolutePath(), "File", JOptionPane.PLAIN_MESSAGE);
                        }
                        // 该方法用于通知系统拖放操作的完成结果。当 success 参数为 true 时，表示拖放操作成功完成；当 success 参数为 false 时，表示拖放操作未成功完成或被取消。
                        event.dropComplete(true);
                    } else {
                        // 方法用于通知系统拒绝当前的拖放操作
                        event.rejectDrop();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    // 方法用于通知系统拒绝当前的拖放操作
                    event.rejectDrop();
                }

            }
        };

        // 创建一个 DropTarget，该对象用于接收拖动源（Drag Source）拖放的数据或对象。
        DropTarget dropTarget = new DropTarget(component, DnDConstants.ACTION_COPY_OR_MOVE, listener, true);

        // 将 DropTarget 设置到组件上
        component.setDropTarget(dropTarget);
    }

}
