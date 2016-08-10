package com.forecast.app;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
//this starts the dialog fragment with all the notifiaction in it
public class AlertFragment_Act extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		//this creates the new dialog fragment
		DialogFragment_Frag alert = new DialogFragment_Frag();

		//this opens the dialog once the alarm goes off

		alert.show(getFragmentManager(), "DialogFragment_Frag");

	}


}
