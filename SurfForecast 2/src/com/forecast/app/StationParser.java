package com.forecast.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;


//this class parses the saved XML file with all the stations and ID's and adds them to an Arraylist
//so far we only do california, but can easliy expand by downloading and formating XMLs from NOAA.gov
public class StationParser  {
	
	Stations station;

	
	public void parseCall(InputStream is){
		parse(is);
	}

	
	private void parse(InputStream is)
	{

		XmlPullParserFactory factory = null;
		XmlPullParser parser = null;
		String text = null;
		String nameTemp = null;
		String idTemp = null;
		ArrayList<Helper> helperList = new ArrayList<Helper>();
		
		boolean idCheck = false;
		boolean nameCheck = false;
		
		
		try {

			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			parser = factory.newPullParser();
			parser.setInput(is, null);

			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				
				String tagname = parser.getName();
				
				if(eventType == XmlPullParser.TEXT){
					text = parser.getText();
					
				}
				if(eventType == XmlPullParser.END_TAG)
				{
					
					if (tagname.equalsIgnoreCase("name")) 
					{
				
						nameTemp = text;
						nameCheck = true;
					}
					else if(tagname.equalsIgnoreCase("id")){
						
					
						idTemp = text;
						idCheck = true;
					}
				}
				if(nameCheck == true && idCheck == true){
					station = station.initializeSingleton();
					Helper helper = new Helper();
					helper.name = nameTemp;
					helper.id = idTemp;
					station.addToHelperList(helper);
					station.setName(nameTemp);
					station.setId(idTemp);
					
					station.addStation(station);
					
					nameCheck = false;
					idCheck = false;
				}
			
				eventType = parser.next();
			}
		}
		catch (XmlPullParserException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}