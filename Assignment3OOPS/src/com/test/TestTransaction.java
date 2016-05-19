package com.test;

import static org.junit.Assert.*;
import java.io.IOException;
import org.json.JSONException;
import org.junit.Test;
import com.NoSQL.CustomObject;
import com.NoSQL.Database;
import com.NoSQL.customArray;
import com.NoSQL.Transaction;

public class TestTransaction {

	@Test
	public void testTransaction() throws IOException, JSONException, IllegalAccessException {
		Database dataBase=new Database();
		Transaction sample=dataBase.transaction();
		
		String convertToArrary="[1,\"deep\",2,4.0,[3,4,5],{\"name\":\"MyNode\", \"width\":200, \"height\":100}]";
		customArray arr=customArray.fromString(convertToArrary);
		String convertToObject="{\"name\":\"MyNode\", \"width\":200, \"height\":100.10, \"arr\":[3,4,5],\"obj\":{\"name\":\"dep\", \"width\":25, \"height\":11.0}}";
		CustomObject obj=CustomObject.fromString(convertToObject);

		sample.put("deep", "sanghvi");
		sample.put("d",10);
		sample.put("dep",7.2);
		sample.put("ep",obj);
		sample.put("arr",arr);
		assertEquals("sanghvi", sample.getString("deep"));
		assertEquals(10, sample.getInt("d"));
		assertEquals(7.2, sample.getDouble("dep"),0.01);
		assertEquals("[1,deep,2,4.0,[[3,4,5]],{height:100,name:MyNode,width:200}]", sample.getDbArray("arr").toString());
		assertEquals("{arr:[[3,4,5]],height:100.1,name:MyNode,width:200,obj:{height:11.0,name:dep,width:25}}",sample.getCustomObject("ep").toString());
		sample.commit();
		assertEquals(false, sample.isActive());
	}
}
