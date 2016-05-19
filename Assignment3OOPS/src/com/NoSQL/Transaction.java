package com.NoSQL;

/**
 * @author Deep Sanghvi
 */

import java.io.IOException;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Transaction {

	private boolean isactive=true;
	
	 private static HashMap<String, Integer> map=new HashMap<>();
	 private static JSONArray array=new JSONArray();
	 private int index=-1;
	 private Database database;
		 
	 public Transaction() throws IOException, JSONException {
			database=new Database();
		}
	
	public boolean put(String key, Object value) throws JSONException, IllegalAccessException {
		if(!isactive){
			throw new IllegalAccessException();
		}
		if(map.containsKey(key)){
			replace(key, value);
			return true;
		}
		JSONObject obj=new JSONObject();
		 obj.put(key, value);
		 this.index++;
		 map.put(key, index);
		 array.put(obj);
		 return true;
	}

	public int getInt(String key) throws JSONException {
		if(key==null){
			throw new NullPointerException();
		}
		if(map.containsKey(key)){
			 int i= map.get(key);
			 JSONObject obj=(JSONObject) array.get(i);
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
			 int i= map.get(key);
			 JSONObject obj=(JSONObject) array.get(i);
			 Object val= obj.get(key);
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
			 int i= map.get(key);
			 JSONObject obj=(JSONObject) array.get(i);
			 Object val= obj.get(key);
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
	
	public customArray getDbArray(String key) throws JSONException {
		if(key==null){
			throw new NullPointerException();
		}
		if(map.containsKey(key)){
			 int i= map.get(key);
			 JSONObject obj=(JSONObject) array.get(i);
			 Object val= obj.get(key);
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
			 int i= map.get(key);
			 JSONObject obj=(JSONObject) array.get(i);
			 Object val=obj.get(key);
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

	public Object get(String key) throws JSONException, IllegalAccessException {
		if(!isactive){
			throw new IllegalAccessException();
		}
		if(map.containsKey(key)){
			 int i= map.get(key);
			 JSONObject obj=(JSONObject) array.get(i);
			 Object val=obj.get(key);
			 return val;
		 }
		else{
			throw new IllegalAccessError("key not found");
		}
	}

	public Object remove(String key) throws JSONException, IllegalAccessException {
		if(!isactive){
			throw new IllegalAccessException();
		}
		while(map.containsKey(key)){
			 int i=map.get(key);
			 Object value=array.get(i);
			 array.remove(i);
			 map.remove(key);
			 this.index--;
			 for(String keys:map.keySet()){
				 if(map.get(keys)>i){
				 map.put(keys, map.get(keys)-1);
				 }
			 }
			 return value;
		 }
			return null;
	}
	
	public Object replace(String key,Object newValue) throws JSONException {
		if(map.containsKey(key)){
			int i=map.get(key);
			array.getJSONObject(i).put(key, newValue);
			map.put(key,i);
			return newValue;
		}
		return null;
	 }

	public void commit() throws IOException, JSONException {
		database.setArray(array);
		this.index=-1;
		isactive=false;
	}

	public void abort() {
		array=null;
		isactive=false;
	}

	public boolean isActive() {
		return isactive;
	}
}
