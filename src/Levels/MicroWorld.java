package Levels;

import Game.Direction;
import Game.Handler;
import Game.ID;
import Game.LoreCraft;
import GameObjects.Player;
import tileSets.TileSet;

public class MicroWorld  extends World{
	

	public MicroWorld(int ws, int wss, TileSet ts, int levelTier, boolean randomFill) {
		super(ws,wss,ts,levelTier,randomFill);
	}


	@Override
	public void Zoom(int pX, int pY) {
		World levelTo = LoreCraft.cachedLevel;
		LoreCraft.currentLevel = levelTo;
		//Handler.findObject(ID.Player).getFirst().setLocation(pX, pY, false);
		
	}


}
