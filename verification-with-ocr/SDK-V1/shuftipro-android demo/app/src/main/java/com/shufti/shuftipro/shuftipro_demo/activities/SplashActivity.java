package com.shufti.shuftipro.shuftipro_demo.activities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.shufti.shuftipro.shuftipro_demo.R;


public class SplashActivity extends AppCompatActivity {
    private CountDownTimer splashTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        splashTime = new CountDownTimer(1000,500) {

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                SplashActivity.this.finish();
            }
        }.start();
    }
}
