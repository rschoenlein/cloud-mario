package mario;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Buffered_Image_Loader {
	private BufferedImage image;

	public BufferedImage load_image(String path) throws IOException {
		image = ImageIO.read(getClass().getResourceAsStream(path));
		return image;
	}
}
