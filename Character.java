package mario;

import java.awt.Image;

import java.awt.Rectangle;

public class Character
{
	public Image image;
	
	public int x;
	public int y;
	
	Rectangle collisionR;
	
	public boolean collision = false;

	public Character() {
		
	}

	public Image getImage() {
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
