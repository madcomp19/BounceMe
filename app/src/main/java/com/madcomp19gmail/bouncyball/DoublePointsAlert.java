package com.madcomp19gmail.bouncyball;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class DoublePointsAlert extends AppCompatActivity {

    TextView numberBouncesEarned;
    int bouncesEarned;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_points_alert);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.8f), (int) (height * 0.4f));

        bouncesEarned = GameWorld.getTouches() - MainMenu.getPrevTouches();

        numberBouncesEarned = findViewById(R.id.numberBounces);
        numberBouncesEarned.setText(bouncesEarned + " Bounces");
    }
}
