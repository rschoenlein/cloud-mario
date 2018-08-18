package mario;

import java.awt.Image;

public class Scrolling_Background
{
	Image image;
	int x;
	int y;
	public float vY;
	
	public Scrolling_Background(Image img)
	{
		image = img;
		x = 0;
		y = -550;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void scroll()
	{
		x-=6;
	}
}