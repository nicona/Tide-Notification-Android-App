package com.forecast.app;

import java.util.ArrayList;


//the list of all the notifications

public class ListOfNotifications_Class {
	private int currentNotification;
	public static ListOfNotifications_Class instance;
	private ArrayList<Notification_Class> allNotifications = new ArrayList<Notification_Class>();
	
	ListOfNotifications_Class(){
		
		
	}
	
	public static ListOfNotifications_Class getInstance()
	{
		if (instance == null)
		{
			instance = new ListOfNotifications_Class();
		}

		return instance;
	}

	public ArrayList<Notification_Class> getAllNotifications() {
		return allNotifications;
	}
	
	
	
	//getters at id
	
	public String getNameAtId(int id)
	{
		return allNotifications.get(id).getName();
	}
	public String getDestAtId(int id){
		
		return allNotifications.get(id).getDestination();
	}
	
	public int getHourAtId(int id){
		return allNotifications.get(id).getHour();
	}
	
	public int getMinuteAtId(int id){
		return allNotifications.get(id).getMinute();
	}
	
	public int getCurrentNotification(){
		return currentNotification;
	}
	
	public void setCurrentNotification(int currentNotification){
		this.currentNotification = currentNotification;
	}
	//setters at id's
	public void setNameAtId(String name, int id){
		getAllNotifications().get(id).setName(name);
	}
	public void setDestAtId(String dest, int id){
		getAllNotifications().get(id).setDest(dest);	
	}
	
	public void setHourAtId(int hour, int id){
		getAllNotifications().get(id).setHour(hour);
	}
	
	public void setMinuteAtId(int minute, int id){
		getAllNotifications().get(id).setMinute(minute);
	}
	//remove at position
	public void deleteItemInList(int id){
		
		getAllNotifications().remove(getAllNotifications().get(id));
		
	}
	

	
}
