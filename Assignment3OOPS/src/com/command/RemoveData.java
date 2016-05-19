package com.command;

import java.io.IOException;
import org.json.JSONException;
import com.NoSQL.*;

public class RemoveData implements Command {

	private Database database;
	private String key;

	public RemoveData(Database db) throws IOException {
		this.database=db;
	}
	
	public void remove(String key) throws IOException{
		this.key=key;
		FileWrite.writeCommandFile("remove -> "+key);
	}

	@Override
	public void execute() throws IOException, JSONException {
		System.out.println("removing");
		database.remove(key);
	}

}
