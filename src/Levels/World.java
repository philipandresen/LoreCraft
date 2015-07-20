package Levels;

import java.awt.Graphics2D;
import java.util.ArrayList;

import tileSets.Tile;
import tileSets.TileSet;
import Game.ArrayList3D;
import Game.Direction;
import Game.Handler;
import Game.ID;
import Game.LoreCraft;
import GameObjects.Player;
import GameObjects.Village;

public class World{
	
	protected int worldSize;
	protected ArrayList<ArrayList<WorldSquare>> WorldGrid = new ArrayList<ArrayList<WorldSquare>>();
	private String sourceFile = "grayscaleHeightmap.png";
	public static int tileSize = 16;
	protected TileSet myTileSet;
	protected int worldSquareSize;
	private int waterHeight = 100;
	
	//TODO : Find a way to organize entities on the world map. Will there be enough to really slow the game down?
	//Will entities even need to render if they are away from the player? Probably not on overworld.
	//How about only if they are detected? That could be useful. Related to size of entity.
	public ArrayList3D<Tile> EntityList;
	
	public World(int ws, int wss, TileSet ts, int levelTier, boolean randomFill){
		myTileSet = ts;
		worldSize = ws;
		worldSquareSize = wss;
		myTileSet.loadTiles();
		Integer[] borderN = new Integer[worldSquareSize];
		Integer[] borderS = new Integer[worldSquareSize];
		Integer[] borderE = new Integer[worldSquareSize];
		Integer[] borderW = new Integer[worldSquareSize];
		Integer centerPoint = new Integer(1);
		
		for (int x = 0 ; x < worldSize; x++){
			WorldGrid.add(new ArrayList<WorldSquare>());
		}
		
		//borderN[0] = 0;
		//borderN[worldSquareSize-1] = 0;
		
		if (randomFill){
			Handler.addObject(new Player(100,100,ID.Player));
			Handler.addObject(new Village(100,110,ID.Village));
			
			for (int x = 0 ; x < worldSize; x++){
				for (int y = 0; y<worldSize; y++){
					//transfer border from previous squares for smooth terrain transitions.
					if (y>0){
						if (WorldGrid.get(x).get(y-1) != null) {
							Integer[][] borders = WorldGrid.get(x).get(y-1).getTerrainBorders();
							borderN = borders[1];
						}
					}
					if (x>0) {
						if (WorldGrid.get(x-1).get(y) != null) {
							Integer[][] borders = WorldGrid.get(x-1).get(y).getTerrainBorders();
							borderW = borders[2];
						}
					}
					//generate Square///////////////////////////
					generateSquare(x,y,borderN, borderS, borderE, borderW, centerPoint, false, 500);
				}
			}
			//Add a pathfinder that will help in making landmarks and roads.
			//new TerrainOrganizer(this);
		}
	}
	
	public void render(Graphics2D g2){
		for (int x = 0 ; x < worldSize; x++){
			for (int y = 0; y<worldSize; y++){
				WorldGrid.get(x).get(y).render(g2);
			}
		}
	}
	
	public void tick(){
		for (int x = 0 ; x < worldSize; x++){
			for (int y = 0; y<worldSize; y++){
				WorldGrid.get(x).get(y).tick();
			}
		}
	}
	
	public Integer[][] getMasterTerrain(){
		Integer[][] masterTerrainGrid = new Integer[worldSize*worldSquareSize][worldSize*worldSquareSize];
		Integer[][] currentTerrain;
		for (int i = 0 ; i<worldSize ; i++){
			for (int j = 0; j< worldSize ; j++){
				currentTerrain = WorldGrid.get(i).get(j).getTerrain();
				for (int i2 = 0 ; i2 < worldSquareSize; i2++){
					for (int j2 = 0 ; j2 < worldSquareSize; j2++){
					masterTerrainGrid[i*worldSquareSize+i2][j*worldSquareSize+j2] = currentTerrain[i2][j2];
					}
				}
			}
		}
		return masterTerrainGrid;
		
	}
	
	public int getWaterHeight(){
		return waterHeight;
	}

	public void setTile(int newX, int newY, int tileX, int tileY){
		int subX = newX%worldSquareSize;
		int subY = newY%worldSquareSize;
		Tile T = myTileSet.getTile(tileX, tileY);
		getWorldSquare(newX,newY).setTile(subX,subY,T);
	}
	
	public WorldSquare getWorldSquare(int x, int y){
		int subX = x%worldSquareSize;
		int subY = y%worldSquareSize;
		int majorGridX = (x-subX)/worldSquareSize;
		int majorGridY = (y-subY)/worldSquareSize;
		return WorldGrid.get(majorGridX).get(majorGridY);
	}
	
	public void refreshAll(){
		for (int x = 0 ; x < worldSize; x++){
			for (int y = 0; y<worldSize; y++){
				WorldGrid.get(x).get(y).refreshSquare();
			}
		}
	}
	
	public WorldSquare generateSquare(int x, int y, Integer[] borderN,Integer[] borderS,Integer[] borderE,Integer[] borderW, 
			Integer Center, boolean wrap, int noise){
		WorldSquare S = new WorldSquare(x,y,worldSquareSize,worldSquareSize,myTileSet);
		if (borderN[0]!=null) S.myGen.setBorder(borderN, Direction.North);
		if (borderS[0]!=null) S.myGen.setBorder(borderS, Direction.South);
		if (borderE[0]!=null) S.myGen.setBorder(borderE, Direction.East);
		if (borderW[0]!=null) S.myGen.setBorder(borderW, Direction.West);
		S.generateTerrain(wrap, noise);
		WorldGrid.get(x).add(y,S);
		return S;
	}
	
	public void Zoom(int pX, int pY){
		MicroWorld miniWorld = new MicroWorld(1, 65, myTileSet,1,false);
		Integer hN = null, hS = null, hE = null, hW = null, centerPoint = null;
		Integer[][] alts = getMasterTerrain();
		centerPoint = alts[pX][pY];
		if (pY>0) hN = alts[pX][pY-1];
		if (pY<alts.length-2) hS = alts[pX][pY+1];
		if (pX>0) hW = alts[pX-1][pY];
		if (pX<alts.length-2) hE = alts[pX+1][pY];
		Integer[] borderN = new Integer[worldSquareSize];
		Integer[] borderS = new Integer[worldSquareSize];
		Integer[] borderE = new Integer[worldSquareSize];
		Integer[] borderW = new Integer[worldSquareSize];
		for (int i = 0 ; i<worldSquareSize; i++){
			borderN[i] = hN;
			borderS[i] = hS;
			borderE[i] = hE;
			borderW[i] = hW;
		}
		miniWorld.generateSquare(0,0,borderN,borderS,borderE,borderW,centerPoint, false, 0);
		
		
		System.out.println(hN);
		System.out.println(hS);
		
		LoreCraft.cachedLevel = this;
		LoreCraft.currentLevel = miniWorld;
	}

	
}
