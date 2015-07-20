package Game;

import GameObjects.*;
import Levels.World;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.LinkedList;


public class Handler{
	
	static KeyInput keyinput;
	static int gridSize = LoreCraft.GRIDSIZE;
	static ArrayList<Entity> EntityList = new ArrayList<Entity>();
	
	public Handler(KeyInput keyinput){
		Handler.keyinput = keyinput;
	}
	
	static ArrayList<GameObject> objectlist = new ArrayList<GameObject>();
	public static AffineTransform windowTform = new AffineTransform();
	public static double viewWScale = 0.2, viewHScale = 0.2;
	public static ID viewTarget = ID.Player;
	public static double windowX = 0, windowY = 0, windowWidth, windowHeight;
	static boolean didtick = false;
	
	public static void tick(){
		windowWidth=LoreCraft.WIDTH/viewWScale;
		windowHeight=LoreCraft.HEIGHT/viewHScale;
		for(int i = 0; i < objectlist.size(); i++){
			GameObject tempobject = objectlist.get(i);
			tempobject.tick(keyinput);
			if(tempobject.id == viewTarget){
				//windowX=tempobject.getX()*gridSize-windowWidth/2;
				//windowY=tempobject.getY()*gridSize-windowHeight/2;
			}
		}
	}
	
	public static void render(Graphics2D g2){
		windowTform.setToIdentity();
		windowTform.scale(viewWScale, viewHScale);
		windowTform.translate(-windowX,-windowY);
		g2.setTransform(Handler.windowTform);
		/*g2.setColor(new Color(0.5F, 0.5F, 0.5F, 1F));
		for(int i = 0; i<HelloWorld.currentLevel.Width ; i = i+200){
			g2.drawLine(i,0,i,HelloWorld.currentLevel.Height);
		}
		for(int j = 0; j<HelloWorld.currentLevel.Height ; j = j+200){
			g2.drawLine(0,j,HelloWorld.currentLevel.Width,j);
		}*/
		
		LoreCraft.currentLevel.render(g2);
		g2.setColor(Color.WHITE);
		String debugstr = Boolean.toString(didtick);
		g2.setFont(new Font("Serif",Font.PLAIN,12));
		g2.drawString(debugstr, 16, 16);
		didtick=false;
		
		for(int i = 0; i < objectlist.size(); i++){
			GameObject tempobject = objectlist.get(i);
			/*if(!(tempobject.getX()*gridSize<windowX) && !(tempobject.getX()*gridSize>windowX+windowWidth)
					&& !(tempobject.getY()*gridSize<windowY) && !(tempobject.getY()*gridSize>windowY+windowHeight)){
				tempobject.render(g2);
			}8]*/
		}
		g2.setColor(Color.WHITE);
		/*String debugstr = Integer.toString(objectlist.size());
		g2.setFont(new Font("Serif",Font.PLAIN,12));
		g2.drawString(debugstr, 16, 16);*/
		
	}
	
	public static void addObject(GameObject newObject){
		objectlist.add(newObject);
	}
	public static void removeObject(GameObject oldObject){
		objectlist.remove(oldObject);
		oldObject = null;
	}
	
	public static void addEntity(GameObject newObject, World world, int x, int y){
		
	}
	
	

	public static LinkedList<GameObject> findObject(ID checkID){
		LinkedList<GameObject> objList = new LinkedList<GameObject>();
		for(GameObject tempObj : objectlist){
			if(tempObj.id == checkID){
				objList.add(tempObj);
			}
		}
		return objList;
	}
}
