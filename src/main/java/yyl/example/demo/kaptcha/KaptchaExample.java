package yyl.example.demo.kaptcha;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Properties;

import javax.swing.JFrame;

import com.google.code.kaptcha.Constants;
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
        kaptcha.setConfig(getConfig());
        return kaptcha;
    }

    private static Config getConfig() {
        Properties properties = new Properties();
        properties.put(Constants.KAPTCHA_BORDER, "no");// kaptcha.border
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "237,247,255");// kaptcha.textproducer.font.color
        properties.put(Constants.KAPTCHA_BACKGROUND_CLR_FROM, "75,177,255");// kaptcha.background.clear.form
        properties.put(Constants.KAPTCHA_BACKGROUND_CLR_TO, "75,177,255");// kaptcha.background.clear.to
        properties.put(Constants.KAPTCHA_NOISE_COLOR, "white");// kaptcha.noise.color
        properties.put(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.WaterRipple");
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");// kaptcha.textproducer.char.length
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "5");// kaptcha.textproducer.char.space
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Arial,Courier");// kaptcha.textproducer.font.names
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "150");// kaptcha.textproducer.font.size
        properties.put(Constants.KAPTCHA_IMAGE_WIDTH, "500");// kaptcha.image.width
        properties.put(Constants.KAPTCHA_IMAGE_HEIGHT, "230");// kaptcha.image.height
        return new Config(properties);
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
        frame.setBounds(100, 100, 600, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Hello World");
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
