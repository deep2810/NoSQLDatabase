package com.NoSQL;

/**
 * @author Deep Sanghvi
 */

import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONException;
import com.command.*;

public class DatabaseInvoker {

	private ArrayList<Command> arrayListOfCommands=new ArrayList<Command>();

    public DatabaseInvoker() {

    }
    
    public void addDatabaseCommand(Command command){
    	 	arrayListOfCommands.add(command);
    }
    
    public boolean runCommand(int index) throws IOException, JSONException{
    		arrayListOfCommands.get(index).execute();		
    	   	return true;
    }
 
}

