package com.demo2;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

public class MyNotification extends Activity{
	String ns=Context.NOTIFICATION_SERVICE;
	NotificationManager mNotificationManager=(NotificationManager)getSystemService(ns);
	int icon=R.drawable.ic_launcher;
	String tickerText=null;
	long when=0;
	Notification mNotification=new Notification(icon, tickerText, when);
	
/*	Context context=getApplicationContext();
	String contextTitle="";
	String contentText="";
	Intent notificationIntent=new Intent(this)*/
}
