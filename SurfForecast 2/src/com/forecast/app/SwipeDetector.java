package com.forecast.app;

import android.view.MotionEvent;
import android.view.View;

//swipe detector class to determine the swipe right to left action in the list view activity
//used stackoverflow and the android developer tools for help;
public class SwipeDetector implements View.OnTouchListener {

    public static enum Action {
        LR, // Left to right
        RL, // Right to left
        TB, // Top to bottom
        BT, // Bottom to top
        None // Action not found
    }

    private static final int HORIZONTAL_MIN_DISTANCE = 30; // The minimum

    private static final int VERTICAL_MIN_DISTANCE = 80; // The minimum distance

    private float downX, downY, upX, upY; 
    private Action mSwipeDetected = Action.None; 

    public boolean swipeDetected() {
        return mSwipeDetected != Action.None;
    }

    public Action getAction() {
        return mSwipeDetected;
    }

    
     public boolean onTouch(View v, MotionEvent event) {
    	 switch (event.getAction()) {
    	 case MotionEvent.ACTION_DOWN: {
    		 downX = event.getX();
    		 downY = event.getY();
    		 mSwipeDetected = Action.None;
    		 return false; // allow other events like Click to be processed
    	 }
    	 case MotionEvent.ACTION_MOVE: {
    		 upX = event.getX();
    		 upY = event.getY();

    		 float deltaX = downX - upX;
    		 float deltaY = downY - upY;

    		 // horizontal swipe detection
    		 if (Math.abs(deltaX) > HORIZONTAL_MIN_DISTANCE) {
    			 // left or right
    			 if (deltaX < 0) {

    				 mSwipeDetected = Action.LR;
    				 return true;
    			 }
    			 if (deltaX > 0) {

    				 mSwipeDetected = Action.RL;
    				 return true;
    			 }
    		 } else 

    			 // vertical swipe detection
    			 if (Math.abs(deltaY) > VERTICAL_MIN_DISTANCE) {
    				 // top or down
    				 if (deltaY < 0) {

    					 mSwipeDetected = Action.TB;
    					 return false;
    				 }
    				 if (deltaY > 0) {

    					 mSwipeDetected = Action.BT;
    					 return false;
    				 }
    			 } 
    		 return true;
    	 }
    	 }
    	 return false;
     }

}