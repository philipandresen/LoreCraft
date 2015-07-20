package Levels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import Levels.AStar_algorithm_by_kevin_glass.*;

public final class TerrainOrganizer {


	private World myWorld;
	private Integer[][] masterTerrain;
	private int waterHeight;
	
	public TerrainOrganizer(World newWorld){
		myWorld = newWorld;
		waterHeight = newWorld.getWaterHeight();
		importTerrainData();
		defineMajorLandFeatures();
	}
	
	
	private void importTerrainData(){
		masterTerrain = myWorld.getMasterTerrain();
		//printData();
	}
	
	private void defineMajorLandFeatures(){
		//TODO Need to write floodfill algorithms and figure out how to define this mess in a useful way.
		//this function separates the land into continents/islands oceans/lakes/puddles
		//to be used later to help narrow the scope of each path calculation.
		//should I make a subclass? A subclass that can be arranged?
		//Collections.sort can sort lists
		//SortedSet<object> set = new TreeSet<object>;
		//TreeSets sort automatically!
		//public int compareTo(Object compareObject){
		//return 0, 1, or -1
		GridUnit[][] GRID = new GridUnit[masterTerrain.length][masterTerrain.length]; 
		//hold all blocks that you've found but haven't checked neighbors
		ArrayList<GridUnit> floodFillOpenList = new ArrayList<GridUnit>();
		//hold all blocks that meet criteria and neighbors have been checked.
		ArrayList<GridUnit> floodFillClosedList = new ArrayList<GridUnit>(); 
		
		//hold all blocks that meet LAND criteria but still may need to be used to start a new floodfill
		//Sorted by natural order (altitude)
		SortedSet<GridUnit> floodFillStartLandList = new TreeSet<GridUnit>();
		//hold all blocks that meet SEA criteria but still may need to be used to start a new floodfill
		//Sorted by natural order (altitude)
		SortedSet<GridUnit> floodFillStartSeaList = new TreeSet<GridUnit>();
		
		for (int i = 0; i<GRID.length; i++){
			for (int j = 0; j<GRID.length; j++){
				Integer height = masterTerrain[i][j];
				GRID[i][j] = new GridUnit(i, j, height);
				if (height>waterHeight){
					GRID[i][j].setLand(true);
					floodFillStartLandList.add(GRID[i][j]);
				} else {
					GRID[i][j].setLand(false);
					floodFillStartSeaList.add(GRID[i][j]);
				}
			}
		}
	
		//define continents
		while (!floodFillStartLandList.isEmpty()){
			//start floodfill on the frist (tallest) element
			GridUnit G = floodFillStartLandList.first();
			GridUnit potentialG;
			//now add the current grid as open.
			floodFillOpenList.add(G);
			//and make sure we don't start on this one again.
			floodFillStartLandList.remove(G);
			while (!floodFillOpenList.isEmpty()) {
				G = floodFillOpenList.get(0);
				int x = G.getX();
				int y = G.getY();
				for (int i = -1; i<=1 ; i++){
					for (int j = -1; j<=1; j++){
						
						if (Math.abs(i)==Math.abs(j)){
							continue;
							//skip diagonals;
						}
						//avoid negative indexes on edge blocks.
						if (((x+i)<0) || ((y+j)<0)) continue;
						if (((x+i)>=GRID.length) || ((y+j)>=GRID.length)) continue;
						//System.out.println(x+i);
						//System.out.println(y+j);
						potentialG = GRID[x+i][y+j];
						if (potentialG.isLand() 
								&& !floodFillOpenList.contains(potentialG)
								&& !floodFillClosedList.contains(potentialG)){
							floodFillOpenList.add(potentialG);
							floodFillStartLandList.remove(potentialG);
						}
						
					}
				}
				floodFillClosedList.add(G);
				//myWorld.setTile(x, y, 15, 0);
				floodFillOpenList.remove(G);
			}
			System.out.println("New continent of size: ");
			System.out.println(floodFillClosedList.size());
			floodFillClosedList.clear();
			floodFillOpenList.clear();
		}
		
		
		
		//myWorld.refreshAll();
		
	}
	
	
	
	public void printData(){
		for(Integer[] row : masterTerrain){
			if (row != null){
				for(Integer d : row){
					System.out.printf("%5d",d);
				}
				System.out.println();
			}
		}
		System.out.println();
	}
	
}

final class GridUnit implements Comparable<GridUnit> {
	
	private int x, y, altitude, delta;
	//delta is going to be defined by whatever is iterating the path. Will have to be initialized at runtime.
	private boolean land;
	private boolean iterated;
	
	public GridUnit(int newx, int newy, Integer newheight){
		x=newx;
		y=newy;
		altitude = newheight;
		iterated = false;
	}
	
	public boolean isLand(){
		return land;
	}
	public boolean hasIterated(){
		return iterated;
	}
	public void setLand(boolean isLand){
		land = isLand;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getHeight(){
		return altitude;
	}
	
	@Override
	public int compareTo(GridUnit unit) {
		// Sort by height, then x, then y, otherwise return "same"
		if (altitude<unit.altitude){
			return -1;
		}
		if (altitude>unit.altitude){
			return 1;
		}
		if (altitude == unit.altitude){
			if (x<unit.x){
				return -1;
			}
			if (x>unit.x){
				return 1;
			}
			if (x == unit.x){
				if (y<unit.y){
					return -1;
				}
				if (y>unit.y){
					return 1;
				}
			}
		}
		return 0;
	}
	
}
