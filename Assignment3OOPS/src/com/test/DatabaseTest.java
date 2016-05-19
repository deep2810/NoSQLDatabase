package com.test;

/**
 * @author Deep Sanghvi
 */

import static org.junit.Assert.*;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import com.NoSQL.*;
import com.command.*;

public class DatabaseTest {
	
	
	@Test
	public void testPut() throws JSONException, IOException{
		Database database=new Database();
		assertEquals(database,database.put("deep", 819657911));
	}
	
	@Test
	public void testGet() throws Exception {
		Database database=new Database();
		DatabaseInvoker databaseInvoker = new DatabaseInvoker();
		AddData addToDatabase = new AddData(database);
		
		addToDatabase.put("deep","sanghvi");
		databaseInvoker.addDatabaseCommand(addToDatabase);
		databaseInvoker.runCommand(0);	
		assertEquals("sanghvi",database.getString("deep"));
		
		customArray dbArray=new customArray();
		dbArray=customArray.fromString("[1,5,2.4,\"sanghvi\",[5,6,7],{\"name\" : \"Deep\"}]");
		addToDatabase.put("array",dbArray);
		databaseInvoker.addDatabaseCommand(addToDatabase);
		databaseInvoker.runCommand(1);	
		assertEquals(dbArray,database.getCustomArray("array"));
		assertEquals(1,dbArray.getInteger(0));
		assertEquals(6,dbArray.length());
		assertEquals("sanghvi",dbArray.getString(3));
		assertEquals("{name:Deep}",dbArray.getCustomObject(5).toString());
		
		String convertToObject="{\"name\":\"MyNode\", \"width\":200, \"height\":100.10, \"arr\":[3,4,5],\"obj\":{\"name\":\"dep\", \"width\":25, \"height\":11.0}}";
		CustomObject customObject=CustomObject.fromString(convertToObject);
		addToDatabase.put("object", customObject);
		databaseInvoker.addDatabaseCommand(addToDatabase);
		databaseInvoker.runCommand(2);
	    assertEquals(customObject,database.getCustomObject("object"));
	    assertEquals("MyNode", database.getCustomObject("object").getString("name"));
	    assertEquals(200, database.getCustomObject("object").getInteger("width"));
	    assertEquals("[[3,4,5]]",database.getCustomObject("object").getCustomArray("arr").toString());
	    
	    database.closeFiles();
	}
	
	@Test
	public void testRemove() throws JSONException, IOException{
		Database database=new Database();
		JSONObject obj = new JSONObject();
		obj.put("value", 25);
		Object value=database.remove("value");
		System.out.println(value);
	}
}
