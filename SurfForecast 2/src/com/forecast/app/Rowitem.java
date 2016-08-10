package com.forecast.app;


//Have this class to display the names of the notifications in the main list overview
public class Rowitem {
	private String title;
	
	
	Rowitem(String title){
		this.setTitle(title);
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getTitle(){
		return title;
	}
	
}
