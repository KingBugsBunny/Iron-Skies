/*********************************************************************
									FINAL GAME BY										*
																							*
	Ryan Fodor 				Robert Chahanovich					Xiaolu Shi	*
																							*
**********************************************************************/
import java.awt.*;

public class Bullet 
{
	// a Point for position
	protected Point position = new Point(0,0);	
	
	// height and width for size
	protected int height =3;
	protected int width = 2;
	
	// 2 ints for the velocity
	protected int velX=0;
	protected int velY=0;
	
	protected int mileage = 0;

	protected boolean wasFired;
	protected boolean hitEnemy;
	protected boolean alive;

	
	// CONSTRUCTORS
	
	
	protected Bullet(int newX, int newY, int newVelY, int _width, int _height)
	{
		
		// set the x and y position
		position.x = newX;
		position.y = newY;
		velY = newVelY;
		width = _width;
		height = _height;
		alive = true;
	}
	
	
	
	protected Bullet(int newX, int newY, int newVelY)
	{
		
		// set the x and y position
		position.x = newX;
		position.y = newY;
		velY = newVelY;
		alive = true;
	}


	protected Bullet()
	{
		alive = true;
	}
	
	/*This method checks if the bullet is alive, if the bullet 
	hits a plane or goes off screen it is removed from the array List */
	protected void updateBulletLife(int range)
	{
		if(mileage > range)
		{
			alive = false;
		}
		if(hitEnemy)
		{
			alive = false;
		}
	}
	
	// set and get methods of x 
	protected void setX(int x)
	{
		position.x = x;
	} 
	 
	 // increment methods for x and y position
	public void updateX(int newVelX)
	{
	 	position.x += newVelX;
		mileage += Math.abs(newVelX);
	}
	 
	public void updateY(int newVelY)
	{
	 	position.y += newVelY;
		mileage += Math.abs(newVelY);
	}
	 
	 // increment methods for velocities
	 public void incVelX(int i)
	 {
	 	velX += i;
	 }
	 
	 public void incVelY(int i)
	 {
	 	velY += i;
	 }
	 
	 //return bounding rectangle
   public Rectangle getBounds() 
	{
		Rectangle r;
		r = new Rectangle(position.x, position.y, width, height);
		return r;
    }

	
}
	 
	 	 	

