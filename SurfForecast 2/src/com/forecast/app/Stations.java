package com.forecast.app;

import java.util.ArrayList;
//singleton class that keeps track of random station variables for us. 
//also has a list of all the stations for checking against user input
public class Stations {
	
	private static Stations instance = null;
	private String name;
	private String id;
	private String userInputName = null;
	private int pendingIntent =0; 
	private String notificationTitle = null;
	private ArrayList<Stations> stationList = new ArrayList<Stations>();
	private ArrayList<Helper> helperList = new ArrayList<Helper>();
	
	public static Stations initializeSingleton(){
		if(instance == null){
			instance = new Stations();
		}
		return instance;
	}
	public void addToHelperList(Helper helper){
		helperList.add(helper);
	}
	public ArrayList<Helper> getHelperList(){
		return this.helperList;
	}
	public int setPendingIntentId(int pendingIntentId){
		this.pendingIntent = pendingIntentId;
		return this.pendingIntent;
		
	}
	public int getPendingIntentId(){
		return this.pendingIntent;
		
	}
	
	public void setNotificationTitle(String notificationTitle){
		this.notificationTitle = notificationTitle;
		
		
	}
	public String getNotificationTitle(){
		return this.notificationTitle;
		
		
	}
	
	
	
	public void setUserName(String userName){
		this.userInputName = userName;
	}
	
	public String getUserInputName(){
		return userInputName;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	public String getId(){
		return id;
	}

	public void addStation(Stations station){
		stationList.add(station);
	}
	
	public void removeStation(int index){
		stationList.remove(index);
	}
	public Stations getStationAtId(int index){
		return stationList.get(index);
	}
	
	public ArrayList<Stations> getList(){
		return stationList;
	}
	public boolean isValid(){
		boolean decider = false;
		for(int i = 0; i < helperList.size(); ++i){
			
			if(userInputName.equalsIgnoreCase(helperList.get(i).name)){
				decider = true;
			}
			else{
				System.out.println("Not in list."); // just to check for now maybe remove later
				
			}
			
			
		}
		return decider;
	}
	
	public String getIdByName(){
		String id = null;
		
		for(int i = 0; i < helperList.size(); ++i){
			
			if(userInputName.equalsIgnoreCase(helperList.get(i).name)){
				
				id =  helperList.get(i).id;
			}
			else
				System.out.println("This id is not in the list."); // just to check for now maybe remove later
		}
		return id;
	}
	
	
}
