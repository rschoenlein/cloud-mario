package mario;

import java.awt.image.BufferedImage;

public class Sprite_Sheet
{
	private BufferedImage image;
	public Sprite_Sheet(BufferedImage image)
	{
		this.image = image;
	}

	public BufferedImage grab_image(int x, int y, int width, int height)
	{
		BufferedImage img = image.getSubimage((x * 32) - 32, (y *32) - 32, width, height);
		return img;
	}
}
