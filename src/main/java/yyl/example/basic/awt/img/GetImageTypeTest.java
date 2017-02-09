package yyl.example.basic.awt.img;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * 获得图片类型
 */
public class GetImageTypeTest {

	public static void main(String[] args) throws IOException {
		InputStream input = GetImageTypeTest.class.getResourceAsStream("image1.png");
		System.out.println(getFormatName(input));
	}

	/**
	 * 读取图片类型
	 * @param input 图片数据流
	 * @return 图片类型
	 */
	private static String getFormatName(InputStream input) {
		ImageInputStream is = null;
		try {
			// Create an image input stream on the image
			is = ImageIO.createImageInputStream(input);
			// Find all image readers that recognize the image format
			Iterator<ImageReader> it = ImageIO.getImageReaders(is);
			if (!it.hasNext()) {
				return null;
			}
			// Use the first reader
			ImageReader reader = it.next();
			// Return the format name
			return reader.getFormatName();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// The image could not be read
		return null;
	}

}
