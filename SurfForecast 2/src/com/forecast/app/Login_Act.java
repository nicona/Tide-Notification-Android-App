package com.forecast.app;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
// here we get the user's information
//when the user swipes to the right he will get to the next screen 
//original plan was to set the picture of the next activity to whatever the user inputed,
//however we ran into runtime problems because parsing would slow the GUI thread down.
public class Login_Act extends Activity{
	
	
	EditText homebreak;
	String userHomeBreak;
	Intent intent;
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);	
		setContentView(R.layout.mainlayout);
		homebreak = (EditText)findViewById(R.id.homeBreakEdit);
		
		try {
            StationParser parser = new StationParser();
            parser.parseCall(getAssets().open("californiastationid.xml"));
            
        } catch (IOException e) {
        	
            e.printStackTrace();
        }
		
		
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}
	//this the swipe listener which checks for a swipe detection of the user 
	//referenced stackoverflow for this
	SimpleOnGestureListener simpleOnGestureListener = new SimpleOnGestureListener(){

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			intent = new Intent(Login_Act.this, AllNotifications_Act.class);
			String swipe = "Swipe to the right or left.";
			float sensitvity = 50;

			// TODO Auto-generated method stub
			if((e1.getX() - e2.getX()) > sensitvity){
				userHomeBreak = homebreak.getText().toString();
				intent.putExtra("homebreak", userHomeBreak);
				startActivity(intent);
			}else if((e2.getX() - e1.getX()) > sensitvity){
				userHomeBreak = homebreak.getText().toString();
				intent.putExtra("homebreak", userHomeBreak);
				startActivity(intent);
			}else{
				Toast.makeText(Login_Act.this, swipe, Toast.LENGTH_SHORT).show();
			}
			return super.onFling(e1, e2, velocityX, velocityY);
		}
	};

	GestureDetector gestureDetector= new GestureDetector(simpleOnGestureListener);
	
	
}

