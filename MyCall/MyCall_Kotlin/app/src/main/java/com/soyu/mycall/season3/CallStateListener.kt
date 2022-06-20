package com.soyu.mycall.season3

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.SimPhonebookContract.SimRecords.PHONE_NUMBER
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import com.soyu.mycall.season3.UTIL.soyuPerference
import java.util.*

class CallStateListener : BroadcastReceiver() {
    private val TAG: String = com.soyu.mycall.season3.CallStateListener::class.java.getSimpleName()

    private var soyu: soyuPerference? = null
    private var intent: Intent? = null

    override fun onReceive(context: Context, received: Intent) {
        Log.e(TAG, "onReceive");
        soyu = soyuPerference(context)

        if (intent == null) {
            intent = Intent(context, VibratorService::class.java)
        }

        if (received.action == "android.intent.action.PHONE_STATE") {
            val state: String = received.getStringExtra(TelephonyManager.EXTRA_STATE)!!
            if (state == TelephonyManager.EXTRA_STATE_IDLE) {
                Log.e("!!!", "EXTRA_STATE_IDLE")
                context.stopService(intent)
            } else if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                Log.e("!!!", "EXTRA_STATE_RINGING")
                context.stopService(intent)
                var innumber = received.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                if (innumber != null) {
                    val number = soyu!!.getValue("PHONE_NUMBER", "")
                    var numbers: List<String> = ArrayList<String>()
                    numbers = Arrays.asList(*number.split(",".toRegex()).toTypedArray())
                    for (num in numbers) {
                        if (num == innumber) {
                            Toast.makeText(context, "전화가 왔습니다", Toast.LENGTH_LONG).show()
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                context.startForegroundService(intent)
                            } else {
                                context.startService(intent)
                            }
                        }
                    }
                }
            } else if (state == TelephonyManager.EXTRA_STATE_OFFHOOK) {
                Log.e("!!!", "EXTRA_STATE_OFFHOOK")
                context.stopService(intent)
            }
        }
    }
}