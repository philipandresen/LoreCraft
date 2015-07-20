package Game;


import java.awt.event.KeyAdapter; /// WHAT
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class KeyInput extends KeyAdapter{
	
	private ArrayList<Integer> KeyList = new ArrayList<Integer>();
	private ArrayList<Integer> IgnoreList = new ArrayList<Integer>();
	
	public KeyInput (){//constructor
		
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		addKey(key, KeyList);
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		removeKey(key, KeyList);
		removeKey(key, IgnoreList);
	}
	
	private boolean addKey(int key, ArrayList<Integer> list){ //add a key to the list of keys being held down
		int Found = 0;
		for(int i = 0; i < list.size(); i++){
			if (list.get(i) == key){
				Found = 1;
				break;
			}
		}
		if (Found == 1){
			return false;
		} else {
			list.add(key);
			return true;
		}
	}
	
	private boolean removeKey(int key, ArrayList<Integer> list){ //remove a key from the list of keys being held down
		int Found = 0;
		int FoundAt = 0;
		for(int i = 0; i < list.size(); i++){
			if (list.get(i) == key){
				Found = 1;
				FoundAt = i;
				break;
			}
		}
		if (Found == 1){
			list.remove(FoundAt);
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<Integer> getKeyList(){//return the list of keys pressed
		return KeyList;
	}
	
	public boolean isPressed(int key){ //return if key is contained in list
		int Found = 0;
		for(int i = 0; i < KeyList.size(); i++){
			if (KeyList.get(i) == key){
				Found = 1;
				break;
			}
		}
		if (Found == 1){
			return true;
		} else {
			return false;
		}
	}

	public boolean justPressed(int key){ //same as isPressed but should only happen once per press.
		boolean ignored = false;
		for(int i = 0; i < IgnoreList.size(); i++){
			if(IgnoreList.get(i) == key){
				ignored = true;
				break;
			}
		}
		if(isPressed(key) && (ignored == false)){
			addKey(key, IgnoreList);
			return true;
		} else {
			return false;
		}
	}
	
	
}
