package com.command;

/**
 * @author Deep Sanghvi
 */
import java.io.IOException;
import org.json.JSONException;
import com.NoSQL.*;

public class AddData implements Command{
	
	private Database database;
	private String key;
	private Object value;
	
	public AddData(Database db) throws IOException {
		this.database=db;
	}

	public void put(String key,Object value) throws IOException{
		this.key=key;
		this.value=value;
		FileWrite.writeCommandFile("put -> "+key+" : "+value);
	}
	
	@Override
	public void execute() throws IOException, JSONException {
		database.put(key, value);
	}

}
