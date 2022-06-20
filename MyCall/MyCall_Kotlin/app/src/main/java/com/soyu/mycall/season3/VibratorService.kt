package com.soyu.mycall.season3

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationCompat

class VibratorService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()

        val CHANNEL_ID = "MY CALL"
        val channel = NotificationChannel(
            CHANNEL_ID,
            "MyCall", NotificationManager.IMPORTANCE_DEFAULT
        )
        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
            channel
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("")
            .setContentText("").build()
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val mVibe = getSystemService(VIBRATOR_SERVICE) as Vibrator
        val pattern = longArrayOf(700, 300, 700, 100)
        mVibe.vibrate(VibrationEffect.createWaveform(pattern, 0))

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        val mVibe = getSystemService(VIBRATOR_SERVICE) as Vibrator
        mVibe.cancel()
        super.onDestroy()
    }
}