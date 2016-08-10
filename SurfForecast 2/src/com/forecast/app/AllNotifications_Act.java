package com.forecast.app;


import java.io.InputStream;
import java.util.ArrayList;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

//displays all the notifications with the customized adapter in the list

public class AllNotifications_Act extends Activity {

	LinearLayout lay;	
	ProgressDialog mProgressDialog;
	private ListView notificationList;
	public ArrayList<Rowitem> notificationItems;
	NotificationAdapter_Class adapter;
	ListOfNotifications_Class notiData = ListOfNotifications_Class.getInstance(); //the actual list with all notifications in it
	
	Button delBtn;
	Button addNotification;
	private String hBreak;
	
	public void setHbreak(String hBreak){
		this.hBreak = hBreak;
	}
	
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notificationslayout);
		addNotification = (Button)findViewById(R.id.addNewBtn);
		
		
		notificationList = (ListView)findViewById(R.id.list);
		lay = (LinearLayout)findViewById(R.id.test);
		DownloadImage download = new DownloadImage();
		download.execute("http://cdn.surf-one.com/media/wysiwyg/surfone/home/sunset-surf.jpg"); //calls the asynctask below to download the background picture
		notificationItems = new ArrayList<Rowitem>();
		adapter = new NotificationAdapter_Class (this, R.layout.item_allnotifications_row, notificationItems);      
		fillNotificationList();
		final SwipeDetector swipeDetector = new SwipeDetector(); //swipe detector to delete the notifications in the list by swiping from right to left
		notificationList.setOnTouchListener(swipeDetector);
		
		//referenced the android developer website and stackoverflow for help
		//this makes the delete button visible when swiping from right to left
		notificationList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, final View view,  final int position, final long id) {
				
				
				delBtn = (Button)view.findViewById(R.id.deleteBtn);
				
				if (swipeDetector.swipeDetected()){
					if (swipeDetector.swipeDetected()) {

						if (swipeDetector.getAction() == SwipeDetector.Action.RL) {

							delBtn.setVisibility(View.VISIBLE);
							delBtn.setOnClickListener(new OnClickListener(){

								@Override
								public void onClick(View v) {
									cancelAlarm((int)id);// calls cancel alarm which looks at the pendingIntent id and cancels specific alarm
									notiData.deleteItemInList((int)id);
									refreshData();	
								}
								
							});
						}
						if( swipeDetector.getAction() == SwipeDetector.Action.LR){
							delBtn.setVisibility(View.INVISIBLE);
						}

					}

				} else {
					//if user clicks onto the list item we start a new activity
					//send all the information extra to fill the edittext boxes and set the time 
					notiData.setCurrentNotification((int) id);
					Intent intent = new Intent(AllNotifications_Act.this, EditNotification_Act.class);
					intent.putExtra("id", id);
					intent.putExtra("name", notiData.getNameAtId((int)id));
					intent.putExtra("dest", notiData.getDestAtId((int)id));
					intent.putExtra("hour", notiData.getHourAtId((int)id));
					intent.putExtra("minute", notiData.getMinuteAtId((int)id));
					startActivity(intent);
				}
			}
		});

		
	}
	//if delete by swipe cancel alarm with the specific pending intent
	public void cancelAlarm(int pendingIntentId){

		Intent i = new Intent("com.forecast.app.AlertFragment_Act");
		startActivity(i);
		PendingIntent pendingIntent = PendingIntent.getActivity(this,pendingIntentId, i , Intent.FLAG_ACTIVITY_NEW_TASK );
		AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
	    alarmManager.cancel(pendingIntent);
	}
	
	//if the user uses the delete by swipe method refresh the listview so items that are deleted are actually removed from the list view
	private void refreshData(){
		
		if(notiData.getAllNotifications().size() == 0){
			adapter.clear();
			notificationList.setAdapter(adapter);
		}
		else{
			adapter.clear();
			for (int i=0; i < notiData.getAllNotifications().size(); i++)
			{
				Rowitem item = new Rowitem(notiData.getAllNotifications().get(i).getName());
				adapter.add(item);
			}
			notificationList.setAdapter(adapter);

		}
		
	}
	//fill the notification list with the notiData ArrayList 
	private void fillNotificationList() {

		if(notiData.getAllNotifications() != null)
		{
			notificationItems.clear();

			for (int i=0; i < notiData.getAllNotifications().size(); i++)
			{
				Rowitem item = new Rowitem(notiData.getAllNotifications().get(i).getName());
				adapter.add(item);
			}
		}
		
		notificationList.setAdapter(adapter);
	}

	// when user presses add a new notification sends him/her to next activity where they can add
	public void addNotificationClick(View sender){

		Intent intent = new Intent(this, AddNewNotification_Act.class);
		startActivity(intent);
	}

	
	//download the image from the URL from the top
	//reference Android developer for help
	private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Toast.makeText(AllNotifications_Act.this, "Background Image is loading...", Toast.LENGTH_SHORT).show();
			
		}

		@Override
		protected Bitmap doInBackground(String... URL) {

			String imageURL = URL[0];

			Bitmap bitmap = null;
			try {
				InputStream input = new java.net.URL(imageURL).openStream();
				bitmap = BitmapFactory.decodeStream(input);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bitmap;
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(Bitmap result) {
			@SuppressWarnings("deprecation")
			BitmapDrawable ob = new BitmapDrawable(result);
			lay.setBackgroundDrawable(ob);
			//mProgressDialog.dismiss();
		}
	}


}




