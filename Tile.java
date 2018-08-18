package mario;

import java.awt.Image;

public class Tile extends Character  {
	
	int x;
	int y;
	
	Image image;
	
	boolean isPlatformStarter;

	public Tile(int i, int j, Image im) {
		image = im;
		
		x = i;
		y = j;
		
		isPlatformStarter = false;
	}

	public Image getImage() {
		return image;
	}
	
	public void scroll() {
		this.x -= 6;
	}
}
