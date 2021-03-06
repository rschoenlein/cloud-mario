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

import javax.swing.Timer;

@SuppressWarnings("serial")
public class Run extends Applet implements Global_Variables, ActionListener,
		KeyListener {

	@SuppressWarnings("unused")
	private boolean collision = false, pressed_left = false,
			pressed_right = false, pressed_up = false, pressed_down = false, pressed_space = false;
	private Timer gameTimer;


	public static void main(String[] args) throws IOException {
		new Run();
	}

	public Run() throws IOException {
		
		// set background Color
		setBackground(Color.CYAN);
		
		// create characters and tileMap 
		vars.level = new Level_Generator();
		
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
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT)
			pressed_left = true;
		if (key == KeyEvent.VK_RIGHT)
			pressed_right = true;
		if (key == KeyEvent.VK_UP) {
			vars.mario.y = 10;
			pressed_up = true;
		}
			
		if (e.getKeyChar() == KeyEvent.VK_SPACE) {
			pressed_space = true;
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
		if (key == KeyEvent.VK_SPACE)
			pressed_space = false;
	}

	// PERFORM EVERY TIMER INTERVAL
	@Override
	public void actionPerformed(ActionEvent e) {
	
		if(!vars.gameOver) {
			// apply gravity
			if (vars.mario.falling)
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
					for (int i = 0; i < vars.level.platforms.size(); i++)
						vars.level.platforms.get(i).scroll();
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
			
			if(pressed_space) {
				//only allow jumping while on platform
				if(vars.mario.onPlatform) {
					vars.mario.movingUp = true;
					vars.mario.movingDown = false;
					vars.mario.falling = false;
				}
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
	
			// MARIO JUMPING SEQUENCE
			if (vars.mario.movingUp) {
				
				// move mario and change clip
				// update collision box
				vars.mario.y -= vars.mario.getJumpRate();
				vars.mario.setJumpRate(vars.mario.getJumpRate() - 2);
				vars.mario.clipX = 14;
				if (vars.mario.getJumpRate() == 0) {
					vars.mario.movingUp = false;
					vars.mario.movingDown = true;
				}
			}
			
			if (vars.mario.movingDown) {
				
				// move mario
				vars.mario.y += vars.mario.getJumpRate() ;
				vars.mario.clipX = 8;
				//set mario clip
	
				vars.mario.setJumpRate(vars.mario.getJumpRate() + 2);
				
				if (vars.mario.getJumpRate() > vars.mario.MAX_JUMP_RATE) {

					vars.mario.setJumpRate(vars.mario.MAX_JUMP_RATE);
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
			if(vars.mario.y > vars.screen_height )
				vars.gameOver = true;
		}
		
		checkCollisions();
		repaint();
	}

	// check if one object runs into another
	public void checkCollisions() {
		
		// check collision with platforms
		for (int i = 0; i < vars.level.platforms.size(); i++) {
			vars.mario.collisionR = new Rectangle(vars.mario.x, vars.mario.y,
					vars.mario.marioClip.getWidth(this),
					vars.mario.marioClip.getHeight(this));
			vars.level.platforms.get(i).collisionR = new Rectangle(
					vars.level.platforms.get(i).x, vars.level.platforms.get(i).y,
					vars.level.platforms.get(i).getWidth(),
					vars.level.platforms.get(i).getImage().getHeight(this));
		
			
			boolean collision = vars.level.platforms.get(i).collisionR
					.intersects(vars.mario.collisionR);

			//ALLOW MARIO TO JUMP IF ON PLATFORM
			if (collision) {
				//if mario is on top of platform
				if (vars.mario.y <  vars.level.platforms.get(i).y) {
					vars.mario.falling = false;
					vars.mario.movingDown = false;
					vars.mario.setJumpRate(vars.mario.MAX_JUMP_RATE);
					vars.mario.onPlatform = true;
					
					break;
				}
			} else {
				
				vars.mario.falling = true;
				vars.mario.onPlatform = false;
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
		for (int i = 0; i < vars.level.platforms.size(); i++) {
			for(int j = 0; j < vars.level.platforms.get(i).tiles.size(); j++)
				g.drawImage(vars.level.platforms.get(i).tiles.get(j).getImage(),
					vars.level.platforms.get(i).tiles.get(j).x, vars.level.platforms.get(i).y, this);
		}
		
		//game over message
		if(vars.gameOver == true) {
			g.drawString("Game Over", vars.screen_width/2, vars.screen_height/2);
		}
	}

	//double buffering
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

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}