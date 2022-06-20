package com.soyu.mycall.season3

import android.Manifest
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setContentView(R.layout.activity_intro)

        permissionCheck()
    }
    private fun permissionCheck(){
        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE )
        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE )
        }
    }

    private val splashDuration = 1500L
    private fun initalize() {
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, splashDuration)
    }
    val PERMISSIONS_REQUEST_CODE = 1001
    var REQUIRED_PERMISSIONS = arrayOf<String>( Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_PHONE_STATE, Manifest.permission.VIBRATE )

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                if ( grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                    initalize()
                } else if ( grantResults.size ==0 ) {
                } else {
                    Toast.makeText(this@IntroActivity, "권한이 없으면 앱을 실행 할 수 없습니다.", Toast.LENGTH_SHORT).show()

                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.parse("package:$packageName")
                    startActivityForResult(intent, PERMISSIONS_REQUEST_CODE)

                }
            }
        }
    }

    override fun onBackPressed() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> if (checkSelfPermission(
                    Manifest.permission.READ_PHONE_STATE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(this@IntroActivity, "권한이 없으면 앱을 실행 할 수 없습니다.", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                initalize()
            }
        }

    }
}