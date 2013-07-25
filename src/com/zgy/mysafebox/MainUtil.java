package com.zgy.mysafebox;

import java.io.File;
import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class MainUtil {

	private static final String SERVICE_NAME = "com.zgy.mysafebox.MainService";
	public static final File FILE_TAG = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/safebox");

	/**
	 * 判断是否开启了监听
	 * 
	 * @Description:
	 * @param context
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-7-25
	 */
	public static void checkService(Context context) {
		if (!isServiceStarted(context, SERVICE_NAME)) {
			Intent i = new Intent(context, MainService.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startService(i);
			Log.v("service", "service is not running, need to start service!");
		} else {
			Log.v("service", "service is running, no need to start service!");
		}
	}

	/**
	 * ͨ服务是否开启
	 * 
	 * @param mServiceList
	 * @param className
	 * @return
	 */
	private static boolean isServiceStarted(Context context, String serviceName) {

		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> mServiceList = activityManager.getRunningServices(100);

		for (int i = 0; i < mServiceList.size(); i++) {
			if (serviceName.equals(mServiceList.get(i).service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断存储卡是否存在
	 * 
	 * @return
	 */
	public static boolean existSDcard() {
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {
			return true;
		} else
			return false;
	}

	public static boolean isSafing() {
		if (existSDcard()) {
			if (FILE_TAG.exists()) {
				return true;
			}
		}
		return false;

	}

	public static void addDelTag(boolean add) {
		if (existSDcard()) {
			if (add) {
				if (!FILE_TAG.exists()) {
					FILE_TAG.mkdirs();
				}
			} else {
				if (FILE_TAG.exists()) {
					FILE_TAG.delete();
				}
			}

		}
	}
}
