package com.forecast.app;

import java.util.concurrent.ExecutionException;

//class that executes the PARSEHTML class
//returns the parsed data 
public class OnlineData {
	
	
	private String data;
	Stations station;
	
	public void setData(String data){
		this.data = data;
	}
	
	
	public String getData(){
		parseData();
		
		return data;

	}
	
	
	public void parseData(){
		String tempData;
		ParseTide parser = new ParseTide();
		try {
			
			tempData = parser.execute().get();
			setData(tempData);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
