package com.soyu.mycall.season2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class VibratorService extends Service {
    //  Static
    private static final String TAG = VibratorService.class.getSimpleName();

    public VibratorService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "MY CALL";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "MyCall", NotificationManager.IMPORTANCE_DEFAULT);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();
            startForeground(1, notification);
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");

        Vibrator mVibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {700, 300, 700, 100};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mVibe.vibrate(VibrationEffect.createWaveform(pattern, 0));
        } else {
            mVibe.vibrate(pattern, 0); //deprecated in API 26
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");

        Vibrator mVibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mVibe.cancel();
        super.onDestroy();
    }
}