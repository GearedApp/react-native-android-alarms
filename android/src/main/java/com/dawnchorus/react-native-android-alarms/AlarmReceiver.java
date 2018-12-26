package com.dawnchorus.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
	
    @Override
    public void onReceive(Context context, Intent intent) {
        String alarmID = intent.getAction();
		String sendAlarmOn = "sendAlarmOn";
        launchApplication(context, alarmID, sendAlarmOn);
    }

    private void launchApplication(Context context, String alarmID, String sendAlarmOn) {
        String packageName = context.getApplicationContext().getPackageName();
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);

        launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        launchIntent.putExtra("alarmID", alarmID);
		launchIntent.putExtra("sendAlarm", sendAlarmOn);
		
        context.startActivity(launchIntent);
        Log.i("ReactNativeAppLauncher", "AlarmReceiver: Launching: " + packageName + " " + alarmID);
    }        
}