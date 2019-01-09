package mario;

import java.awt.Image;

import mario.Global_Variables.vars;

public class Scrolling_Background
{
	Image image;
	int x;
	int y;
	
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
		// scroll objects around mario
		for (int i = 0; i < vars.level.platforms.size(); i++)
			vars.level.platforms.get(i).x-=3;
		for (int i = 0; i < vars.mushrooms.size(); i++)
			vars.mushrooms.get(i).x -= 3;
		for (int i = 0; i < vars.coins.size(); i++)
			vars.coins.get(i).x -= 3;
		x-=3;
	}
}