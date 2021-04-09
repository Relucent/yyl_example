package yyl.example.demo.thumbnailator;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * _Thumbnailator 是一个开源的图像处理工具类库。<br>
 * 提供了对图片多种处理，例如缩放，旋转，裁切，水印等功能，支持文件夹批量的图片处理。<br>
 * {@link https://github.com/coobird/thumbnailator}
 */
public class ThumbnailatorExample {

    public static void main(String[] args) throws Exception {
        // 原始图片
        BufferedImage original = getImage();

        // 创建一个水印图片，图片大小 50,50
        BufferedImage watermark = Thumbnails.of(original).size(50, 50).asBufferedImage();

        // 处理图片
        BufferedImage processed = Thumbnails.of(original)
                // 旋转角度 90
                .rotate(90)
                // 加水印，右下角，透明度 0.5
                .watermark(Positions.BOTTOM_RIGHT, watermark, 0.5f)
                // 缩放(如果没有设置尺寸，则必须设置缩放度)
                .scale(1.0)
                // 输出品质 0.8
                .outputQuality(0.8)
                // 输出为 BufferedImage
                .asBufferedImage();

        // 此处用来展示图片，实际环境可以生成文件或者文件流
        display(original, processed);
    }

    private static void display(Image original, Image processed) {

        @SuppressWarnings("serial")
        class ImageCanvas extends Canvas {
            private final Image image;

            public ImageCanvas(Image image) {
                this.image = image;
            }

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(image, 0, 0, this);
            }
        }
        Frame frame = new Frame();
        frame.setLayout(new GridLayout(1, 2));
        frame.add(new ImageCanvas(original));
        frame.add(new ImageCanvas(processed));
        frame.setTitle("Thumbnailator");
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        int width = 520;
        int height = 300;
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();
        frame.setBounds((screen.width - width) / 2, (screen.height - height) / 2, 520, 300);
        frame.setVisible(true);
    }

    private static BufferedImage getImage() throws IOException {
        try (InputStream input = ThumbnailatorExample.class.getResourceAsStream("/yyl/example/basic/awt/img/world.png")) {
            return ImageIO.read(input);
        }
    }
}
