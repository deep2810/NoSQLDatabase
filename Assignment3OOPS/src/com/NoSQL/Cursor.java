package com.NoSQL;

/**
 * @author Deep Sanghvi
 */

import java.util.ArrayList;
import org.json.JSONException;

public class Cursor {
	
	private String key;
	private static ArrayList<Observer> observer=new ArrayList<Observer>();
	private Database database;
	
	public Cursor(){
		
	}
	public Cursor(Database database,String key){
		this.database=database;
		this.key=key;
	}
	
	public Object get() throws JSONException{
		return database.get(key);
	}
	
	public int getInteger() throws JSONException{
		return database.getInt(key);
	}
	
	public String getString() throws JSONException{
		return database.getString(key);
	}
	
	public double getDouble() throws JSONException{
		return database.getDouble(key);
	}
	public customArray getCustomArray() throws JSONException {
		return database.getCustomArray(key);
	}
	
	public CustomObject getCustomObject() throws JSONException {
		return database.getCustomObject(key);
	}
	
	public boolean addObserver(Observer o){
		observer.add(o);
		return true;
	}
	
	public boolean removeObserver(Observer o){
		observer.remove(o);
		return true;
	}
	
	public void notifyAllObservers() throws JSONException{
		Object value=get();
		for(Observer listOfObservers : observer)  {
            listOfObservers.update(value);
        }
	}
	
	public void update() throws JSONException    {
        notifyAllObservers();
    }
}
