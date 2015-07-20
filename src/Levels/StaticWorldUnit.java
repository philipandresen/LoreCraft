package Levels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Game.ArrayList3D;
import Game.Direction;
import Game.Handler;
import terrainGeneration.DiamondSquareGenerator;
import tileSets.Tile;
import tileSets.TileSet;

public class StaticWorldUnit {

	public int Width;
	public int Height;
	public int imageWidth, imageHeight;
	protected BufferedImage levelImage;
	protected Integer[][] myTerrain;
	public DiamondSquareGenerator myGen;
	protected int worldX, worldY;
	public ArrayList3D<Tile> levelMatrix;
	protected TileSet myTileSet;

	//public Tile levelMatrix[][] = new Tile[Width][Height];

	//private TileSet myTileSet;

	protected int limits[];
	Graphics gLevel;

	public StaticWorldUnit(int wx, int wy, int newWidth, int newHeight, TileSet newTiles){
		Width = newWidth;
		Height = newHeight;
		worldX = wx*Width*World.tileSize;
		worldY = wy*Height*World.tileSize;
		myGen = new DiamondSquareGenerator(Width);
		myTileSet = newTiles;
		limits = newTiles.getDims();
		imageWidth = Width*limits[0];
		imageHeight = Height*limits[0];
		levelMatrix = new ArrayList3D<Tile>(Width,Height);

	}

	public void refreshSquare(){

		///create the level image - draw all tiles and turn it into an image.
		levelImage = new BufferedImage(imageWidth,imageHeight,
				BufferedImage.TYPE_4BYTE_ABGR);
		gLevel = levelImage.getGraphics();

		for (int i = 0; i< levelMatrix.getDims() ; i++) {
			for (int j = 0 ; j<levelMatrix.getDims(i) ; j++) {
				for (int k = 0 ; k< levelMatrix.getDims(i,j) ; k++){
					Tile tempT = levelMatrix.get3D(i,j,k);
					gLevel.drawImage(tempT.getImage(), (limits[0])*i, (limits[0])*j, null);
				}
			}
		}
		gLevel.dispose();
	}


	//The number in the LevelMatrix corresponds to a tile in a tileset

	public void render(Graphics2D g2) {
		g2.drawImage(levelImage, null,worldX,worldY);
	}

	public void generateTerrain(boolean wrap, int noise){
		myTerrain = myGen.newTerrain(wrap,noise);
		generateTiles(myTileSet);
	}

	protected void generateTiles(TileSet newTiles){
		System.out.println(myTerrain.length);
		int maxval = myTerrain[0][0];
		int minval = maxval;
		minval = -500;
		maxval = 500;
		/*
			for (Integer[] row : myTerrain){
				for (int val : row){
					if (val<minval){
						minval = val;
					}
					if (val > maxval){
						maxval = val;
					}
				}
			}*/


		int numtiles = newTiles.getDims()[1];
		int divisions[] = new int[numtiles];
		int tileSelector = 0;
		for (int i = 0 ; i<numtiles ; i++){
			divisions[i] = (i)*((maxval-minval)/numtiles)+minval;
		}

		for (int i = 0; i< Width ; i++) {
			for (int j = 0 ; j<Height ; j++) {
				for (int k = 0 ; k<divisions.length; k++) {
					if (myTerrain[i][j] > divisions[k]) {
						tileSelector = k;
					}
				}
				levelMatrix.add3D(newTiles.getTile(tileSelector, 0),i,j,0);
			}
		}
	}

	public void setTile(int newX, int newY, Tile T){
		levelMatrix.clearTiles(newX,newY);
		levelMatrix.add3D(T,newX,newY,0);
	}

	public Integer[][] getTerrainBorders(){
		//returns in North South East West order
		int terrainSize = myTerrain.length;
		Integer[][] outputBorderArray = new Integer[4][terrainSize];
		for (int i = 0; i<terrainSize ; i++){
			outputBorderArray[0][i] = myTerrain[i][1];
			outputBorderArray[1][i] = myTerrain[i][terrainSize-2];
			outputBorderArray[2][i] = myTerrain[terrainSize-2][i];
			outputBorderArray[3][i] = myTerrain[1][i];



			//outputBorderArray[0][i] = -250;
			//outputBorderArray[1][i] = 0;
			//outputBorderArray[2][i] = 250;
			//outputBorderArray[3][i] = 500;
		}

		return outputBorderArray;
	}

	public void tick(){
		boolean isCleared = false;
		//boolean isOnScreen = false;

		if (levelImage == null) isCleared = true;

		if ((worldX > (Handler.windowX+Handler.windowWidth)) ||
				(worldY > (Handler.windowY+Handler.windowHeight)) ||
				((worldX + imageWidth)<Handler.windowX) ||
				((worldY + imageHeight)<Handler.windowY)){
			if (!isCleared){
				levelImage = null;
				isCleared = true;
				//isOnScreen = false;
				//System.out.println("OFFSCREEN!");
			}
		} else {
			if (isCleared == true) {
				refreshSquare();
				isCleared = false;
				//isOnScreen = true;
				//System.out.println("ON SCREEN!");
			}
		}




	}

	public Integer[][] getTerrain(){
		return myTerrain;
	}



}
