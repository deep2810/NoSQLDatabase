package com.NoSQL;

/**
 * @author Deep Sanghvi
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;


public class FileWrite {
	private static FileWriter writer1;
	private static FileWriter writerMemento;
	private static FileWrite fileWrite;
	private static FileWriter write1;
	private static FileWriter writeMemento;
	private final static String commandFile ="F:/eclipse/storage/Assignment3OOPS/src/com/command.txt";
	private final static String snapShotFile="F:/eclipse/storage/Assignment3OOPS/src/com/dbSnapShot.txt";

	public FileWrite() {
			
		}
	
	public static void FileWriteCommand(FileWriter writer){
		FileWrite.writer1=writer;
	}
	
	public static void FileWriteMemento(FileWriter writer){
		FileWrite.writerMemento=writer;
	}
	
	public static void createCommandFile() throws IOException{
		 write1=new FileWriter(commandFile,true);
		 fileWrite=new FileWrite();
		 FileWriteCommand(write1);
	 }
	 
	 public static void createMementoFile() throws IOException{
		 writeMemento=new FileWriter(snapShotFile);
		 fileWrite=new FileWrite();
		 FileWriteMemento(writeMemento);
	 }
	public static void createCommandFile(File file) throws IOException{
		 write1=new FileWriter(file,true);
		 fileWrite=new FileWrite();
		 FileWriteCommand(write1);
	 }
	 
	 public static void createMementoFile(File snapShot) throws IOException{
		 writeMemento=new FileWriter(snapShot);
		 fileWrite=new FileWrite();
		 FileWriteMemento(writeMemento);
	 }
	
	public static void writeMementoFile(JSONArray array) throws IOException, JSONException{
		System.out.println(array);
		for(int i=0;i<array.length();i++){
			writerMemento.write(array.get(i).toString());
			writerMemento.write(System.getProperty("line.separator"));
		}
		writerMemento.flush();
		writerMemento.close();
	}
	public static void writeCommandFile(String command) throws IOException{
		writer1.write(command);
		writer1.write(System.getProperty("line.separator"));
	}
	
	public static void closeFiles() throws IOException{
		writer1.flush();
		writer1.close();
	}
}
