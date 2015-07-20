package tileSets;

import java.awt.image.BufferedImage;

import Game.LoreCraft;

//TODO Tiles need physical properties, I think. Solid, Free, ForceEnter;

public class Tile {

		private String sourcefile;
		//private int[] sourceLocation = new int[2];
		//private int[] destLocation = new int[2];
		private BufferedImage img = null;
		
		private boolean mustEnter = false;
		
		public Tile() {
			
		}
		
		public void setTile(BufferedImage newImg, int srcx, int srcy) {
			img = newImg;
			//System.out.println(img.getType());
			//sourceLocation[0]=srcx;
			//sourceLocation[1]=srcy;
		}

		
		public String getSource() {
			return sourcefile;
		}
		
		/*public int[] getSourceLocation() {
			return sourceLocation;
		}
		public int[] getDestLocation() {
			return destLocation;
		}*/
		public BufferedImage getImage(){
			return img;
		}
		
}
