package GameObjects;

import Levels.LoadableLevel;

public class Entity {
	
	//an entity is an instance of one object that can exist in multiple worlds.
	//this is to help keep track of where objects are when switching between worlds.
	//we need to have a way to update all of an objects entities in terms of existence and position.
	
	//I think handler needs to keep track of entities.
	GameObject myGameObject;
	LoadableLevel myLevel;
	int x;
	int y;
	int depth;
	
	public Entity(GameObject newobj, LoadableLevel newlev, int newx, int newy, int newdepth){
		myGameObject = newobj;
		myLevel = newlev;
		x=newx;
		y=newy;
		depth=newdepth;
	}
	
}
