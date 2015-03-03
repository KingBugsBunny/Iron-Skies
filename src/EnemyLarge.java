/*****************************************************

*****************************************************/
import java.awt.*;

public class EnemyLarge extends FighterPlane
{


	// constructor
	public EnemyLarge(Point newPoint) 
	{
		//sets health, gets image URLs for sprites and sets coordinates
		super(newPoint);
		noFiringImg = tk.getImage(super.getURL("EnemyLarge.png"));
		firingImg = tk.getImage(super.getURL("EnemyLarge.png"));
		isHitImg = tk.getImage(super.getURL("EnemyLargeHit.png"));
		health = 300;
	}
	
	//this method fires bullets from the large ship in a wedge pattern
	protected void fireNewBullet(int newX, int newY, int newVelY, int delay)
	{
		if (gunDelayCount != delay)
		{
			gunDelayCount++;
		}
		else
		{
			Bullet newBullet01 = new Bullet(newX+36,newY+61, newVelY, 12, 12);
			bullets.addElement(newBullet01);
			Bullet newBullet02 = new Bullet(newX+80,newY+89, newVelY, 12, 12);
			bullets.addElement(newBullet02);
			Bullet newBullet03 = new Bullet(newX+107,newY+89, newVelY, 12, 12);
			bullets.addElement(newBullet03);
			Bullet newBullet04 = new Bullet(newX+150,newY+61, newVelY, 12, 12);
			bullets.addElement(newBullet04);
			Bullet newBullet05 = new Bullet(newX,newY+43, newVelY, 12, 12);
			bullets.addElement(newBullet05);
			Bullet newBullet06 = new Bullet(newX+188,newY+43, newVelY, 12, 12);
			bullets.addElement(newBullet06);
			
			gunDelayCount = 0;
		}
	}
   

}

