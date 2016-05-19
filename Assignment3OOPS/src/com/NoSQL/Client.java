package com.NoSQL;

import java.io.IOException;
import java.util.Scanner;
import org.json.JSONException;
import com.command.*;

public class Client {
public static void main(String[] args) throws JSONException, IOException, IllegalAccessException{
		
		Database database = new Database();
		database.executeCommand();
		int choice,count=0;
		boolean result=false;
		DatabaseInvoker dbInvoker=new DatabaseInvoker();
		AddData addData=new AddData(database);
		GetData getData=new GetData(database);
		RemoveData removeData=new RemoveData(database);
		ReplaceData replaceData=new ReplaceData(database);
		Transaction sample;
	//	Observer obs1=new Observer1();	
		Cursor cursor=new Cursor();
	//	cursor.addObserver(obs1);
	
		do{
			if(result){
				count++;
			}
			System.out.println("WELCOME TO THE DATABASE : ");
			System.out.println("\tMENU");
			System.out.println("1.ADD\n2.REMOVE\n3.GET\n4.REPLACE\n5.SAVE DATABASE STATE\n6.RESTORE PREVIOUS STATE\n7.Cursor\n8.Transaction\n9.EXIT");
			Scanner inputChoice = new Scanner(System.in);
			choice=inputChoice.nextInt();
			if(choice==1){
				System.out.println("Enter your Key : ");
				Scanner inputKey = new Scanner(System.in);
				String key=inputKey.next();
				System.out.println("Enter your Value : ");
				Scanner inputValue = new Scanner(System.in);
				Object value=(Object)inputValue.next();
		//		System.out.println(value.getClass().getName());
				addData.put(key,value);
				dbInvoker.addDatabaseCommand(addData);
				result=dbInvoker.runCommand(count);
			}
			else if(choice==2){
				System.out.println("Enter your Key : ");
				Scanner inputKey = new Scanner(System.in);
				String key=inputKey.next();
				removeData.remove(key);
				dbInvoker.addDatabaseCommand(removeData);
				result=dbInvoker.runCommand(count);
			}
			else if(choice==3){
				System.out.println("Enter your Key : ");
				Scanner inputKey = new Scanner(System.in);
				String key=inputKey.next();	
				getData.get(key);
				dbInvoker.addDatabaseCommand(getData);
				result=dbInvoker.runCommand(count);
			}
			else if(choice==4){
				System.out.println("Enter your Key : ");
				Scanner inputKey = new Scanner(System.in);
				String key=inputKey.next();
				System.out.println("Enter your Value : ");
				Scanner inputValue = new Scanner(System.in);
				Object value=inputValue.next();
				replaceData.replace(key, value);
				dbInvoker.addDatabaseCommand(replaceData);
				result=dbInvoker.runCommand(count);
			}
			else if(choice==5){
				database.snapShot();
				count=0;
			}
			else if(choice==6){
				database.recover();
			}
			else if(choice==7){
				System.out.println("Enter your Key : ");
				Scanner inputKey = new Scanner(System.in);
				String key=inputKey.next();	
				Cursor data=database.getCursor(key);
				Observer1 obs=new Observer1();
				cursor.addObserver(obs);
		}
			else if(choice==8){
				sample=database.transaction();
				sample.put("trans2", 50);
				sample.put("trans3", 60);
				System.out.println(sample.isActive());
				sample.commit();
			}}while(choice!=9);
		database.closeFiles();
}	
}
