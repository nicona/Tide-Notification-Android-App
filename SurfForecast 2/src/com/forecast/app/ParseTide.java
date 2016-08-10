package com.forecast.app;

import java.io.IOException;
import java.util.ArrayList;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import android.content.Context;
import android.os.AsyncTask;


//this class uses JSoup to retrieve the data from the URL below
public class ParseTide extends AsyncTask<Void, Void, String> {
	Context context;
	
	String data;	

	private String id;
	Stations station;
	
	
	ParseTide(){
	}


	public void setId(String id){

		this.id = id;		
	}
	
	public String getId(){
		
		return this.id;
	}
	
	public void onPreExecute() {
		super.onPreExecute();
		station = station.initializeSingleton(); //using the singleton to get the ID corresponding to a particular beach, need this to add to the URL
	}
	
	String URL; 
	protected String doInBackground(Void... params) {
		try {
			String temp = station.getIdByName();
			String url = "http://co-ops.nos.noaa.gov/stationhome.html?id="; //format the URL string by adding the ID corresponding to the user input
			url+= temp;
			System.out.println(url +" ****** new URL");
			Document doc = Jsoup.connect(url).timeout(20*1000).get();
			Elements links = doc.select("div[class=span4 well]");//this will get the Tide data at the NOAA website
			for(Element element : links){
				data = element.text();	
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;

	}

	protected void onPostExecute(Void result) {

	}
}



	

