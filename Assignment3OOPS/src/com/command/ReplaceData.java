package com.command;

import java.io.IOException;
import org.json.JSONException;
import com.NoSQL.*;

public class ReplaceData implements Command{

	private Database database;
	private String key;
	private Object newValue;
	
	public ReplaceData(Database db) throws IOException {
		this.database=db;
	}

	public void replace(String key,Object newValue) throws IOException{
		this.key=key;
		this.newValue=newValue;
		FileWrite.writeCommandFile("replace -> "+key+" : "+newValue);
	}
	@Override
	public void execute() throws IOException, JSONException {
		database.replace(key, newValue);
	}

}
