package com.test;

import static org.junit.Assert.*;

import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;
import org.junit.Test;
import com.NoSQL.*;

public class CustomArrayandObjectTest {

	@Test
	public void testDbArray() throws JSONException, JsonParseException, JsonMappingException, IOException {
		String convertToArrary="[1,\"deep\",2,4.0,[3,4,5],{\"name\":\"MyNode\", \"width\":200, \"height\":100}]";
		customArray array=customArray.fromString(convertToArrary);
		String actual=array.toString();
		assertEquals("[1,deep,2,4.0,[[3,4,5]],{height:100,name:MyNode,width:200}]", actual);
		assertEquals("deep", array.getString(1));
		assertEquals(2, array.getInteger(2));
		assertEquals(4.0, array.getDouble(3),0.01);
		assertEquals("{height:100,name:MyNode,width:200}",array.getCustomObject(5).toString());	
	}
	
	@Test
	public void testCustomObject() throws Exception {
		String convertToObject="{\"name\":\"MyNode\", \"width\":200, \"height\":100.10, \"arr\":[3,4,5],\"obj\":{\"name\":\"dep\", \"width\":25, \"height\":11.0}}";
		CustomObject obj=CustomObject.fromString(convertToObject);
		String actual=obj.toString();
		assertEquals("MyNode", obj.getString("name"));
		assertEquals(200, obj.getInteger("width"));
		assertEquals(100.10, obj.getDouble("height"),0.01);
		assertEquals("[[3,4,5]]", obj.getCustomArray("arr").toString());
		assertEquals("{height:11.0,name:dep,width:25}", obj.getCustomObject("obj").toString());
	}

}
