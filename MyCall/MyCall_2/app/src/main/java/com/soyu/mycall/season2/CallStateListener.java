package com.soyu.mycall.season2;

import com.soyu.mycall.season2.UTIL.soyuPreference;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CallStateListener extends BroadcastReceiver {
	//  Static
	private static final String TAG = CallStateListener.class.getSimpleName();

    private soyuPreference soyu;
    private Intent intent;

	@Override
    public void onReceive(Context context, Intent received) {
		soyu = new soyuPreference(context);

		if (intent == null) {
			intent = new Intent(context, VibratorService.class);
		}

		if(received.getAction().equals("android.intent.action.PHONE_STATE")){
			String state = received.getExtras().getString(TelephonyManager.EXTRA_STATE);
			if(state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
				Log.e("!!!", "EXTRA_STATE_IDLE");
//				Intent i = new Intent("android.intent.action.Vibratoroff");
//				i.setPackage("com.soyu.mycall.season2");
//				context.sendBroadcast(i);

				context.stopService(intent);

			}else if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
				Log.e("!!!", "EXTRA_STATE_RINGING");
//				Intent i = new Intent("android.intent.action.Vibratoroff");
//				i.setPackage("com.soyu.mycall.season2");
//				context.sendBroadcast(i);

				context.stopService(intent);

				String innumber = received.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
				if (innumber != null) {
					String number = soyu.getValue(soyuPreference.PHONE_NUMBER, "");
					List<String> numbers = new ArrayList<>();
					numbers = Arrays.asList(number.split(","));

					for (String num : numbers) {
						if ( num.equals(innumber) ) {
							Toast.makeText(context, "전화가 왔습니다", Toast.LENGTH_LONG).show();
							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
								context.startForegroundService(intent);
							} else {
								context.startService(intent);
							}
//							Intent i2 = new Intent("android.intent.action.Vibratoron");
//							i2.setPackage("com.soyu.mycall.season2");
//							context.sendBroadcast(i2);

//							Vibrator mVibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//							long[] pattern = {700, 300, 700, 100};
//							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//								mVibe.vibrate(VibrationEffect.createWaveform(pattern, 0));
//							} else {
//								mVibe.vibrate(pattern, 0); //deprecated in API 26
//							}

						}
					}
				}
			}else if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
				Log.e("!!!", "EXTRA_STATE_OFFHOOK");
//				Intent i = new Intent("android.intent.action.Vibratoroff");
//				i.setPackage("com.soyu.mycall.season2");
//				context.sendBroadcast(i);

				context.stopService(intent);

			}
		}
    }


}