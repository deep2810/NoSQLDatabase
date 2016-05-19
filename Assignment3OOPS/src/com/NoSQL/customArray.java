package com.NoSQL;

/**
 * @author Deep Sanghvi
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

	public class customArray {

	    private ArrayList<Object> customArray;

	    public customArray() {
	        customArray=new ArrayList<Object>();
	    }
  			
	    public void put(Object value) {
	    	customArray.add(value);
	    }

	    public Object get(int index) {
	    	Object value=customArray.get(index);
			return value;
	    }

		public String getString(int index) {
			Object getVal=customArray.get(index);
			if(!(getVal instanceof String)){
				 throw new IllegalArgumentException();
			 }
			String value=getVal.toString();
			return value;
		}

		public int getInteger(int index) {
			Object getVal=customArray.get(index);
			if(!(getVal instanceof Integer)){
				 throw new IllegalArgumentException();
			 }
			int value=(int)getVal;
			return value;
		}
		
		public double getDouble(int index) {
			Object getVal=customArray.get(index);
			if(!(getVal instanceof Double)){
				 throw new IllegalArgumentException();
			 }
			double value=(double)getVal;
			return value;
		}
		
		public customArray getDbArray(int index) {
			Object val=customArray.get(index);
			if(!(val instanceof com.NoSQL.customArray)){
				 throw new IllegalArgumentException();
			 }
			customArray value=(customArray) val;
			return value;
		}
		
		public CustomObject getCustomObject(int index) {
			Object getVal=customArray.get(index);
			if(!(getVal instanceof com.NoSQL.CustomObject)){
				 throw new IllegalArgumentException();
			 }
			CustomObject value=(CustomObject) getVal;
			return value;
		}
		
		public int length() {
			return customArray.size();
		}

		public Object remove(int index) {
			Object getVal=customArray.get(index);
			customArray.remove(index);
			return getVal;
		}
		
		public String toString(){
			StringBuffer buffer=new StringBuffer();
			buffer.append("[");
			for(int i=0;i<customArray.size();i++)
				buffer.append(customArray.get(i)+",");
			buffer.deleteCharAt(buffer.length()-1);
			buffer.append("]");
			return buffer.toString();
		}
		
		public static customArray fromString(String str) throws JSONException, IOException{
			customArray customArray=new customArray();
			JSONArray jsonArray=new JSONArray(str);
			for(int i=0;i<jsonArray.length();i++){
				Object value=jsonArray.get(i);
				if(value instanceof org.json.JSONArray){
					customArray array=new customArray();
					array.put(value);
					customArray.put(array);
				}
				else if(value instanceof org.json.JSONObject){
					CustomObject dbObject=new CustomObject();
					JSONObject jsonObject = new JSONObject(value.toString());
					Iterator a = jsonObject.keys();
					while(a.hasNext()) {
						String key = (String)a.next();
						Object val = jsonObject.get(key);
						dbObject.put(key, val);
					}
		        customArray.put(dbObject);
				}
				else{
					customArray.put(value);
				}
			}
			return customArray;
		}
	}