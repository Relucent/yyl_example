package yyl.example.demo.poi.ppt;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.poi.sl.usermodel.PictureData.PictureType;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextBox;

public class PptExample {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        try (XMLSlideShow ppt = new XMLSlideShow()) {
            XSLFSlide slide = ppt.createSlide();// 创建幻灯片
            XSLFTextBox textBox = slide.createTextBox();
            textBox.setAnchor(new Rectangle2D.Double(10, 10, 0, 0));
            textBox.addNewTextParagraph().addNewTextRun().setText("创建幻灯片");

            // reading an image
            File image = new File("D:/1.png");
            BufferedImage img = ImageIO.read(image);
            byte[] picture = IOUtils.toByteArray(new FileInputStream(image));
            XSLFPictureData idx = ppt.addPicture(picture, PictureType.PNG);
            XSLFPictureShape pic = slide.createPicture(idx);

            // 设置当前图片在ppt中的位置，以及图片的宽高
            pic.setAnchor(new java.awt.Rectangle(0, 0, img.getWidth(), img.getHeight()));

            ppt.write(new FileOutputStream("D:/1.pptx"));
        }
    }
}
