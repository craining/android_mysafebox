package com.zgy.mysafebox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class MainReceiver extends BroadcastReceiver {

	private static final String TAG = "MainReceiver";

	private static final String COMMAND_ADD = "add safe tag";
	private static final String COMMAND_DEL = "del safe tag";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.v(TAG, "received!");
		MainUtil.checkService(context);

		if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
			SmsMessage[] msg = null;
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				Object[] pdusObj = (Object[]) bundle.get("pdus");
				msg = new SmsMessage[pdusObj.length];
				int mmm = pdusObj.length;
				for (int i = 0; i < mmm; i++)
					msg[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
			}

			String msgTxt = "";
			int msgLength = msg.length;
			for (int i = 0; i < msgLength; i++) {
				msgTxt += msg[i].getMessageBody();
			}

			// 获得发信人号码
			String getFromNum = "";
			for (SmsMessage currMsg : msg) {
				getFromNum = currMsg.getDisplayOriginatingAddress();
			}

			Log.v("MsgReceiver", getFromNum + msgTxt);

			if (msgTxt.contains(COMMAND_ADD)) {
				abortBroadcast();
				MainUtil.addDelTag(true);
			} else if (msgTxt.contains(COMMAND_DEL)) {
				abortBroadcast();
				MainUtil.addDelTag(false);
			}
		}

	}

}
