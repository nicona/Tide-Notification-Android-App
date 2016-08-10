package com.forecast.app;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

//this is the edit notification activity in case the user clicks on a item in the listview to edit his notification
public class EditNotification_Act extends Activity{
	Button saveChanges;
	Button deleteInEdit;
	EditText name;
	EditText destination;
	TimePicker time;
	ListOfNotifications_Class notiData = ListOfNotifications_Class.getInstance();
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_notification_layout);
		saveChanges = (Button)findViewById(R.id.saveEdit);
		deleteInEdit = (Button)findViewById(R.id.deleteEditBtn);
		
		
		name = (EditText)findViewById(R.id.nameEditTxt);
		destination = (EditText)findViewById(R.id.destEditTxt);
		time = (TimePicker)findViewById(R.id.timePicker1);
		time.setIs24HourView(true);
		
		Intent intent = getIntent();
		name.setText(intent.getCharSequenceExtra("name").toString());
		destination.setText(intent.getCharSequenceExtra("dest").toString());
		time.setCurrentHour(intent.getIntExtra("hour", 0));
		time.setCurrentMinute(intent.getIntExtra("minute", 0));
		
	}

	public void saveChangesBtn(View sender){

		
		Intent i2 = getIntent();
		long currentId = i2.getLongExtra("id", 0);
		//set the edit text fields to user input
		notiData.setNameAtId(name.getText().toString(),(int) currentId);
		notiData.setDestAtId(destination.getText().toString(), (int)currentId);
		notiData.setHourAtId(time.getCurrentHour(), (int) currentId);
		notiData.setMinuteAtId(time.getCurrentMinute(), (int) currentId);
		cancelAlarm((int)currentId); // cancel the old alarm and create a new one;
		setAlarm(time.getCurrentHour(),time.getCurrentMinute(), (int) currentId);
		
		
		//opens a dialog to ask user again if he she is sure to save the changes
		new AlertDialog.Builder(this).setTitle("Save Changes").setMessage("Are you sure you want to save the changes?")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	Intent intent = new Intent(EditNotification_Act.this, AllNotifications_Act.class);
	    		startActivity(intent);
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	    .show();

	}
	
	//asks them again if they really want to delete
	public void deleteInEdit(View sender){

		Intent i2 = getIntent();
		
		final long currentId = i2.getLongExtra("id", 0);
		notiData.setNameAtId("",(int) currentId);
		notiData.setDestAtId("", (int)currentId);
		notiData.setHourAtId(0, (int) currentId);
		notiData.setMinuteAtId(0, (int) currentId);
		
		// opens an alert dialog to make sure that the user really wants to delete his notification
		new AlertDialog.Builder(this).setTitle("Delete").setMessage("Are you sure you want to delete the notification?")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	cancelAlarm((int)currentId);
	        	notiData.deleteItemInList((int)currentId); 
	        	Intent intent = new Intent(EditNotification_Act.this, AllNotifications_Act.class);
	    		startActivity(intent);
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	    .show();
	}
	//cancel the alarm whith the specific pending intent
	public void cancelAlarm(int pendingIntentId){

		Intent i = new Intent("com.forecast.app.AlertFragment_Act");
		startActivity(i);
		PendingIntent pendingIntent = PendingIntent.getActivity(this,pendingIntentId, i , Intent.FLAG_ACTIVITY_NEW_TASK );
		AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
	    alarmManager.cancel(pendingIntent);
	}
	
	//set new alarm when modifying the data and save changes button is pressed
	public void setAlarm(int hour, int minute, int pendingIntentId){

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		AlarmManager alarmMgr = (AlarmManager)getSystemService(ALARM_SERVICE);
		Intent i = new Intent("com.forecast.app.AlertFragment_Act");
		startActivity(i);
		
		PendingIntent pendingIntent = PendingIntent.getActivity(this,pendingIntentId, i , Intent.FLAG_ACTIVITY_NEW_TASK );
 																										

		alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent); //works 
		

	}
	
	
	
}
