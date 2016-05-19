package com.NoSQL;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomObject {
	 Hashtable<String, Object> objectData=new Hashtable<>();
	    
	 public void put(String key,Object value)    {
	        objectData.put(key, value);
	    }
	    
	    public Object get(String key)  {
	        return objectData.get(key);
	    }
	    
	    public int getInteger(String key)  throws Exception{
	    	if (key == null) {
	            throw new IllegalArgumentException("Key " + key + " cannot be null.");
	        }
	    	if(objectData.get(key) instanceof Integer)
	            return (int)objectData.get(key);
	        throw new Exception("Invalid Exception");
	    }
	    public double getDouble(String key) throws Exception {
	    	if (key == null) {
	            throw new IllegalArgumentException("Key " + key + " cannot be null.");
	        }
	    	if(objectData.get(key) instanceof Double)
	            return (double)objectData.get(key);
	        throw new Exception("Invalid Type");
	    }
	    public String getString(String key) throws Exception  {
	    	if (key == null) {
	            throw new IllegalArgumentException("Key " + key + " cannot be null.");
	        }
	    	if(objectData.get(key) instanceof String)
	            return (String)objectData.get(key);
	        throw new Exception("Invalid Type");
	    }
	    
	    public customArray getCustomArray(String key) throws Exception{
	    	if (key == null) {
	            throw new IllegalArgumentException("Key " + key + " cannot be null.");
	        }
	    	if(objectData.get(key) instanceof com.NoSQL.customArray)
	        	return (customArray)objectData.get(key);
	        throw new Exception("Invalid Type");
	    }
	    
	    public CustomObject getCustomObject(String key) throws Exception  {
	    	if (key == null) {
	            throw new IllegalArgumentException("Key " + key + " cannot be null.");
	        }
	    	if(objectData.get(key) instanceof com.NoSQL.CustomObject)
	        	return (CustomObject)objectData.get(key);
	        throw new Exception("Invalid Type");
	    }
	    public int length() {
	        return objectData.size();
	    }
	    public Object remove(String key)    {
	        return objectData.remove(key);
	    }
	    public String toString()    {
	    	StringBuffer buffer=new StringBuffer();
	    	buffer.append("{");
	    	for(String key:objectData.keySet()){
	    		buffer.append(key+':'+objectData.get(key)+",");
	    	}
	    	buffer.deleteCharAt(buffer.length()-1);
	    	buffer.append("}");
	    	return buffer.toString();
	    }
	    
	    public static CustomObject fromString(String string) throws JSONException, IOException{
	    	CustomObject customObject=new CustomObject();
	    	JSONObject jsonObject = new JSONObject(string);
	        Iterator a = jsonObject.keys();
	   
	        while(a.hasNext()) {
	        	String key = (String)a.next();
	            Object value = jsonObject.get(key);
	            if(value instanceof org.json.JSONArray){
	            	customArray arr1=new customArray();
					arr1.put(value);
					customObject.put(key,arr1);
				}
				else if(value instanceof org.json.JSONObject){
					CustomObject objectType=new CustomObject();
					JSONObject json = (JSONObject)(value);
			        Iterator itr = json.keys();
			        while(itr.hasNext()) {
			        	String subKey = (String)itr.next();
			            Object getValue = json.get(subKey);
			            objectType.put(subKey, getValue);
			        }
			        customObject.put(key,objectType);

				}
				else{
					customObject.put(key,value);
				}

	        }
	       	return customObject;
		}

	   
}
