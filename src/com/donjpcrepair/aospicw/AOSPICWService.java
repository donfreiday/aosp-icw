package com.donjpcrepair.aospicw;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class AOSPICWService extends Service {

	private PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			KeyguardManager mKeyGuardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
			KeyguardLock mLock = mKeyGuardManager.newKeyguardLock("activity_classname");
			
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				mLock.disableKeyguard();
				break;
			case TelephonyManager.CALL_STATE_IDLE:
				mLock.reenableKeyguard();
				break;
			}
		}
	};
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		mTelephonyManager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// for communication return IBinder implementation
		return null;
	}
} 