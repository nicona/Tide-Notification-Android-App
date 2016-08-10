package com.forecast.app;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;


//this fragment creates the notificaiton that shows up on the users screen at the set time
public class DialogFragment_Frag extends DialogFragment{
	

	public CharSequence title;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		
		OnlineData tides = new OnlineData(); //class in which i format the tide data and call the parseHtml class to parse the website;
	
		
		getActivity().getWindow().addFlags(LayoutParams.FLAG_TURN_SCREEN_ON | LayoutParams.FLAG_DISMISS_KEYGUARD);//turns the screen on and unlocks it when called
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		Stations station = Stations.initializeSingleton(); //initialize the singleton the get the user input name and display it in the notification title
		
		
		builder.setTitle(station.getUserInputName());
		builder.setMessage(tides.getData()); // set message to the data we retrieved from the Tide Forecast website
		
		//set a button for the user to acknowledge the information
		builder.setNeutralButton("OK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				getActivity().finish();
			}
		});
		return builder.create();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().finish();
	}

}
