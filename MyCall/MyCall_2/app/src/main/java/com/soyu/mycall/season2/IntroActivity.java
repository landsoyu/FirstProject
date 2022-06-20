package com.soyu.mycall.season2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.soyu.mycall.season2.UTIL.UIUtil;

public class IntroActivity extends AppCompatActivity {
    //  Static
    private static final String TAG = IntroActivity.class.getSimpleName();

    //  UI
    private Context context;

    //  Data
    private int width, height;

    /** 폰 상태 요청 SEQ */
    public static final int REQUEST_PHONE_STATE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.context = this;
        this.width = UIUtil.deviceWidth(context);
        this.height = UIUtil.deviceHeight(context);

        FrameLayout mainPanel = new FrameLayout(context);
        mainPanel.setBackgroundColor(Color.WHITE);
        UIUtil.setLayout(mainPanel, true, true);
        {
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.intro);
            UIUtil.frame(imageView, 0, 0, width, height);
            ( (FrameLayout.LayoutParams)imageView.getLayoutParams() ).gravity = Gravity.CENTER;
            mainPanel.addView(imageView);

            AlphaAnimation animation = new AlphaAnimation(0.0f,  1.0f);
            animation.setDuration(2000);
            imageView.setAnimation(animation);
        }
        setContentView(mainPanel);

        permissionCheck();
    }

    private void permissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_PHONE_STATE, Manifest.permission.VIBRATE}, REQUEST_PHONE_STATE);
            if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_PHONE_STATE, Manifest.permission.VIBRATE}, REQUEST_PHONE_STATE);
            }

        }
    }

    private void initalize() {
        Log.e(TAG, "[initalize] ");
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                finish();

                startActivity(new Intent(context, mycallActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        };

        handler.sendEmptyMessageDelayed(0, 2700);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_STATE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initalize();
                } else if (grantResults.length == 0) {
                } else {
                    Toast.makeText(context, "권한이 없으면 앱을 실행 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, REQUEST_PHONE_STATE);
                }
                break;
        }
    }
    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e(TAG, "[onActivityResult] requestCode : "+requestCode);
        Log.e(TAG, "[onActivityResult] resultCode : "+resultCode);

        switch (requestCode) {
            case REQUEST_PHONE_STATE:
                if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(context, "권한이 없으면 앱을 실행 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    initalize();
                }
                break;
        }
    }
}
