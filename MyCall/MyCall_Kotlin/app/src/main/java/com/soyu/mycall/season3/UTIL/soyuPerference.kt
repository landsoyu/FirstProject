package com.soyu.mycall.season3.UTIL

import android.content.Context
import android.content.SharedPreferences

const val PREF_NAME = "com.soyu.mycall.season3"
const val PHONE_NUMBER = "PHONE_NUMBER"

class soyuPerference(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getValue(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun put(key: String, str: String) {
        prefs.edit().putString(key, str).commit()
    }

}