package com.forecast.app;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


//customized array adapter for the list view in the ViewAllNotifications class 
//
public class NotificationAdapter_Class extends ArrayAdapter<Rowitem> {
	Context context;
	
	public NotificationAdapter_Class(Context context, int resourceID, List<Rowitem> notificationItems) {
		super(context, resourceID);
		this.context = context;
	}
	
	private class ViewHolder {
	
		TextView textName;

	}
	
	@SuppressLint("InflateParams") public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Rowitem item = getItem(position);
		
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_allnotifications_row, null);
			
			holder = new ViewHolder();
			holder.textName= (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		
		holder.textName.setText(item.getTitle());
		
		
		return convertView;
		
	}

	  
}


