package mario;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.IOException;

import javax.swing.ImageIcon;

import mario.Global_Variables.vars;

import java.util.ArrayList;
import java.util.Random;

public class Level_Generator implements ImageObserver {

	public Image platformTile;
	public ArrayList<Platform> platforms;
	public int map_width;
	public int map_height;

	public Level_Generator() throws IOException {
		createImages();
		createPlatforms();
		createCharacters();

	}

	public Image getTile() {
		return platformTile;
	}

	public void createImages() {

		ImageIcon i = new ImageIcon(Global_Variables.class.getResource("platformTile.jpg"));
		platformTile = i.getImage();
	}

	public void createPlatforms() {
		platforms = new ArrayList<Platform>();

		Random r = new Random();

		map_width = 14000;
		map_height = 400;

		// initial platform for mario to land on
		platforms.add(new Platform(Mario.x, map_height / 2, platformTile, 6));

		// create strings of tiles to make platforms
		int maxPlatformLength = 4;
		for (int k = 1; k < 300; k++) {

			// -----------------------------------------------------------------------------------------------------
			// assign platform bases at random distance between 5 and marios max jump length
			// from the previous tile
			// -----------------------------------------------------------------------------------------------------

			int baseX = 0;
			int baseY = -1;
			int x = r.nextInt(2);

			// generate platforms randomly certain distances apart within screen boundaries

			if (x == 0) {
				
					baseY = platforms.get(k - 1).y + r.nextInt(75) + 45;
				baseX = platforms.get(k - 1).x + r.nextInt(170) + platforms.get(k - 1).getWidth();
			}

			else {
				
				baseY = platforms.get(k - 1).y - r.nextInt(150) + 45;
				baseX = platforms.get(k - 1).x + +r.nextInt(170) + platforms.get(k - 1).getWidth();
			}

			platforms.add(new Platform(baseX, baseY, platformTile, maxPlatformLength));
		}
	}

	// create objects from classes of entities and characters
	public void createCharacters() throws IOException {

		// create mario
		vars.mario = new Mario();

		// create mushrooms on a random tile on the platform for 1 out of every 10 platforms
		vars.mushrooms.add(new Mushroom(10000, 10000));
		Random rand = new Random();
		for (int i = 0; i < platforms.size(); i += 10) {
			int x = platforms.get(i).x + rand.nextInt(platforms.get(i).getWidth());
			int y = platforms.get(i).y - vars.mushrooms.get(0).image.getHeight(this);
			vars.mushrooms.add(new Mushroom(x, y));
		}

		// create coins
		vars.coins.add(new Coin(0, 0));
		for (int i = 0; i < platforms.size(); i += 10) {
			int x = platforms.get(i).x + rand.nextInt(platforms.get(i).getWidth());
			int y = platforms.get(i).y - vars.mushrooms.get(0).image.getHeight(this);
			vars.coins.add(new Coin(x, y));
		}
	}

	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub
		return false;
	}
}