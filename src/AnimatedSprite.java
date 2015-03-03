/*********************************************************************
								FINAL GAME BY						 *
																	 *
	Ryan Fodor 				Robert Chahanovich			 Xiaolu Shi	 *
																	 *
**********************************************************************/


/*****************************************************
* This class is implemented from the student file/CSC200/Source/Chapter 7
* No modification was made by me.
* Beginning Java Game Programming, 3rd Edition
* by Jonathan S. Harbour
* AnimatedSprite class
*****************************************************/
import java.awt.*;
import java.net.*;
import java.applet.*;

public class AnimatedSprite 
{
	// declaration of graphic rendering classes
	protected Applet applet;
	protected Graphics2D g2d;

	// declaration of variables
	public Image image;
	public boolean alive;
	public Point position;
	public Point velocity;
	public double rotationRate;
	public int currentState;
	public int currentFrame, totalFrames;
	public int animationDirection;
	public int frameCount, frameDelay;
	public int frameWidth, frameHeight, columns;
	public double moveAngle, faceAngle;

	// constructor
	public AnimatedSprite(Applet _applet, Graphics2D _g2d) 
	{
		applet = _applet;
		g2d = _g2d;
		image = null;
		alive = true;
		position = new Point(0, 0);
		velocity = new Point(0, 0);
		rotationRate = 0.0;
		currentState = 0;
		currentFrame = 0;
		totalFrames = 1;
		animationDirection = 1;
		frameCount = 0;
		frameDelay = 0;
		frameWidth = 0;
		frameHeight = 0;
		columns = 1;
		moveAngle = 0.0;
		faceAngle = 0.0;
	}
    
	// returns an applet
	public Applet getApplet() 
	{ 
		return applet; 
	}

	// returns a Graphic2D of the sprite
	public Graphics2D getGraphics() 
	{
		return g2d; 
	}
	
	// sets the Graphic2D of the sprite
	public void setGraphics(Graphics2D _g2d) 
	{ 
		g2d = _g2d; 
	}
  
	// sets the sprite strip to an image
	public void setImage(Image _image) 
	{ 
		image = _image; 
	}

	// gets the width of the frame
	public int getWidth() 
	{
		if (image != null)
            return image.getWidth(applet);
        else
            return 0;
    }

	// gets the height of the frame	 
	public int getHeight() 
	{
		if (image != null)
		{
            return image.getHeight(applet);
		}
		else
		{
            return 0;
		}
	}

	// gets the center x point of the frame
	public double getCenterX() 
	{
		return position.x + getWidth() / 2;
	}
	
	// gets the center y point of the frame
	public double getCenterY() 
	{
		return position.y + getHeight() / 2;
	}
	
	// gets the center point of the frame
	public Point getCenter() 
	{
		int x = (int)getCenterX();
		int y = (int)getCenterY();
		return(new Point(x,y));
	}

	
	//SET METHODS
	public void setPos(int x, int y)
	{
		
		position = new Point(x,y);

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
	public Rectangle getBounds() 
	{
		return (new Rectangle((int)position.x, (int)position.y, getWidth(), getHeight()));
	}

	//To load sprite strip, columns, total frames, width, and height.
	public void load(String filename, int _columns, int _totalFrames, int _width, int _height)
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		image = tk.getImage(getURL(filename));
		while(image.getWidth(applet) <= 0);
		columns = _columns;
		totalFrames = _totalFrames;
		frameWidth = _width;
		frameHeight = _height;
	}


// update position, rotation and animation.
	protected void update() 
	{
	 
		//update position, position add velocity.
		position.x += velocity.x;
		position.y += velocity.y;
    
		//update rotation
		if (rotationRate > 0.0) 
		{
			faceAngle += rotationRate;
			if (faceAngle < 0)
			{
				faceAngle = 360 - rotationRate;
			}
			else if (faceAngle > 360)
			{
				faceAngle = rotationRate;
			}
		}
            
		//update animation
		if (totalFrames > 1) 
		{
			frameCount++;
			if (frameCount > frameDelay) 
			{
				frameCount = 0;
				currentFrame += animationDirection;
				if (currentFrame > totalFrames - 1) 
				{
					currentFrame = 0;
				}
				else if (currentFrame < 0) 
				{
					currentFrame = totalFrames - 1;
				}
			}
		}
	}

    //draw bounding rectangle around sprite
	public void drawBounds(Color c) 
	{
		g2d.setColor(c);
		g2d.draw(getBounds());
	}
	
	//Draws animation
	public void draw() 
	{
		update();
        
		//get the current frame
		int frameX = (currentFrame % columns) * frameWidth;
		int frameY = (currentFrame / columns) * frameHeight;

		//draw the frame 
		g2d.drawImage(image, position.x, position.y, position.x+frameWidth, position.y+frameHeight, 
		frameX, frameY, frameX+frameWidth, frameY+frameHeight, getApplet());

	}

	//check for collision with a rectangular shape
	public boolean collidesWith(Rectangle rect) 
	{
		return (rect.intersects(getBounds()));
	}
	
	//check for collision with another sprite
	public boolean collidesWith(AnimatedSprite sprite) 
	{
		return (getBounds().intersects(sprite.getBounds()));
	}
	
	//check for collision with a point
	public boolean collidesWith(Point point) 
	{
		return (getBounds().contains(point.x, point.y));
	} 
	   
}

