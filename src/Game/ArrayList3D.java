package Game;

import java.util.ArrayList;

public class ArrayList3D<T> {
	private ArrayList<ArrayList<ArrayList<T>>> theArray = new ArrayList<ArrayList<ArrayList<T>>>();
	
	public ArrayList3D(int hSize, int vSize){
		for (int i = 0 ; i< hSize; i++){
			theArray.add(i, new ArrayList<ArrayList<T>>());
			for (int j = 0 ; j < vSize ; j++){
				theArray.get(i).add(j, new ArrayList<T>()); 
			}
		}
	}
	
	public T get3D(int x, int y, int z){
		return theArray.get(x).get(y).get(z);
	}

	public void add3D(T newObject, int x, int y, int z){
		theArray.get(x).get(y).add(z, newObject);
	}
	public void addLast3D(T newObject,int x, int y){
		int z = theArray.get(x).get(y).size();
		theArray.get(x).get(y).add(z, newObject);
	}
	public void removeFirst3D(int x, int y){
		theArray.get(x).get(y).remove(0);
	}
	public void removeLast3D(int x, int y){
		int index = theArray.get(x).get(y).size();
		theArray.get(x).get(y).remove(index);
	}
	public int getDims() {
		return theArray.size();
	}
	public int getDims(int x){
		return theArray.get(x).size();
	}
	public int getDims(int x, int y){
		return theArray.get(x).get(y).size();
	}
	public void clearTiles(int x, int y){
		theArray.get(x).get(y).clear();
	}

}
