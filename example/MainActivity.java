package com.your_app_name;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactInstanceManager;

import javax.annotation.Nullable;
import com.dawnchorus.alarms.LauncherModule;

public class MainActivity extends ReactActivity {

    @Override
    protected String getMainComponentName() {
        return "YourAppName";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
    }

    public class AlarmActivityDelegate extends ReactActivityDelegate {
        private final String ALARM_ID = "alarmID";
        private final String MISSED_ALARMS = "missedAlarms";
        private Bundle mInitialProps = null;
        private final @Nullable Activity mActivity; 

        public AlarmActivityDelegate(Activity activity, String mainComponentName) {
            super(activity, mainComponentName);
            this.mActivity = activity;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            mInitialProps = new Bundle();
            final Bundle bundle = mActivity.getIntent().getExtras();
            if (bundle != null && bundle.containsKey(ALARM_ID)) {
                mInitialProps.putString(ALARM_ID, bundle.getString(ALARM_ID));
            }
            if (bundle != null && bundle.containsKey(MISSED_ALARMS)) {
                mInitialProps.putString(MISSED_ALARMS, bundle.getString(MISSED_ALARMS));
            }   
            if (bundle != null && bundle.containsKey("launchAlarm")) {
                if (bundle.getString("launchAlarm").equals("ringtoneOn")) {
                    mInitialProps.putBoolean("alarmOn", true);
                }
            }       
            ReactInstanceManager mReactInstanceManager = getReactNativeHost().getReactInstanceManager();
            ReactApplicationContext context = (ReactApplicationContext) mReactInstanceManager.getCurrentReactContext();    
            if (context == null) {
                mReactInstanceManager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
                    public void onReactContextInitialized(ReactContext context) {
                        if (bundle != null && bundle.containsKey("launchAlarm")) {
                            if (bundle.getString("launchAlarm").equals("ringtoneOn")) {
                                LauncherModule.startAlarm(mActivity); 
                            }
                        }                                
                    }
                });
            } else {
                if (bundle != null && bundle.containsKey("launchAlarm")) {
                    if (bundle.getString("launchAlarm").equals("ringtoneOn")) {
                        LauncherModule.startAlarm(mActivity); 
                    }
                }                  
            }
            super.onCreate(savedInstanceState);
        }

        @Override
        protected Bundle getLaunchOptions() {
            return mInitialProps;
        }
    };

    @Override
    protected ReactActivityDelegate createReactActivityDelegate() {
        return new AlarmActivityDelegate(this, getMainComponentName());
    }
}
