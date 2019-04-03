package yyl.example.basic.awt.img;

import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * AffineTransform 类表示 2D 仿射变换
 */
public class AffineTransformTest {

    public static void main(String[] args) throws IOException {

        BufferedImage image = Helper.getImage();
        int w = image.getWidth();
        int h = image.getHeight();

        BufferedImage canvas = createTranslucentImage(w * 2, h * 2);
        Graphics2D graphics = canvas.createGraphics();

        drawImage(image, 0, 0, w, h, 1.0, 0, graphics);
        drawImage(image, w, 0, w, h, 0.5, 90, graphics);
        drawImage(image, 0, h, w, h, 0.5, 180, graphics);
        drawImage(image, w, h, w, h, 1.0, 270, graphics);

        graphics.dispose();

        write(canvas);
    }

    private static void drawImage(BufferedImage img, int x, int y, int w, int h, double scale, double angle, Graphics2D graphics) {

        // 获得缩放后的图片
        img = scale(img, w, h, scale);

        // 新图片宽高
        int w1 = img.getWidth();
        int h1 = img.getHeight();

        // 计算缩放后产生的坐标偏移
        int xOffset = (int) ((w - w * scale) / 2);
        int yOffset = (int) ((h - h * scale) / 2);

        // 新图片坐标
        int x1 = (int) (x + xOffset);
        int y1 = (int) (y + yOffset);

        // 角度转弧度
        double theta = toRadians(angle);

        // 旋转圆心坐标
        double anchorx = x1 + (w1 / 2);
        double anchory = y1 + (h1 / 2);

        // 仿射变换
        AffineTransform transform = new AffineTransform();
        transform.rotate(theta, anchorx, anchory);
        transform.translate(x1, y1);

        graphics.drawImage(img, transform, null);
    }

    // 缩放
    private static BufferedImage scale(BufferedImage src, int w, int h, double scale) {
        double sx = w * scale / src.getWidth();
        double sy = h * scale / src.getHeight();
        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(sx, sy), null);
        return op.filter(src, null);
    }

    // 将角度转为弧度
    private static double toRadians(double degree) {

        // 将角度转换到0-360度之间
        degree = degree % 360;
        if (degree < 0) {
            degree = 360 + degree;
        }
        // 将角度转为弧度
        return Math.toRadians(degree);
    }

    private static BufferedImage createTranslucentImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        image = graphics.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        graphics.dispose();
        return image;
    }

    private static void write(RenderedImage image) {
        File target = new File("D:/test-" + System.currentTimeMillis() + ".png");
        try {
            ImageIO.write(image, "png", target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
