package mario;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Random;

public class Platform extends Tile implements ImageObserver {

	ArrayList<Tile> tiles;
	int platformLength;

	public Platform(int i, int j, Image im, int maxPlatformLength) {
		super(i, j, im);
		
		tiles = new ArrayList<Tile>();
		
		Random r = new Random();
		platformLength = r.nextInt(maxPlatformLength) + 1;
		
		for(int k = 0; k < platformLength; k++)
			tiles.add(new Tile(i + (k * im.getWidth(this)), j, im));
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return getImage().getWidth(this) * tiles.size();
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void scroll() {
		for(int i = 0; i < tiles.size(); i++) {
			tiles.get(i).x -= 6;
			
		}
		x -= 6;
	}
}
