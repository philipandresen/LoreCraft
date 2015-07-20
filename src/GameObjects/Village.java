package GameObjects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Game.ID;

public class Village extends GameObject{
	
	
	public Village(int newx, int newy, ID newid){
		super(newx,newy,newid);
		collidable = false;
		String sourcePath = "F:\\eclipse\\Workspace\\LoreCraft\\Graphics\\village1.png";
		try {
			mySprite = ImageIO.read(new File(sourcePath));
		} catch (IOException e){
			System.out.println(e.toString());
		}
	}
}
