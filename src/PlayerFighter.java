/*********************************************************************
									FINAL GAME BY										*
																							*
	Ryan Fodor 				Robert Chahanovich					Xiaolu Shi	*
																							*
**********************************************************************/
import java.awt.*;

public class PlayerFighter extends FighterPlane
{
	protected Image background = tk.getImage(super.getURL("space.jpg"));
	
	// constructor
	public PlayerFighter(Point newPoint) 
	{
		super(newPoint);
		noFiringImg = tk.getImage(super.getURL("PlayerFighter.png"));
		firingImg = tk.getImage(super.getURL("PlayerFighterFiring.png"));
		isHitImg = tk.getImage(super.getURL("PlayerFighterHit.png"));
		gunPositionOffSet = new Point(36,-15);
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
			Bullet newBullet01 = new Bullet(newX+14,newY+10, newVelY);
			bullets.addElement(newBullet01);
			shoot.play();
			Bullet newBullet02 = new Bullet(newX+18,newY+10, newVelY);
			bullets.addElement(newBullet02);
			shoot.play();
			Bullet newBullet03 = new Bullet(newX+55,newY+10, newVelY);
			bullets.addElement(newBullet03);
			shoot.play();
			Bullet newBullet04 = new Bullet(newX+59,newY+10, newVelY);
			bullets.addElement(newBullet04);
			
			justFired = true;
			shoot.play();
			gunDelayCount = 0;
		}
	}
	   
}

