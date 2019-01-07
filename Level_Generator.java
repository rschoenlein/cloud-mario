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

		for (int k = 0; k < 100; k++) {
			platforms.add(new Platform(r.nextInt(map_width), r.nextInt(map_height), platformTile, 6));
		}

		// initial platform for mario to land on
		platforms.get(0).x = Mario.x;
		platforms.get(0).y = map_height/2 + 20;

//		// create strings of tiles to make platforms
//		int platformLength = 6;
//		int maxPlatformLength = 6;
//		for (int k = 1; k < tileMap.size() + maxPlatformLength; k+= platformLength) {
//
//			//-----------------------------------------------------------------------------------------------------
//			// assign platform bases at random distance between 5 and marios max jump length from the previous tile
//			//-----------------------------------------------------------------------------------------------------
//			
//			int dist = 0;
//			int baseX = r.nextInt(100) + 5;
//			int baseY = r.nextInt(75) + 5;
//			while(dist > 200 || dist < 5 ) {
//				
//				
//				if(r.nextInt(2) == 0)
//				{
//					baseY = tileMap.get(k - 1).y + r.nextInt(75) + 5;
//					baseX = tileMap.get(k - 1).x + r.nextInt(150) + 5;
//				}
//				
//				else
//				{
//					baseY = tileMap.get(k - 1).y - r.nextInt(75) + 5;
//					baseX = tileMap.get(k - 1).x + r.nextInt(150) + 5;
//				}
//				
//				
//				dist = getDistanceBetweenPoints(baseX, baseY, tileMap.get(k - 1).x, tileMap.get(k - 1).y);
//			}
//			
//			tileMap.get(k).y = baseY;
//			tileMap.get(k).x = baseX;
//			platformLength = r.nextInt(4) + 2;
//			
//		
//			
//			// create platform by adding tiles in same y plane to tile located at baseY, baseX
//			for (int j = k + 1; j < k + platformLength; j++) {
//				System.out.println("k" + k);
//				System.out.println("j" + j);
//				System.out.println("platformLength" + platformLength);
//				tileMap.get(j).y = baseY;
//				tileMap.get(j).x = tileMap.get(j - 1).x + tileMap.get(j).getImage().getWidth(this);
//			}
//		}
	}

	// create objects from classes of entities and characters
	public void createCharacters() throws IOException {

		// create mario
		vars.mario = new Mario();
		vars.mario.speak();

		// create mushrooms on platforms every 10 tiles
		vars.mushrooms.add(new Mushroom(10000, 10000));
		Random rand = new Random();
		for (int i = 0; i < platforms.size(); i += 10) {
			int x = platforms.get(i).x + rand.nextInt(100);
			int y = platforms.get(i).y - vars.mushrooms.get(0).image.getHeight(this);
			vars.mushrooms.add(new Mushroom(x, y));
		}

		// create coins
		vars.coins.add(new Coin(0, 0));
		for (int i = 0; i < platforms.size(); i += 10) {
			int x = platforms.get(i).x;
			int y = platforms.get(i).y - vars.mushrooms.get(0).image.getHeight(this);
			vars.coins.add(new Coin(x, y));
		}
	}

	public int getDistanceBetweenPoints(int x1, int y1, int x2, int y2) {
		return (int) Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}

	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub
		return false;
	}
}