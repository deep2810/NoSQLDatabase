package com.NoSQL;

/**
 * @author Deep Sanghvi
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;
import org.json.*;

public class Database {

	 private static HashMap<String, Integer> map=new HashMap<>();
	 private static JSONArray array=new JSONArray();
	 private static Scanner readLine;
	 private int index=-1;
	 private Memento memento;
	 private final String commandFile ="F:/eclipse/storage/Assignment3OOPS/src/com/command.txt";
	 private final String snapShotFile="F:/eclipse/storage/Assignment3OOPS/src/com/dbSnapShot.txt";
	 Hashtable<String, ArrayList<Cursor>> observers=new Hashtable<>();
	 
	 
	 public Database() throws IOException, JSONException {
		 FileWrite.createCommandFile(); 
		 executeCommand();
	}
	
	public void executeCommand() throws IOException, JSONException{
		 Scanner input = null;
			try{
				input = new Scanner(new File(commandFile));
			}catch(FileNotFoundException e){
				e.printStackTrace();
			}
			while(input.hasNextLine()){
				readLine = new Scanner(input.nextLine());
				Object value = null;
				String command = readLine.next();
				String expression = readLine.next();
				String key = readLine.next();
				if(readLine.hasNext()){
					readLine.next();
					value = readLine.next();
				}
				if(command.compareTo("put")==0){
					put(key, value);
				}
				else if(command.compareTo("remove")==0){
					remove(key);
				}
			}
	 }
 
	public Database put(String key, Object value) throws  JSONException {
		if(map.containsKey(key)){
			replace(key, value);
			return this;
		}
		JSONObject object=new JSONObject();
		 object.put(key, value);
		 this.index++;
		 map.put(key, index);
		 array.put(object);
		 notifyObserver(key);
		 return this;
	}

	public int getInt(String key) throws  JSONException {
		if(key==null){
			throw new NullPointerException();
		}
		if(map.containsKey(key)){
			 int keyIndex= map.get(key);
			 JSONObject obj=(JSONObject) array.get(keyIndex);
			 Object val=obj.get(key);
			 if(!(val instanceof Integer)){
				 throw new IllegalArgumentException();
			 }
			 int value=(int) obj.get(key);
			 return value;
		 }
		else{
			throw new IllegalAccessError("key not found");
		}
	}
	
	public double getDouble(String key) throws JSONException {
		if(key==null){
			throw new NullPointerException();
		}
		if(map.containsKey(key)){
			 int keyIndex= map.get(key);
			 JSONObject obj=(JSONObject) array.get(keyIndex);
			 Object val=obj.get(key);
			 if(!(val instanceof Double)){
				 throw new IllegalArgumentException();
			 }
			 double value=(double)obj.get(key);
			 return value;
		 }
		else{
			throw new IllegalAccessError("key not found");
		}
	}
	
	public String getString(String key) throws JSONException {
		if(key==null){
			throw new NullPointerException();
		}
		if(map.containsKey(key)){
			 int keyIndex= map.get(key);
			 JSONObject obj=(JSONObject) array.get(keyIndex);
			 Object val=obj.get(key);
			 if(!(val instanceof String)){
			 	throw new IllegalArgumentException();
			 }
			 String value=val.toString();
			 return value;
		 }
		else{
			throw new IllegalAccessError("key not found");
		}
	}
	
	public customArray getCustomArray(String key) throws JSONException {
		if(key==null){
			throw new NullPointerException();
		}
		if(map.containsKey(key)){
			 int keyIndex= map.get(key);
			 JSONObject obj=(JSONObject) array.get(keyIndex);
			 Object val=obj.get(key);
			 if(!(val instanceof com.NoSQL.customArray)){
				 throw new IllegalArgumentException();
			 }
			 customArray value=(customArray)val;
			 return value;
		 }
		else{
			throw new IllegalAccessError("key not found");
		}
	}
	
	public CustomObject getCustomObject(String key) throws JSONException {
		if(key==null){
			throw new NullPointerException();
		}
		if(map.containsKey(key)){
			 int keyIndex= map.get(key);
			 JSONObject obj=(JSONObject) array.get(keyIndex);
			 Object val= obj.get(key);
			 if(!(val instanceof com.NoSQL.CustomObject)){
				 throw new IllegalArgumentException();
			 }
			 CustomObject value=(CustomObject)val;
			 return value;
		 }
		else{
			throw new IllegalAccessError("key not found");
		}
	}
	
	public Object get(String key) throws JSONException {
		if(key==null){
			throw new NullPointerException();
		}
		if(map.containsKey(key)){
			 int keyIndex= map.get(key);
			 JSONObject obj=(JSONObject) array.get(keyIndex);
			 Object val=obj.get(key);
			 return val;
		 }
		else{
			throw new IllegalAccessError("key not found");
		}
	}
	
	public Object remove(String key) throws JSONException {
		if(key==null){
			throw new NullPointerException();
		}
		while(map.containsKey(key)){
			 int keyIndex=map.get(key);
			 Object value=array.get(keyIndex);
			 array.remove(keyIndex);
			 map.remove(key);
			 this.index--;
			 for(String keys:map.keySet()){
				 if(map.get(keys)>keyIndex){
				 map.put(keys, map.get(keys)-1);
				 }
			 }
			 return value;
		 }
		return null;
	}
	
	public Object replace(String key,Object newValue) throws JSONException {
		if(key==null){
			throw new NullPointerException();
		}
		if(map.containsKey(key)){
			int keyIndex=map.get(key);
			array.getJSONObject(keyIndex).put(key, newValue);
			map.put(key,keyIndex);
			notifyObserver(key);
			return newValue;
		}
		return null;
	 }
	
	public Cursor getCursor(String key) {
		if(key==null){
			throw new NullPointerException();
		}
		if(!(map.containsKey(key))){
			throw new IllegalArgumentException("key "+key+" not found");
		}
        Cursor cursor= new Cursor(this,key);
        addObserver(cursor,key);
        return cursor;
    }
    
    public void addObserver(Cursor cursor,String key)    {
        ArrayList<Cursor> listOfObservers=observers.get(key);
        if(listOfObservers==null)  {
            listOfObservers=new ArrayList<Cursor>();
        }
        listOfObservers.add(cursor);
        this.observers.put(key,listOfObservers);
    }
    
    public void removeObserver(Cursor cursor,String key)    {
        ArrayList<Cursor> list=observers.get(key);
        if(list==null)  {
            list=new ArrayList<Cursor>();
        }
        list.remove(cursor);
        this.observers.remove(key);
    }
    
    public void notifyObserver(String key) throws JSONException    {
        ArrayList<Cursor> listOfObserver=observers.get(key);
        if(listOfObserver!=null)   {
        for(Cursor observer : listOfObserver)    {
            observer.update();
            }
        }
     }
	public boolean snapShot() throws IOException, JSONException{
		 memento=new Memento();
		 FileWrite.createMementoFile();
		 memento.storeMemento(array);
		 PrintWriter file=new PrintWriter(commandFile);
	     file.close();
		 return true;
	}
	
	public boolean snapShot(File command,File snapShot) throws IOException, JSONException{
		 memento=new Memento();
		 FileWrite.createMementoFile(snapShot);
		 memento.storeMemento(array);
		 PrintWriter file=new PrintWriter(commandFile);
	     file.close();
	     FileWrite.createCommandFile(command);
		 return true;
	}
	
	public boolean recover() throws JSONException, IOException{
		readFile();
		executeCommand();
		return true;
	}
	
	public boolean recover(File command,File snapShot) throws JSONException, IOException{
		readFile(snapShot);
		executeCommand();
		return true;
	}
	
	public void readFile() throws JSONException{
		 Scanner sc=null;
		 HashMap<String, Object> map1=new HashMap<String,Object>();
		 try{
			 sc=new Scanner(new File(snapShotFile));
		 }catch(FileNotFoundException e){
			 e.printStackTrace();
		 }
		 while(sc.hasNextLine()){
			 Scanner scanner=new Scanner(sc.nextLine());
			 String key_data=scanner.next();
			 String value=key_data.replaceAll("[{}\"]", "");
			 String data[]=value.split(":");
			 String key=data[0];
			 Object obj=data[1];
			 map1.put(key, obj);
			 }
		 for(String keys:map1.keySet()){
			if(!map.containsKey(keys)){
			 this.index++;
			 map.put(keys, index);
			 JSONObject json=new JSONObject();
			 json.put(keys, map1.get(keys));
			 array.put(json);
			}
		 }
	 }
	
	public void readFile(File dbSnapShot) throws JSONException{
		 Scanner sc=null;
		 HashMap<String, Object> map1=new HashMap<String,Object>();
		 try{
			 sc=new Scanner(dbSnapShot);
		 }catch(FileNotFoundException e){
			 e.printStackTrace();
		 }
		 while(sc.hasNextLine()){
			 Scanner scanner=new Scanner(sc.nextLine());
			 String key_data=scanner.next();
			 String value=key_data.replaceAll("[{}\"]", "");
			 String data[]=value.split(":");
			 String key=data[0];
			 Object obj=data[1];
			 map1.put(key, obj);
			 }
		 for(String keys:map1.keySet()){
			if(!map.containsKey(keys)){
			 this.index++;
			 map.put(keys, index);
			 JSONObject json=new JSONObject();
			 json.put(keys, map1.get(keys));
			 array.put(json);
			}
		 }
	 }
	
	public Transaction transaction() throws IOException, JSONException {
		Transaction transactionObj=new Transaction();
		return transactionObj;
	}
	
	public boolean setArray(JSONArray jsonArray) throws IOException, JSONException{
		for(int i=0;i<jsonArray.length();i++){
			array.put(jsonArray.getJSONObject(i));
		}
		return true;
	}
	
	public void closeFiles() throws IOException{
		FileWrite.closeFiles();
	}
}
