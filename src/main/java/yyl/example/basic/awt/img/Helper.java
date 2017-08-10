package yyl.example.basic.awt.img;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

class Helper {
	public static InputStream openInputStream() throws IOException {
		return Helper.class.getResourceAsStream("world.png");
	}

	public static Image getImage() throws IOException {
		try (InputStream input = openInputStream()) {
			return ImageIO.read(input);
		}
	}

}
