package com.command;

/**
 * @author Deep Sanghvi
 */

import java.io.IOException;
import org.json.JSONException;
import com.NoSQL.*;

public class GetData implements Command{

	private Database database;
	private String key;
	
	public GetData(Database db) throws IOException {
		this.database=db;
	}

	public void get(String key) throws IOException{
		this.key=key;
		FileWrite.writeCommandFile("get -> "+key);
	}
	@Override
	public void execute() throws IOException, JSONException {
		database.get(key);
	}
}
