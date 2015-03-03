/*********************************************************************
									FINAL GAME BY										*
																							*
	Ryan Fodor 				Robert Chahanovich					Xiaolu Shi	*
																							*
**********************************************************************/
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

@SuppressWarnings("serial")
public class FinalGame extends Applet implements Runnable, KeyListener 
{

		//the main thread becomes the game loop
	Thread gameloop;

		//use this as a double buffer
	BufferedImage backbuffer;

		//the main drawing object for the back buffer
	Graphics2D g2d;
	
		//the screen size
	final int arenaX = 700;
	final int arenaY = 800;
	
		//player lives, plus the first one. Total of 3.
	int extraLives = 2;	
	
		//Objects
	PlayerFighter player;
	EnemyFighter enemy01;
	EnemyFighter enemy02;
	EnemyLarge boss;
	
	
		//game states
	final int GAME_MENU = 0;
	final int GAME_RUNNING = 1;
	final int GAME_OVER = 2;
	final int GAME_PAUSE = 3;
	final int GAME_WIN = 4;
	int gameState = GAME_MENU;
	
		// Key handling variables
	int keyCode;
	boolean leftPressed, rightPressed;
	boolean upPressed, downPressed;
	boolean spacePressed;

	 //Initialize game
	public void init() 
	{
			//set screen size
		resize(arenaX,arenaY);
			//create the back buffer for smooth graphics
		backbuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		g2d = backbuffer.createGraphics();
		
			// declare and initialize player's fighter plane and position
		player = new PlayerFighter(new Point(275, arenaY - 100));

			// declare and initialize an enemy fighter plane and position
		enemy01 = new EnemyFighter(new Point(200, 150));
			// assign a random point for this plane to move to
		enemy01.destination.setLocation(enemy01.getRandomPoint(arenaX/2, arenaY/2));
		
			// declare and initialize another small enemy fighter plane and position
		enemy02 = new EnemyFighter(new Point(400, 250));
			// assign a random point for this plane to move to
		enemy02.destination.setLocation(enemy02.getRandomPoint(arenaX/2, arenaY/2));
		
			// declare and initialize a big enemy fighter plane and position
		boss = new EnemyLarge(new Point(100, 225));
			// assign a random point for this plane to move to
		boss.destination.setLocation(boss.getRandomPoint(arenaX/2, arenaY/2));
		
			//start the user input listener
		addKeyListener(this);
    }
	 
		// This update method handles all the graphics. 	 
	public void update(Graphics g)
	{	 
			//paint the background blue
		//g2d.setPaint(Color.blue);
		//g2d.fillRect(0, 0, getSize().width, getSize().height);
		g2d.drawImage(player.background, 0, 0, this);
		  
			//what is the game state?
			
			// Menu
		if (gameState == GAME_MENU) 
		{
			g2d.setFont(new Font("Verdana", Font.BOLD, 52));
			g2d.setColor(Color.blue);
			g2d.drawString("Galaxan Clone", 60, 100);
			g2d.setFont(new Font("Ariel", Font.BOLD, 52));
			g2d.setColor(Color.red);
			g2d.drawString("Press ENTER to start", 60, 500);
		}
		
			// Pause screen
		else if(gameState == GAME_PAUSE)
		{
			g2d.setPaint(Color.gray);
			g2d.fillRect(0, 0, getSize().width, getSize().height);
			g2d.setFont(new Font("Verdana", Font.BOLD, 120));
			g2d.setColor(Color.RED);
			g2d.drawString("PAUSED", 80, 300);
		}
			
			// When the player wins
		else if(gameState == GAME_WIN)
		{
			g2d.setFont(new Font("Verdana", Font.BOLD, 24));
			g2d.setColor(Color.YELLOW);
			g2d.drawString("VICTORY!", 100, 200);
		}
		
			// Game Over
		else if(gameState == GAME_OVER)
		{
			g2d.setFont(new Font("Verdana", Font.BOLD, 24));
			g2d.setColor(Color.YELLOW);
			g2d.drawString("GAME OVER", 100, 200);
		}
			// Game running state
		else if (gameState == GAME_RUNNING) 
		{
			drawPlayer();
			drawEnemy();
			drawBoss();
			drawBullet();
			drawInfo();
			
			
			//debug();
		}

		paint(g);
	}
	
	// each of the methods in the GAME_RUNNING state will be defined here:
		
	
	// draw all bullet with the updated data
	public void drawBullet() 
	{
			// player's bullets
		g2d.setColor(Color.orange);
		for (int i = 1; i < player.bullets.size(); i++)
		{

			//Spawns bullets at player's position
			g2d.fillRect(player.bullets.elementAt(i).position.x,
					 		 player.bullets.elementAt(i).position.y,
							 player.bullets.elementAt(i).width,
							 player.bullets.elementAt(i).height);
		}
		
			// Boss's bullets
		g2d.setColor(Color.red);
		for (int i = 1; i < boss.bullets.size(); i++)
		{
			
			//Spawns large oval bullets at large plane's position
			g2d.fillOval(boss.bullets.elementAt(i).position.x,
					 		 boss.bullets.elementAt(i).position.y,
							 boss.bullets.elementAt(i).width,
							 boss.bullets.elementAt(i).height);
		}
		
			// small enemy01's bullets
		g2d.setColor(Color.red);
		for (int i = 1; i < enemy01.bullets.size(); i++)
		{
			//Spawn small bullets at enemy fighter's positions
			g2d.fillRect(enemy01.bullets.elementAt(i).position.x,
					 		 enemy01.bullets.elementAt(i).position.y,
							 enemy01.bullets.elementAt(i).width,
							 enemy01.bullets.elementAt(i).height);
		}
		
			// small enemy02's bullets
		for (int i = 1; i < enemy02.bullets.size(); i++)
		{
			//Spawn small bullets at enemy fighter's positions
			g2d.fillRect(enemy02.bullets.elementAt(i).position.x,
					 		 enemy02.bullets.elementAt(i).position.y,
							 enemy02.bullets.elementAt(i).width,
							 enemy02.bullets.elementAt(i).height);
		}
	}
	//DRAW METHODS
	/* these methods draw if the specific enemy 
	 or player is firing, is hit, or is doing nothing*/
	
		// draws the player's spites
	public void drawPlayer()
	{
		if (player.justFired)
		{	
			// when the player just fired, render the justFired image		
			g2d.drawImage(player.firingImg, player.position.x, player.position.y, this);
			this.player.justFired = false;
		}
			
		if(player.isHit)
		{		
			// when the palyer is hit, render the isHitImg image
			g2d.drawImage(player.isHitImg, player.position.x, player.position.y, this);
			player.isHit = false;
		}

		else
		{
			// render the normal image
			g2d.drawImage(player.noFiringImg, player.position.x, player.position.y, this);
		}	
	}
	
	// renders all the enemies' sprites
	public void drawEnemy()
	{
		//One of the small enemy fighters
		if (enemy01.alive)
		{
			if (enemy01.justFired)
			{
				// if 
				g2d.drawImage(enemy01.getImageFire(enemy01.justFired), enemy01.position.x, enemy01.position.y, this);
				enemy01.justFired = false;
			}
				
			else if (enemy01.isHit)
			{		
				g2d.drawImage(enemy01.getImageHit(enemy01.isHit), enemy01.position.x, enemy01.position.y, this);
				enemy01.isHit = false;
			}
			else
			{
				g2d.drawImage(enemy01.getImageFire(false), enemy01.position.x, enemy01.position.y, this);
			}	
		}
		
		//One of the small enemy fighters
		if (enemy02.alive)
		{
			if (enemy02.justFired)
			{
					
				g2d.drawImage(enemy02.getImageFire(enemy02.justFired), enemy02.position.x, enemy02.position.y, this);
				enemy02.justFired = false;
			}
				
			else if (enemy02.isHit)
			{		
				g2d.drawImage(enemy02.getImageHit(enemy02.isHit), enemy02.position.x, enemy02.position.y, this);
				enemy02.isHit = false;
			}
			else
			{
				g2d.drawImage(enemy02.getImageFire(false), enemy02.position.x, enemy02.position.y, this);
			}	
		}
			
	}
	
	//Draws the large plane's sprites
	public void drawBoss()
	{

		if (boss.alive)
		{
			if (boss.justFired)
			{
					
				g2d.drawImage(boss.getImageFire(boss.justFired), boss.position.x, boss.position.y, this);
				boss.justFired = false;
			}
				
			else if (boss.isHit)
			{		
				g2d.drawImage(boss.getImageHit(boss.isHit), boss.position.x, boss.position.y, this);
				boss.isHit = false;
			}
			else
			{
				g2d.drawImage(boss.getImageFire(false), boss.position.x, boss.position.y, this);
			}	
		}
	}
	
	//Draws the lives graphic at the top left part of the screen
	public void drawInfo()
	{
		for(int i =0; i < extraLives; i++)
		{
			g2d.setColor(Color.GRAY);
			g2d.draw3DRect(10+i*player.getWidth(), 10, player.getWidth(), player.getHeight(), true);
			g2d.fillOval(10+i*player.getWidth(), 10, player.getWidth(), player.getHeight());
			g2d.drawImage(player.noFiringImg, 10+i*player.getWidth(), 10, this);

		}
		
	}
	
	
/*	public void debug()
	{
		g2d.setFont((new Font("Verdana", Font.BOLD, 12)));
		g2d.setColor(Color.RED);
		//Draw boss boolean isHit
		g2d.drawString(player.bullets.size() + "", 350, 350);
		g2d.drawString(enemy02.position + "", 350, 375);
		g2d.drawString(player.health + " , ", 350, 400);
		if (enemy02.alive)
		{
				g2d.drawString(enemy02.health + " , ", 350, 420);
		}

	}
*/	
	public void paint(Graphics g) 
	{

		//draw the back buffer onto the applet window
		g.drawImage(backbuffer, 0, 0, this);

	}
	
	/******************************************************
	* thread start event - start the game loop running		*
	******************************************************/
	public void start() 
	{
		//create the gameloop thread for real-time updates
		gameloop = new Thread(this);
		gameloop.start();
	}
	
	

	/******************************************************
	* 				thread run event (game loop)		  *
	******************************************************/
	public void run() 
	{

		//acquire the current thread
		Thread t = Thread.currentThread();

		//keep going as long as the thread is alive
		while (t == gameloop) 
		{
			try 
			{
				//update the game loop
				gameUpdate();
	
				//target framerate is 50 fps
				Thread.sleep(20);
			}
			catch(InterruptedException e) 
			{
				e.printStackTrace();
			}
			repaint();
		}
	}
	
	/******************************************************
	* 						Thread Stop Event			  *
	******************************************************/
	public void stop() 
	{

		//kill the gameloop thread
		gameloop = null;
	}

	/******************************************************
	*      move and animate the objects in the game		  *
	******************************************************/
	private void gameUpdate() 
	{  
		if (gameState == GAME_RUNNING)
		{
			updatePlayer();
			updateBullet();
			checkCollisions();
			updateEnemies();
		}		
	}
	
	
	/*******************************************
	*					PLAYER 1 		  	   *
	********************************************/
	//This method keeps track of player health & player lives
	public void updatePlayer()
	{
		if (player.health <= 0)
		{
			player.alive = false;
			if (extraLives > 0)
			{
				extraLives--;
				player = new PlayerFighter(new Point(275, arenaY - 100));
			}
			else
			{
				gameState = GAME_OVER;
			}
		}
		// when both button being pressed do nothing
		
	//These structures bound the plane within the arena
		
		// when left being pressed...
		if ((leftPressed)&&(!rightPressed))
		{
			// make sure the player is within the boundary
			if(player.position.x > 0)
			{
				// move player left
				 player.position.x-= 10;
			}
		}

		//if right is pressed
		if ((rightPressed)&&(!leftPressed)) 
		{
			// when the player is within the boundary
			if(player.position.x < getWidth() - player.getWidth())
			{
				// move player right by 2
				 player.position.x += 10;
			}
		} 
		
		// when up being pressed...
		if ((upPressed)&&(!downPressed))
		{
			// make sure the player is within the boundary
			if(player.position.y > arenaY-500)
			{
				// move player left
				 player.position.y+= -10;
			}
		}

		//if down is pressed
		if ((downPressed)&&(!upPressed)) 
		{
			// when the player is within the boundary
			if(player.position.y < arenaY - player.getHeight())
			{
				// move player down
				 player.position.y+= 10;
			}
		} 
	}


	//This method handles placing the bullets at player and enemy positions
	public void updateBullet()
	{
		if (spacePressed)
		{ 
			player.fireNewBullet(player.position.x, player.position.y, 50, 8);
		}
		player.updateBullets(2000);
		for (int i = 1; i < player.bullets.size(); i++)
		{
			player.bullets.elementAt(i).velY = -30;
			player.bullets.elementAt(i).updateY(player.bullets.elementAt(i).velY);
		}	
		
		//Large enemy
		boss.updateBullets(2000);
		for (int i = 1; i < boss.bullets.size(); i++)
		{
			boss.bullets.elementAt(i).velY = 5;
			boss.bullets.elementAt(i).updateY(boss.bullets.elementAt(i).velY);
		}	
		if(boss.alive)
		{
			boss.fireNewBullet(boss.position.x,boss.position.y, 10, 50);
		}
		
		//Small enemy
		enemy01.updateBullets(2000);
		for (int i = 1; i < enemy01.bullets.size(); i++)
		{
			enemy01.bullets.elementAt(i).velY = 5;
			enemy01.bullets.elementAt(i).updateY(enemy01.bullets.elementAt(i).velY);
		}	
		if(enemy01.alive)
		{
			enemy01.fireNewBullet(enemy01.position.x,enemy01.position.y, 40, 20);
		}
		
		//Small enemy
		enemy02.updateBullets(2000);
		for (int i = 1; i < enemy02.bullets.size(); i++)
		{
			enemy02.bullets.elementAt(i).velY = 10;
			enemy02.bullets.elementAt(i).updateY(enemy02.bullets.elementAt(i).velY);
		}	
		if(enemy02.alive)
		{
			enemy02.fireNewBullet(enemy02.position.x,enemy02.position.y, 40, 40);
		}
	}
	
	//This method calls all collisions in exception to arena bounds.
	public void checkCollisions()
	{
		checkCollisionsBulletVsPlanes();
		checkCollisionsPlaneVsPlanes();
	}
	
	//Relevantly named method
	public void checkCollisionsBulletVsPlanes()
	{
		//Fires bullets from large plane
		if (boss.alive)
		{
			for (int i = 1; i < player.bullets.size(); i++)
			{
				if (boss.collidesWith(player.bullets.elementAt(i).getBounds()))
				{
					player.bullets.elementAt(i).alive = false;
					boss.isHit = true;//Changes large enemy's sprite if hit
					boss.health--;
				}
			}
		}
		else
		{
			boss.doNothing();
		}
		
		//Fires bullets from small plane
		if (enemy01.alive)
		
		{
			for (int i = 1; i < player.bullets.size(); i++)
			{
				if (enemy01.collidesWith(player.bullets.elementAt(i).getBounds()))
				{
					player.bullets.elementAt(i).alive = false;
					enemy01.isHit = true;
					enemy01.health -= 3;
				}
			}
		}
		else
		{
			enemy01.doNothing();
		}
		
		//Fires bullets from small plane
		if (enemy02.alive)
		
		{
			for (int i = 1; i < player.bullets.size(); i++)
			{
				if (enemy02.collidesWith(player.bullets.elementAt(i).getBounds()))
				{
					player.bullets.elementAt(i).alive = false;
					enemy02.isHit = true;//Changes small enemy's sprite if hit by bullet
					enemy02.health -= 3;
				}
			}
		}
		else
		{
			enemy02.doNothing();
		}
		
		
		
		if (player.alive)
		{
			for (int i = 1; i < boss.bullets.size(); i++)
			{
				if (player.collidesWith(boss.bullets.elementAt(i).getBounds()))
				{
					boss.bullets.elementAt(i).alive = false;
					player.isHit = true;//Changes player's sprite if hit by bullet
					player.health -= 50;
				}
			}
			
			for (int i = 1; i < enemy01.bullets.size(); i++)
			{
				if (player.collidesWith(enemy01.bullets.elementAt(i).getBounds()))
				{
					enemy01.bullets.elementAt(i).alive = false;
					player.isHit = true;//Changes player's sprite if hit by bullet
					player.health-=5;
				}
			}
			
			//
			for (int i = 1; i < enemy02.bullets.size(); i++)
			{
				if (player.collidesWith(enemy02.bullets.elementAt(i).getBounds()))
				{
					enemy02.bullets.elementAt(i).alive = false;
					player.isHit = true;//Changes small enemy's sprite if hit by bullet
					player.health-=5;
				}
			}
		}
		else
		{
			gameState = GAME_OVER;
		}
	}
	
	//This method prevents planes from clipping through eachother.
	public void checkCollisionsPlaneVsPlanes()
	{
		
		//This method also enables Kamikazi attacks on larger bosses
		if (player.collidesWith(boss.getBounds()))
		{
			player.health -= 150;
			boss.health -= 150;
		}
		
		if (player.collidesWith(enemy01.getBounds()))
		{
			player.health -= 150;
			enemy01.health -= 150;
		}
		
		if (player.collidesWith(enemy02.getBounds()))
		{
			player.health -= 150;
			enemy02.health -= 150;
		}
	
	
	}
	
	//This method keeps track of enemy positions/Movement as well as keeps them in the arena bounds and checks if they are alive
	public void updateEnemies()
	{
		// Large Enemy ship
		
		//displays win state if enemies are all dead // TEMPORARY CODE
		if (!boss.alive)
		{
			if(!enemy01.alive && !enemy02.alive)
			{
				gameState = GAME_WIN;
			}
		}
		
		if (boss.health <= 0)
		{
			boss.alive = false;
		}
		
		if (!boss.isInBoundary(arenaX, arenaY-300))
		{
			boss.destination.setLocation(boss.getRandomPoint(arenaX - boss.getWidth(), arenaY-300));
		}
		
		//Moves large plane randomly around arena
		if (boss.position.x == boss.destination.x	&&
				boss.position.y== boss.destination.y)
		{
			boss.destination.setLocation(boss.getRandomPoint(arenaX, arenaY-300));
		}
		else 
		{
			boss.moveTo(boss.destination,1);
		}	
		
		// Small Enemy Ship
		if (enemy01.health <= 0)
		{
			enemy01.alive = false;
		}
		
		//Moves small plane randomly around arena
		if (!enemy01.isInBoundary(arenaX, arenaY-300))
		{
			enemy01.destination.setLocation(enemy01.getRandomPoint(player.position.x, arenaY-300));
		}
	
		if (enemy01.position.x == enemy01.destination.x	&&
				enemy01.position.y== enemy01.destination.y)
		{
			enemy01.destination.setLocation(enemy01.getRandomPoint(player.position.x, arenaY-300));
		}
		else 
		{
			enemy01.destination.x = player.position.x;
			enemy01.moveTo(enemy01.destination,1);
			enemy01.moveTo(enemy01.destination,1);	//Multiple calls made to ensure code is working 
			enemy01.moveTo(enemy01.destination,1);
		}	
		// Small Enemy Ship
		if (enemy02.health <= 0)
		{
			enemy02.alive = false;
		}
		
		//Moves small plane randomly around arena
		if (!enemy02.isInBoundary(arenaX, arenaY-300))
		{
			enemy02.destination.setLocation(enemy02.getRandomPoint(player.position.x, arenaY-300));
		}
	
		if (enemy02.position.x == enemy02.destination.x	&&
				enemy02.position.y== enemy02.destination.y)
		{
			enemy02.destination.setLocation(enemy02.getRandomPoint(player.position.x, arenaY-300));
		}
		else 
		{
			enemy02.destination.x = player.position.x;
			enemy02.moveTo(enemy02.destination,1);
			enemy02.moveTo(enemy02.destination,1);	//Multiple calls made to ensure code is working 
			enemy02.moveTo(enemy02.destination,1);
		}	
	}

	//KEYLISTENERS
	public void keyReleased(KeyEvent k) 
	{ 
		//Booleans are used to flag if certain keys are
		switch (k.getKeyCode()) 
		{
			case KeyEvent.VK_LEFT:  
				leftPressed=false; 
			break;
			
			case KeyEvent.VK_RIGHT: 
				rightPressed=false; 
			break;
			
			case KeyEvent.VK_UP:  
				upPressed=false; 
			break;
			
			case KeyEvent.VK_DOWN: 
				downPressed=false; 
			break;
			
			case KeyEvent.VK_SPACE: 
				spacePressed=false;
				player.justFired = false;				 
				
			break;
		}
	}
	
	public void keyTyped(KeyEvent k) {}
	
	
	public void keyPressed(KeyEvent k) 
	{
		
		keyCode = k.getKeyCode();
		//Booleans are used to flag if certain keys are pressed
		switch (keyCode) 	
		{
			
			case KeyEvent.VK_LEFT:
				leftPressed = true;
			break;

			case KeyEvent.VK_RIGHT:
				rightPressed = true;
			break;
			
			case KeyEvent.VK_UP:
				upPressed = true;
			break;

			case KeyEvent.VK_DOWN:
				downPressed = true;
			break;
			
			case KeyEvent.VK_SPACE: 
				spacePressed = true;

			break;
				
			case KeyEvent.VK_ENTER:
				if (gameState == GAME_MENU || gameState == GAME_PAUSE)
				{
					gameState = GAME_RUNNING;
				} 
				if (gameState == GAME_OVER || gameState == GAME_WIN)
				{
					gameState = GAME_MENU;
				}
				repaint();
				break;
					 
			case KeyEvent.VK_ESCAPE:
				if(gameState == GAME_RUNNING)
				{
					gameState = GAME_PAUSE;
					repaint();
				}
			break;
		}
		 
	}
	
	
	
}