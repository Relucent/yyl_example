package yyl.example.demo.kaptcha;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Properties;

import javax.swing.JFrame;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

public class KaptchaExample {

    public static void main(String[] args) {
        DefaultKaptcha kaptcha = build();
        String text = kaptcha.createText();
        BufferedImage image = kaptcha.createImage(text);
        show(image);
    }

    private static DefaultKaptcha build() {
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.textproducer.font.color", "white");
        properties.setProperty("kaptcha.textproducer.char.space", "5");
        properties.setProperty("kaptcha.background.clear.from", "54,153,214");
        properties.setProperty("kaptcha.background.clear.to", "54,153,214");
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789ABCEFGHIJKLMNOPQRSTUVWXYZ");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        properties.setProperty("kaptcha.image.width", "250");
        properties.setProperty("kaptcha.image.height", "120");
        properties.setProperty("kaptcha.textproducer.font.size", "80");
        properties.setProperty("kaptcha.noise.color", "white");
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }

    @SuppressWarnings("serial")
    private static void show(BufferedImage image) {
        JFrame frame = new JFrame();
        Canvas canvas = new Canvas() {
            @Override
            public void paint(Graphics g) {
                g.drawImage(image, 0, 0, this);
            }
        };
        frame.getContentPane().add(canvas);
        frame.setBounds(100, 100, 250, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Hello World");
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
