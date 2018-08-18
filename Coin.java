package mario;

import java.awt.Image;

import java.awt.Rectangle;
import java.io.IOException;

public class Coin extends Character {
	
	// rectangle for collisions
	public Rectangle collisionR;
	
	// sprite sheet and current clip
	public Sprite_Sheet coinSprite;
	public static Image coinClip;
	
	// x and y of clip
	public int clipX = 1;
	public int clipY = 1;

	// timer for spinning
	public javax.swing.Timer coinTimer;

	public Coin(int i, int j) {
		x = i;
		y = j;
		Buffered_Image_Loader loader = new Buffered_Image_Loader();
		try {
			// put mario sprite image into sprite_sheet object
			coinSprite = new Sprite_Sheet(loader.load_image("/mario/coin.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
