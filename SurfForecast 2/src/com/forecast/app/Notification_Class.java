package com.forecast.app;

import android.widget.TextView;


//this class holds all values of the actual notification set by the user

public class Notification_Class {
	
	
	private String name;
	private String destination;
	private int hour;
	private int minute;
	private int pendingIntentId;

	Stations station = Stations.initializeSingleton();
	//constructor to set all the notification info when creating a new one
	Notification_Class(String name, String destination, int hour, int minute){
		this.setName(name);
		this.setDest(destination);
		this.setHour(hour);
		this.setMinute(minute);
		int i_d = station.getPendingIntentId();
		this.pendingIntentId = (station.setPendingIntentId(i_d++));
	}
	
	
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setDest(String destination){
		this.destination = destination;
	}
	
	public void setHour(int hour){
		this.hour = hour;
	}
	public void setMinute(int minute){
		this.minute = minute;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDestination(){
		return destination;
	}
	
	public int getHour(){
		return hour;
	}
	public int getMinute(){
		return minute;
	}
	
	
	

}
