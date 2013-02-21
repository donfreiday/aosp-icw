package com.donjpcrepair.aospicw;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TextView status = (TextView) findViewById(R.id.status);
        if (isServiceRunning()) {
        	status.setText("Service started.");
        }
        else {
        	status.setText("Service stopped.");
        } 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void startService(View v) {
        Intent service = new Intent(this, AOSPICWService.class);
        startService(service);
        
        TextView status = (TextView) findViewById(R.id.status);
        status.setText("Service started.");
        
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, i, Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setContentTitle("AOSP Call Workaround");
        //builder.setContentText("This is the text");
        builder.setSubText("Service is running.");
        //builder.setNumber(101);
        builder.setContentIntent(pendingIntent);
        builder.setTicker("AOSP Call Workaround");
        builder.setSmallIcon(R.drawable.ic_launcher);;
        builder.setAutoCancel(true);
        builder.setPriority(0);
        builder.setOngoing(true);
        Notification notification = builder.build();
        NotificationManager notificationManager = 
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(7777, notification);
    }
    public void stopService(View v) {
        Intent service = new Intent(this, AOSPICWService.class);
        stopService(service);
        
        TextView status = (TextView) findViewById(R.id.status);
        status.setText("Service stopped.");
        
        NotificationManager notificationManager = 
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(7777); 
    }
    
    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (AOSPICWService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    
}
