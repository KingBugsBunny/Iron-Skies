/*********************************************************************
									FINAL GAME BY										*
																							*
	Ryan Fodor 				Robert Chahanovich					Xiaolu Shi	*
																							*
**********************************************************************/
import java.awt.*;

public class EnemyFighter extends FighterPlane
{
	// constructor
	public EnemyFighter(Point newPoint) 
	{
		//sets health, gets image URLs for sprites and sets coordinates
		super(newPoint);
		noFiringImg = tk.getImage(super.getURL("EnemyFighter.png"));
		firingImg = tk.getImage(super.getURL("EnemyFighterJustFired.png"));
		isHitImg = tk.getImage(super.getURL("EnemyFighterHit.png"));
		health = 150;
	}	   
	
	protected void fireNewBullet(int newX, int newY, int newVelY, int delay)
	{
		if (gunDelayCount != delay)
		{
			gunDelayCount++;
		}
		else
		{
		
			Bullet newBullet01 = new Bullet(newX+14,newY+10, newVelY, 3, 10);
			bullets.addElement(newBullet01);
			//Bullet newBullet02 = new Bullet(newX+18,newY+10, newVelY);
			//bullets.addElement(newBullet02);
			//Bullet newBullet03 = new Bullet(newX+55,newY+10, newVelY);
			//bullets.addElement(newBullet03);
			Bullet newBullet04 = new Bullet(newX+59,newY+10, newVelY, 3, 10);
			bullets.addElement(newBullet04);
			justFired = true;
			gunDelayCount = 0;
		}
	}
	//Gets a random point
	protected Point getRandomPoint(int playerX, int rangeY)
	{
		Point newPoint = new Point(0,0);
		newPoint.x = playerX;
		newPoint.y = rand.nextInt(rangeY);
		return newPoint;
	}
}

