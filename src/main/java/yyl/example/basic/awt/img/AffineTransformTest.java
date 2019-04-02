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


public class AffineTransformTest {

    public static void main(String[] args) throws IOException {

        BufferedImage image = Helper.getImage();
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage canvas = createTranslucentImage(width * 2, height * 2);
        Graphics2D graphics = canvas.createGraphics();

        drawImage(image, 0, 0, 1.0, 0, graphics);
        drawImage(image, width, 0, 0.5, 90, graphics);
        drawImage(image, 0, height, 0.5, 180, graphics);
        drawImage(image, width, height, 1.0, 270, graphics);

        graphics.dispose();

        write(canvas);
    }

    private static void drawImage(BufferedImage img, int x, int y, double scale, double angle, Graphics2D graphics) {

        // 旋转
        img = rotate(img, angle);
        // 缩放
        img = scale(img, scale);

        graphics.drawImage(img, x, y, null);
    }

    // 旋转
    private static BufferedImage rotate(BufferedImage img, double degree) {

        // 将角度转换到0-360度之间
        degree = degree % 360;
        if (degree < 0) {
            degree = 360 + degree;
        }
        // 将角度转为弧度
        double theta = Math.toRadians(degree);

        int w = img.getWidth();
        int h = img.getHeight();
        int sw = 0;
        int sh = 0;

        // 确定旋转后的宽和高(计算原点)
        if (degree == 180 || degree == 0 || degree == 360) {
            sw = w;
            sh = h;
        } else if (degree == 90 || degree == 270) {
            sw = h;
            sh = w;
        } else {
            sw = sh = (int) (Math.sqrt(w * w + h * h));
        }
        int x = (sw / 2) - (w / 2);
        int y = (sh / 2) - (h / 2);

        AffineTransform at = new AffineTransform();
        at.rotate(theta, sw / 2, sh / 2);
        at.translate(x, y);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
        return op.filter(img, null);
    }

    // 缩放
    private static BufferedImage scale(BufferedImage img, double scale) {
        int w = (int) (img.getWidth() * scale);
        int h = (int) (img.getHeight() * scale);
        return zoom(img, w, h);
    }

    // 缩放
    private static BufferedImage zoom(BufferedImage img, int width, int height) {
        double sx = 0.0;
        double sy = 0.0;

        sx = (double) width / img.getWidth();
        sy = (double) height / img.getHeight();

        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(sx, sy), null);
        return op.filter(img, null);
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
