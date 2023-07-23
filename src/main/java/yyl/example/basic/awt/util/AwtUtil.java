package yyl.example.basic.awt.util;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

public class AwtUtil {
    /**
     * 设置居中
     * @param frame 窗口对象
     */
    public static void setCenterLocation(Frame frame) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2);
    }
}