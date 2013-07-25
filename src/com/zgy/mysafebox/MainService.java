package com.zgy.mysafebox;

import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

public class MainService extends Service {
	private Timer mTimer = new Timer(true);

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		TimerTask task = new MainTimerTask(MainService.this);
		mTimer.schedule(task, 1, 1000);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}

	}

	private class MainTimerTask extends TimerTask {

		ActivityManager mActivityManager;

		public MainTimerTask(Context context) {
			mActivityManager = (ActivityManager) context.getSystemService("activity");
		}

		@Override
		public boolean cancel() {
			return super.cancel();
		}

		@Override
		public void run() {
			
			if(MainUtil.isSafing()) {
				ComponentName topActivity = mActivityManager.getRunningTasks(1).get(0).topActivity;
				String packageName = topActivity.getPackageName();
				String className = topActivity.getClassName();
				
				if(packageName!=null && !"com.zgy.mysafebox".equals(packageName)) {
					Log.v("", "packageName =" + packageName);
					Log.v("", "className =" + className);
				}
			}
			
			
		}

	}

}
