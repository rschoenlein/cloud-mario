package mario;

import java.awt.Image;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public interface Global_Variables {
	public class vars {

		public static int screen_width = 750;
		public static int screen_height = 450;

		// for loading images
		Buffered_Image_Loader loader = new Buffered_Image_Loader();

		// background
		public static ImageIcon b1 = new ImageIcon(Global_Variables.class.getResource("coin.png"));
		public static Image temp1 = b1.getImage();
		static Scrolling_Background background = new Scrolling_Background(temp1);

		// mushrooms
		public static ImageIcon b2 = new ImageIcon(Global_Variables.class.getResource("super-mushroom.png"));
		public static Image temp2 = b2.getImage();
		public static ArrayList<Mushroom> mushrooms = new ArrayList<Mushroom>();

		// coins
		public static ArrayList<Coin> coins = new ArrayList<Coin>();

		// mario object
		public static Mario mario;

		//map
		static Level_Generator l;
		
		// floor of map
		public static int floor = screen_height / 2;
		
		//track if game over
		public static boolean gameOver;

		vars vars = new vars();
	}
}
