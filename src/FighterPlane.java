/*********************************************************************
									FINAL GAME BY										*
																							*
	Ryan Fodor 				Robert Chahanovich					Xiaolu Shi	*
																							*
**********************************************************************/
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.net.*;

public class FighterPlane
{
	// declaration of graphic rendering classes
		protected JFrame frame;
		protected Graphics2D g2d;
	
		//Random int for random coordinates
	Random rand = new Random();

	//declare a toolkit to load images
	Toolkit tk = Toolkit.getDefaultToolkit();
	//Image URL's
		protected Image noFiringImg =  tk.getImage(getURL("PlaceHolderPlane.png"));
		protected Image firingImg =  tk.getImage(getURL("PlaceHolderPlane.png"));
		protected Image isHitImg =  tk.getImage(getURL("PlaceHolderPlane.png"));
		protected Image explodeImg =  tk.getImage(getURL("explosion.png"));
	
	//Sound Clips
		SoundClip shoot = new SoundClip("Bullet.wav");
		SoundClip playerDeath = new SoundClip("Player_Death.wav");
		SoundClip enemyDeath = new SoundClip("Enemy_Death.wav");
		
	//Bullet vector
		protected Vector<Bullet> bullets = new Vector<Bullet>();
	
	//Firing declarations
		protected boolean justFired; 
		protected int gunDelayCount;
	
		protected boolean isHit;
	
		protected int loadSpeed;
	
	//Health and live boolean
		protected boolean alive;
		protected int health;
	
	//Location declarations
	protected Point destination;
	protected Point position;
	protected Point gunPositionOffSet = new Point(0,0);
	
	
	protected Point velocity;
	protected double rotationRate;
	
	protected int currentState;
	
	//Animation declarations
	protected int currentFrame, totalFrames;
	protected int animationDirection;
	protected int frameCount, frameDelay;
	protected int frameWidth, frameHeight, columns;
	protected double moveAngle, faceAngle;
	
	

	// constructors
	protected FighterPlane() 
	{
		alive = true;
		position = new Point(0,0);
		velocity = new Point(0, 0);
		destination = new Point(0,0);
		health = 0;
	}
	
	protected FighterPlane(Point newPoint) 
	{
		alive = true;
		position = newPoint;
		velocity = new Point(0, 0);
		destination = new Point(0,0);
		health = 0;
	}
	
	protected void fireNewBullet(int newX, int newY, int newVelY)
	{

			Bullet newBullet = new Bullet(newX,newY, newVelY);
			bullets.addElement(newBullet);


	}
	
	protected void updateBullets(int range)
	{
		for (int i = 0; i < bullets.size(); i++)
		{
			bullets.elementAt(i).updateBulletLife(range);
			if (!bullets.elementAt(i).alive)
			{
				bullets.removeElementAt(i);
			}
		}
	}
	//gets random point
	protected Point getRandomPoint(int rangeX, int rangeY)
	{
		Point newPoint = new Point(0,0);
		newPoint.x = rand.nextInt(rangeX);
		newPoint.y = rand.nextInt(rangeY);
		return newPoint;
	}
	//Moves objects to destinations found randomly within top 2/3 of screen
	protected void moveTo(Point destination, int speed)
	{
	
		if (position.x == destination.x)
		{
			position.x += 0;
		}
		else if (position.x < destination.x)
		{
			position.x += speed;
		}
		else
		{
			position.x -= speed;
		}
		
		
		if (position.y == destination.y)
		{
			position.y += 0;
		}
		else if (position.y < destination.y)
		{
			position.y += speed;
		}
		else
		{
			position.y -= speed;
		}
	
	}
	
	//Checks arena Bounds
	protected boolean isInBoundary(int rangeX, int rangeY)
	{
		if (position.x+getWidth() > rangeX || position.y+getHeight() > rangeY)
		{
			return false;
		}
		if (position.x < 0 || position.y < 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
    
	//Animation for explosion after death
	public void drawExplosion() 
	{
		//update();
        
		//get the current frame
		int frameX = (currentFrame % columns) * frameWidth;
		int frameY = (currentFrame / columns) * frameHeight;

		//draw the frame 
		g2d.drawImage(explodeImg, position.x, position.y, 6, 5, 
		frameX, frameY, 128, 128, getJFrame());

	}
	
	//this method is used to hang enemies outside of the screen to be used in creating waves
	public void doNothing()
	{
		position = new Point(-100, -100);	
	}
	
	
	// returns a JFrame
	protected JFrame getJFrame() 
	{ 
		return frame; 
	}

	// returns a Graphic2D of the sprite
	protected Graphics2D getGraphics() 
	{
		return g2d; 
	}
	
	// sets the Graphic2D of the sprite
	protected void setGraphics(Graphics2D _g2d) 
	{ 
		g2d = _g2d; 
	}
  

	// gets the width of the frame
	protected int getWidth() 
	{
		return noFiringImg.getWidth(frame);
	}

	// gets the height of the frame	 
	protected int getHeight() 
	{
		return noFiringImg.getHeight(frame);
	}

	// gets the center x point of the frame
	protected double getCenterX() 
	{
		return position.x + getWidth() / 2;
	}
	
	// gets the center y point of the frame
	protected double getCenterY() 
	{
		return position.y + getHeight() / 2;
	}
	
	// gets the center point of the frame
	protected Point getCenter() 
	{
		int x = (int)getCenterX();
		int y = (int)getCenterY();
		return(new Point(x,y));
	}

	// search and load sprite strip
	protected URL getURL(String filename) 
	{
		URL url = null;
		try 
		{
			url = this.getClass().getResource(filename);
		}
		catch (Exception e) 
		{
		}

		return url;
	}

	// Returns the bounds of the current frame as a rectangle shape
	protected Rectangle getBounds() 
	{
		return (new Rectangle((int)position.x, (int)position.y, getWidth(), getHeight()));
	}


	//Changes sprite of object firing
	protected Image getImageFire(boolean justFired) 
	{
		if (justFired)
		{
			return firingImg;
		}
		else
		{
			return noFiringImg;
		}
	}
	
	//Changes sprite of object being hit
	protected Image getImageHit(boolean isHit) 
	{

      
		if (isHit)
		{
			return isHitImg;
		}
		else
		{
			return noFiringImg;
		}
	}

	//check for collision with a rectangular shape
	protected boolean collidesWith(Rectangle rect) 
	{
		return (rect.intersects(getBounds()));
	}
	
	//check for collision with another sprite
	protected boolean collidesWith(AnimatedSprite sprite) 
	{
		return (getBounds().intersects(sprite.getBounds()));
	}
	
	//check for collision with a point
	protected boolean collidesWith(Point point) 
	{
		return (getBounds().contains(point.x, point.y));
	} 
	   
}

