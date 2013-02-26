package com.donjpcrepair.aospicw; 

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ServiceStarter extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent("com.donjpcrepair.aospicw.AOSPICWService");
		i.setClass(context, AOSPICWService.class);
		context.startService(i);
	}
}