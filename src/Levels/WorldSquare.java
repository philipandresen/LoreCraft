package Levels;

import java.awt.Graphics;
import java.awt.Graphics2D;


import java.awt.image.BufferedImage;

import Game.ArrayList3D;
import Game.Handler;
import terrainGeneration.DiamondSquareGenerator;
import tileSets.Tile;
import tileSets.TileSet;

public final class WorldSquare extends StaticWorldUnit {

	public WorldSquare(int wx, int wy, int newWidth, int newHeight,
			TileSet newTiles) {
		super(wx, wy, newWidth, newHeight, newTiles);
		// TODO Do we need to specify anything unique about worldsquares at this level?
	}


}
