package mario;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.Timer;

@SuppressWarnings("serial")
public class Run extends Applet implements Global_Variables, ActionListener,
		KeyListener {

	@SuppressWarnings("unused")
	private boolean collision = false, pressed_left = false,
			pressed_right = false, pressed_up = false, pressed_down = false,
			moving_up = false, moving_down = false, falling = true,
			onPlatform = false;
	private Timer gameTimer;
	private static final int MAX_JUMP_RATE = 30;
	private static int jumpRate = MAX_JUMP_RATE;
	

	// create objects from classes of entities and characters
	public void create() throws IOException {
		vars.mario = new Mario();
		vars.mario.speak();

		// map
		vars.l = new Level_Generator();
		
		// create mushrooms on platforms every 10 tiles
		vars.mushrooms.add(new Mushroom(10000, 10000));
		Random rand = new Random();
		for (int i = 0; i < vars.l.tileMap.size(); i += 10) {
			int x = vars.l.tileMap.get(i).x + rand.nextInt(100);
			int y = vars.l.tileMap.get(i).y - vars.mushrooms.get(0).image.getHeight(this);
			vars.mushrooms.add(new Mushroom(x, y));
		}

		// create coins
		vars.coins.add(new Coin(0, 0));
		for (int i = 0; i < vars.l.tileMap.size(); i += 10) {
			int x = vars.l.tileMap.get(i).x;
			int y = vars.l.tileMap.get(i).y - vars.mushrooms.get(0).image.getHeight(this);
			vars.coins.add(new Coin(x, y));
		}
	}

	public static void main(String[] args) throws IOException {
		new Run();
	}

	public Run() throws IOException {
		
		// set background Color
		setBackground(Color.CYAN);
		
		// create things in game
		create();
		
		// timer which sets interval to update screen
		gameTimer = new Timer(25, this);
		gameTimer.setInitialDelay(0);
		gameTimer.start();
		
		// make applet listen to keystrokes
		addKeyListener(this);
		
		vars.gameOver = false;
	}

	// USER INPUT
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT)
			pressed_left = true;
		if (key == KeyEvent.VK_RIGHT)
			pressed_right = true;
		if (key == KeyEvent.VK_UP)
			vars.mario.y = 10;
		pressed_up = true;
		if (e.getKeyChar() == KeyEvent.VK_SPACE) {
			moving_up = true;
			falling = false;
		}
		if (key == KeyEvent.VK_DOWN)
			pressed_down = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT)
			pressed_left = false;
		if (key == KeyEvent.VK_RIGHT)
			pressed_right = false;
		if (key == KeyEvent.VK_UP)
			pressed_up = false;
		if (key == KeyEvent.VK_DOWN)
			pressed_down = false;
	}

	// PERFORM EVERY TIMER INTERVAL
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		if(!vars.gameOver) {
			// apply gravity`
			if (falling)
				vars.mario.y += 3;
	
			// set coinClip
			for (int i = 0; i < vars.coins.size(); i++) {
				vars.coins.get(i).coinClip = vars.coins.get(i).coinSprite
						.grab_image(vars.coins.get(i).clipX,
								vars.coins.get(i).clipY, 44, 44);
			}
	
			// set marioClip to current clip
			vars.mario.marioClip = vars.mario.marioSprite.grab_image(
					vars.mario.clipX, vars.mario.clipY, 32, 68);
	
			// set current mario collision box to current clip
			vars.mario.collisionR = new Rectangle(vars.mario.x, vars.mario.y,
					vars.mario.marioClip.getWidth(this),
					vars.mario.marioClip.getHeight(this));
	
			// if right is pressed move across first 5 clips to run
			if (pressed_right) {
				// allow mario to attempt to move against inanimate object(update
				// clips in code below even if canMoveForward is false)
				if (vars.mario.canMoveForward == true) {
					// scroll objects around mario
					for (int i = 0; i < vars.l.tileMap.size(); i++)
						vars.l.tileMap.get(i).scroll();
					for (int i = 0; i < vars.mushrooms.size(); i++)
						vars.mushrooms.get(i).scroll();
					for (int i = 0; i < vars.coins.size(); i++)
						vars.coins.get(i).scroll();
				}
				
				vars.mario.facingRight = true;
				vars.mario.clipY = 1;
				
				// update collision box
				vars.mario.collisionR = new Rectangle(vars.mario.x, vars.mario.y,
						vars.mario.marioClip.getWidth(this),
						vars.mario.marioClip.getHeight(this));
				
				if (vars.mario.clipX < 4) {
					vars.mario.clipX++;
					
					// update collision box
					vars.mario.collisionR = new Rectangle(vars.mario.x,
							vars.mario.y, vars.mario.marioClip.getWidth(this),
							vars.mario.marioClip.getHeight(this));
				} else
					vars.mario.clipX = 2;
	
			} else
				// if not going right, stand still
				vars.mario.clipX = 1;
	
			// move characters down due to gravity
			for (int i = 0; i < vars.mushrooms.size(); i++) {
				if (vars.mushrooms.get(i).getY() < vars.floor)
					vars.mushrooms.get(i).y++;
			}
	
			for (int i = 0; i < vars.coins.size(); i++) {
				if (vars.coins.get(i).getY() < vars.floor)
					vars.coins.get(i).y++;
			}
	
			// move characters left if in view
			for (int i = 0; i < vars.mushrooms.size(); i++) {
				if (vars.mushrooms.get(i).collision = false)
					vars.mushrooms.get(i).x--;
			}
	
			for (int i = 0; i < vars.coins.size(); i++) {
				if (vars.coins.get(i).collision = false)
					vars.coins.get(i).x--;
			}
	
			// jumping
			if (moving_up) {
				
				// move mario
				// update collision box
				vars.mario.y -= jumpRate;
				jumpRate -= 2;
				vars.mario.clipX = 14;
				if (jumpRate == 0) {
					moving_up = false;
					moving_down = true;
				}
			}
			
			if (moving_down) {
				
				// move mario
				vars.mario.y += jumpRate;
				vars.mario.clipX = 8;
	
				jumpRate += 2;
				if (jumpRate > MAX_JUMP_RATE) {
					moving_down = false;
					jumpRate = MAX_JUMP_RATE;
					vars.mario.movingDown = false;
				}
			}
	
			// update coinClip to spin coin
			for (int i = 0; i < vars.coins.size(); i++) {
				if (vars.coins.get(i).clipX < 21) {
					vars.coins.get(i).clipX += 3;
				} else
					vars.coins.get(i).clipX = 1;
			}
			
			//if mario falls off map game is over
			if(vars.mario.y > 445 )
				vars.gameOver = true;
		}

		checkCollisions();
		repaint();
	}

	// check if one object runs into another
	public void checkCollisions() {
		
		// check collision with platforms
		for (int i = 0; i < vars.l.tileMap.size(); i++) {
			vars.mario.collisionR = new Rectangle(vars.mario.x, vars.mario.y,
					vars.mario.marioClip.getWidth(this),
					vars.mario.marioClip.getHeight(this));
			vars.l.tileMap.get(i).collisionR = new Rectangle(
					vars.l.tileMap.get(i).x, vars.l.tileMap.get(i).y,
					vars.l.tileMap.get(i).getImage().getWidth(this),
					vars.l.tileMap.get(i).getImage().getHeight(this));

			//allow for jumping if on cloud
			if (vars.l.tileMap.get(i).collisionR
					.intersects(vars.mario.collisionR)) {
				
				//if marion is on top of platform
				if (vars.mario.y + 40 < vars.l.tileMap.get(i).y) {
					falling = false;
					moving_down = false;
					jumpRate = MAX_JUMP_RATE;
					onPlatform = true;
					break;
				}
			} else {
				falling = true;
				onPlatform = false;
			}

		}

		// check collision with coins and mario, if they collide delete coin,
		// add to marios coins
		for (int i = 0; i < vars.coins.size(); i++) {
			vars.coins.get(i).collisionR = new Rectangle(vars.coins.get(i).x,
					vars.coins.get(i).y,
					vars.coins.get(i).coinClip.getWidth(this),
					vars.coins.get(i).coinClip.getHeight(this));
			
			if (vars.coins.get(i).collisionR.intersects(vars.mario.collisionR)) {
				vars.coins.remove(vars.coins.get(i));
				vars.mario.coins++;
			}
		}
		
		//check collisions with mushrooms, if mario lands on mushroom, remove it, other collisions mean game is over
		for (int i = 0; i < vars.mushrooms.size(); i++) {
			vars.mushrooms.get(i).collisionR = new Rectangle(vars.mushrooms.get(i).x,
					vars.mushrooms.get(i).y,
					vars.mushrooms.get(i).getImage().getWidth(this),
					vars.mushrooms.get(i).getImage().getHeight(this));
			
			if (vars.mushrooms.get(i).collisionR.intersects(vars.mario.collisionR)) {
				if(vars.mario.collisionR.y + vars.mario.collisionR.getHeight()/2 < vars.mushrooms.get(i).collisionR.y)
					vars.mushrooms.remove(vars.mushrooms.get(i));
				else
					vars.gameOver = true;
			}
		}
	}

	// GRAPHICS
	// graphics methods, double buffers by painting everything onto image then
	// drawing it to screen
	public void paint(Graphics g) {
		// set size of window
		setSize(vars.screen_width, vars.screen_height);
		// draw background
		g.drawImage(vars.background.getImage(), vars.background.getX(),
				vars.background.getY(), this);

		// draw mario
		g.drawImage(Mario.marioClip, vars.mario.x, vars.mario.y, this);

		// draw mushrooms
		for (int i = 0; i < vars.mushrooms.size(); i++)
			g.drawImage(vars.mushrooms.get(i).getImage(), vars.mushrooms.get(i)
					.getX(), vars.mushrooms.get(i).getY(), this);

		// draw coins
		for (int i = 0; i < vars.coins.size(); i++) {
			g.drawImage(Coin.coinClip, vars.coins.get(i).getX(), vars.coins
					.get(i).getY(), this);
		}

		// draw marios coin count
		g.drawString("Coins: ", 5, 15);
		g.drawString(Integer.toString(vars.mario.coins), 45, 15);

		// paint level background
		for (int i = 0; i < vars.l.tileMap.size(); i++) {
			g.drawImage(vars.l.tileMap.get(i).getImage(),
					vars.l.tileMap.get(i).x, vars.l.tileMap.get(i).y, this);
		}
		
		//game over message
		if(vars.gameOver == true) {
			g.drawString("Game Over", vars.screen_width/2, vars.screen_height/2);
		}
	}

	// for double buffering
	@SuppressWarnings("deprecation")
	public void update(Graphics g) {
		Image offScreenBuffer = null;
		// create buffer for double buffering if not created
		Graphics gr;
		if (offScreenBuffer == null
				|| (!(offScreenBuffer.getWidth(this) == this.size().width && offScreenBuffer
						.getHeight(this) == this.size().height))) {
			offScreenBuffer = this.createImage(vars.screen_width,
					vars.screen_height);
		}
		gr = offScreenBuffer.getGraphics();
		paint(gr);
		// draw buffer to screen
		g.drawImage(offScreenBuffer, 0, 0, this);
	}
}