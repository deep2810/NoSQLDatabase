package com.command;

import java.io.IOException;
import org.json.JSONException;

public interface Command  {

    public abstract void execute() throws IOException, JSONException;

}