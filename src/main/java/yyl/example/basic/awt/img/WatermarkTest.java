package yyl.example.basic.awt.img;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * 为图片增加水印<br>
 * 原理就是通过Graphics2D绘图<br>
 */
public class WatermarkTest {

    public static void main(String[] args) throws IOException {
        BufferedImage image = null;
        try (InputStream input = Helper.openInputStream()) {
            image = pressWatermark(input);
        }
        JFrame frame = new JFrame();
        ImageCanvas canvas = new ImageCanvas(image);
        frame.getContentPane().add(canvas);
        frame.setSize(280, 280);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Hello World");
        frame.setResizable(false);
        frame.setVisible(true);
        
        // 输出成文件
        // write(image);
    }

    /**
     * 添加水印
     * @param input 图片流
     * @return 处理后的图片流
     */
    final static BufferedImage pressWatermark(InputStream input) throws IOException {
        int x = 0;// 水印距离目标图片左侧的偏移量
        int y = 0;// 水印距离目标图片上侧的偏移量
        float alpha = 1F;// 透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)

        Image image = ImageIO.read(input);
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        BufferedImage im = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = im.createGraphics();

        g.drawImage(image, 0, 0, width, height, null);

        // 设置透明度
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

        int fontSize = 20;
        // 设置字体
        g.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, fontSize));
        // 设置颜色
        g.setColor(Color.WHITE);
        // 添加水印文字
        g.drawString("hello watermark", x, y + fontSize);

        // 释放此图形的上下文以及它使用的所有系统资源
        g.dispose();
        return im;
    }

    final static void write(RenderedImage image) throws IOException {
        java.io.File target = new java.io.File("D:/test-" + System.currentTimeMillis() + ".png");
        ImageIO.write(image, "png", target);
    }

}
