package Levels;

import java.awt.Graphics2D;
import java.util.ArrayList;

import GameObjects.GameObject;
import tileSets.TileSet;

public interface LoadableLevel {
	
	//low tier is the overworld. Higher tiers are subworlds of the overworld

	public void tick();
	public void render(Graphics2D g2);
	public void Zoom(int x, int y);
	
	
}
