package GameObjects;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import Game.Handler;
import Game.KeyInput;
import Game.ID;
import Game.LoreCraft;

public abstract class GameObject {
	

	public ID id;
	protected BufferedImage mySprite;
	
	public boolean collidable;

	
	public AffineTransform myTform = new AffineTransform();
	
	public GameObject(int newx, int newy, ID newid){ //constructor
		
		id=newid;
		

	}
	
	public void tick(KeyInput keyinput){
		
		/*myTform.setToIdentity();
		myTform.rotate(angle, x, y);
		myTform.translate(x, y);
		myTform.scale(scaleX, scaleY);*/
	}
	
	public void destroy(){
		Handler.removeObject(this);
	}
	
	
	public void render(Graphics2D g2){//render happens on the global canvas.
		//g2.drawImage(mySprite,null,x*LoreCraft.GRIDSIZE,y*LoreCraft.GRIDSIZE);

	};
	
	
	public void setLocation(int newX, int newY, boolean relative){

	}
	
	
}