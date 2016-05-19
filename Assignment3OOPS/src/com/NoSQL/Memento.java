package com.NoSQL;

/**
 * @author Deep Sanghvi
 */

import java.io.*;
import org.json.JSONArray;
import org.json.JSONException;

public class Memento  {

    public Memento() {
	}
    
    public void storeMemento(JSONArray array) throws IOException, JSONException{
    	FileWrite.writeMementoFile(array);
    }
}
