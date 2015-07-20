package tileSets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TileSet {
	private String sourcePath;
	private int tileDims, tileOffsetX, tileOffsetY, tileSeparation, sourceWidth, sourceHeight;
	private BufferedImage img = null;
	private Tile[][] tileMap;
	
	public TileSet(String newSrc, int newTileDims, int newTileOffsetX, int newTileOffsetY, int newTileSep){
		sourcePath = newSrc;
		tileDims = newTileDims;
		tileOffsetX = newTileOffsetX;
		tileOffsetY = newTileOffsetY;
		tileSeparation = newTileSep;
	}
	
	public TileSet(String newSrc, int newTileDims){
		sourcePath = newSrc;
		tileDims = newTileDims;
		tileOffsetX = 0;
		tileOffsetY = 0;
		tileSeparation = 0;
	}
	
	public void loadTiles(){
		try {
			img= ImageIO.read(new File(sourcePath));
		} catch (IOException e){
			System.out.println(e.toString());
		}

		sourceWidth = (int) Math.floor(img.getWidth()/tileDims);
		//System.out.println(sourcePath);
		sourceHeight= (int) Math.floor(img.getHeight()/tileDims);
		tileMap = new Tile[sourceWidth][sourceHeight];
		for (int i = 0 ; i<sourceWidth ; i++) {
			for (int j = 0 ; j < sourceHeight ; j++) {
				tileMap[i][j] = new Tile();
				BufferedImage subImage = img.getSubimage(i*tileDims, j*tileDims, tileDims, tileDims);
				tileMap[i][j].setTile(subImage, i*tileDims, j*tileDims);
			}
		}
	}
	
	public int[] getDims(){
		int out[] = new int[3];
		out[0]=tileDims;
		out[1]=sourceWidth;
		out[2]=sourceHeight;
		return out;
	}
	
	public Tile getTile(int x, int y){
		return tileMap[x][y];
	}
	
	

}
