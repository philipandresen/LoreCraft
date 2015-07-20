package GameObjects;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Game.ID;
import Game.KeyInput;
import Game.LoreCraft;

public class Player extends GameObject{
	
	public Player(int newx, int newy, ID newid){
		super(newx, newy, newid);
		collidable = true;
		String sourcePath = "F:\\eclipse\\Workspace\\LoreCraft\\Graphics\\mans.png";
		try {
			mySprite = ImageIO.read(new File(sourcePath));
		} catch (IOException e){
			System.out.println(e.toString());
		}
	}
	
	public void tick(KeyInput keyinput){
		//System.out.println("OH I AM MOST DEFINITELY HERE");
		if(keyinput.justPressed(KeyEvent.VK_LEFT)){
			//x -= 1;
		}
		if(keyinput.justPressed(KeyEvent.VK_RIGHT)){
			//x += 1;
		}
		if(keyinput.justPressed(KeyEvent.VK_UP)){
			//y -= 1;
		}
		if(keyinput.justPressed(KeyEvent.VK_DOWN)){
			//y += 1;
		}
		
		if (keyinput.justPressed(KeyEvent.VK_ENTER)){
			//LoreCraft.currentLevel.Zoom(x,y);
		}
	}

}
