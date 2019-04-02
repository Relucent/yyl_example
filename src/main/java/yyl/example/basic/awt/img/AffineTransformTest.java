package yyl.example.basic.awt.img;

import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
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
        AffineTransform original = graphics.getTransform();
        try {
            AffineTransform xform = new AffineTransform();
            xform.setTransform(original);//
            // 位移
            xform.concatenate(AffineTransform.getTranslateInstance(x, y));
            // 缩放
            xform.concatenate(AffineTransform.getScaleInstance(scale, scale));
            // 旋转
            double theta = Math.toRadians(angle);// 角度转弧度
            xform.concatenate(AffineTransform.getRotateInstance(theta, (img.getWidth() / 2), (img.getHeight() / 2)));
            graphics.drawImage(img, xform, null);
        } finally {
            graphics.setTransform(original);
        }
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
