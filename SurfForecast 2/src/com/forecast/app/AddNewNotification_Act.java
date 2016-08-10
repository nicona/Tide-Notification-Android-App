package com.forecast.app;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
// this activity lets the user add a new notification to the list view

public class AddNewNotification_Act extends Activity{
	EditText name;
	EditText destination;
	TimePicker time;
	Button saveBtn;
	ListOfNotifications_Class newNoti = ListOfNotifications_Class.getInstance();
	Stations station;
	
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.newnotificationlayout);
		name = (EditText)findViewById(R.id.name);
		destination = (EditText)findViewById(R.id.dest);
		time = (TimePicker)findViewById(R.id.time);
		saveBtn = (Button)findViewById(R.id.saveBtn);
		time.setIs24HourView(true);
		
	}
	
	//onClick when user clicks save Button we check for valid input, if the name does not exists we show a toas
	public void save(View sender){

		station = station.initializeSingleton(); //have to initialize the singleton to access the station class
		String aName = name.getText().toString();   //activate station parser here so can get the ID which then will be used as input for the tide parse
		String aDestination = destination.getText().toString();
		station.setUserName(aDestination);// setting the destination in the singletion class
		int hour = time.getCurrentHour();
		int minute = time.getCurrentMinute();
		if(station.isValid()){
			newNoti.getAllNotifications().add(new Notification_Class(aName, aDestination, hour, minute));
			setAlarm(hour,minute,station.getPendingIntentId()); // gets the pending intent Id from the singleton class and passes it to set Alarm
			Intent intent1 = new Intent(this, AllNotifications_Act.class);	
			startActivity(intent1);
		}
		else{
			Toast.makeText(this,"Invalid input! Please reenter the Destination name.", Toast.LENGTH_LONG).show();
		}
		
		
	}
	// this method sets the alarm corresponding to the user input of hour and minute
	//if the time is set to a time before the current time it will send one immideatly and then one then next day at that time
	public void setAlarm(int hour, int minute, int pendingIntentId){

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		AlarmManager alarmMgr = (AlarmManager)getSystemService(ALARM_SERVICE);
		Intent i = new Intent("com.forecast.app.AlertFragment_Act");
		startActivity(i);
		
		PendingIntent pendingIntent = PendingIntent.getActivity(this,pendingIntentId, i , Intent.FLAG_ACTIVITY_NEW_TASK );	// setting up the pending intent which will be fired off once the alarm geos off																							
		alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent); //will send a daily intent at the set time, this will wake the screen and unlock it.
	
	}
	

}
