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
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView status; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        status = (TextView) findViewById(R.id.status);

        if (isServiceRunning()) {
        	status.setText("Service started.");
        }
        else {
        	status.setText("Service stopped.");
        } 
    }
    
    public void startService(View v) {
        startService(new Intent(this, AOSPICWService.class));
        status.setText("Service started.");

    }
    
    public void stopService(View v) {
        stopService(new Intent(this, AOSPICWService.class));
        status.setText("Service stopped.");
        
        NotificationManager notificationManager = 
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1); 
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
