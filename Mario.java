
package mario;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.io.IOException;

public class Mario implements Global_Variables, ImageObserver {
	public int coins;
	public Rectangle collisionR;
	public boolean facingLeft = false;
	public boolean facingRight = false;
	
	// sprite sheet and current clip
	public Sprite_Sheet marioSprite;
	public static Image marioClip;
	
	// x and y of clip
	public int clipX = 1;
	public int clipY = 1;
	
	// position of mario
	public int x = vars.screen_width / 2 - 8;
	public int y = vars.screen_height / 2 - 16;
	
	public boolean collision;
	public boolean movingDown;
	public boolean canMoveForward;

	public void speak() {
		System.out.println("hello!");
	}

	public Mario() throws IOException {
		
		coins = 0;
		
		// not running into anything to start
		canMoveForward = true;

		collision = false;

		movingDown = false;
		
		// load mario sprite image
		Buffered_Image_Loader loader = new Buffered_Image_Loader();
		try {
			// put mario sprite image into sprite_sheet object
			marioSprite = new Sprite_Sheet(loader.load_image("/mario/mario_sprite.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub
		return false;
	}
}
