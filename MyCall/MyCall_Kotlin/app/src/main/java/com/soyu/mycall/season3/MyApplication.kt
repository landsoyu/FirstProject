package com.soyu.mycall.season3

import android.app.Application
import com.soyu.mycall.season3.UTIL.soyuPerference

class MyApplication : Application() {
    companion object {
        lateinit var prefs: soyuPerference
    }

    override fun onCreate() {
        prefs = soyuPerference(applicationContext)
        super.onCreate()
    }


}