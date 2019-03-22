package com.madcomp19gmail.bouncyball;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.gms.ads.MobileAds;

public class SplashScreen extends AppCompatActivity {

    private CountDownTimer timer;
    private View view;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        view = new View(this);

        timer = new CountDownTimer(500, 1000) {

            public void onTick(long millisUntilFinished) {
                //empty
            }

            public void onFinish() {
                skip(view);
            }
        }.start();
    }

    public void skip(View view) {
        timer.cancel();
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
