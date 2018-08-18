package mario;

import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

import mario.Global_Variables.vars;

import java.util.ArrayList;
import java.util.Random;

public class Level_Generator implements ImageObserver {

	public Image tile;
	public ArrayList<Tile> tileMap;

	public Level_Generator() {
		
		// create images
		tileMap = new ArrayList<Tile>();
		
		ImageIcon i = new ImageIcon(
				Global_Variables.class.getResource("platformTile.jpg"));
		tile = i.getImage();
		
		Random r = new Random();

		// 2000 tiles in a 14000x400 map
		// create tile map filled with platforms
		int map_width = 14000;
		int map_height = 400;
		
		int x = 0;
		int y = 0;
		
		for (int k = 0; k < 500; k++) {
			tileMap.add(new Tile(x, y, tile));
		}

		//initial platform for mario to land on 
		tileMap.get(0).x = vars.mario.x;
		tileMap.get(0).y = map_height - 50;
		
		// create strings of tiles to make platforms
		int platformLength = 5;
		for (int k = 1; k < tileMap.size()- 8; k += platformLength) {
			int baseY = r.nextInt(map_height);
			int baseX = r.nextInt(map_width);
			tileMap.get(k).y = baseY;
			tileMap.get(k).x = baseX;
			platformLength = r.nextInt(3) + 2;
			tileMap.get(k).isPlatformStarter = true;
			for (int j = k + 1; j < k + platformLength; j++) {
				tileMap.get(j).y = baseY;
				tileMap.get(j).x = tileMap.get(j - 1).x + tileMap.get(j).getImage().getWidth(this);
			}
		}
	}

	public Image getTile() {
		return tile;
	}

	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3,
			int arg4, int arg5) {
		// TODO Auto-generated method stub
		return false;
	}
}