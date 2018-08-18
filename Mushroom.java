package mario;

import javax.swing.ImageIcon;

public class Mushroom extends Character {
	public Mushroom(int i, int j) {
		ImageIcon b2 = new ImageIcon(Global_Variables.class.getResource("super-mushroom.png"));
		image = b2.getImage();
		x = i;
		y = j;
	}
}
