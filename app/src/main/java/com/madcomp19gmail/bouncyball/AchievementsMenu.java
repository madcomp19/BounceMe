package com.madcomp19gmail.bouncyball;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AchievementsMenu extends AppCompatActivity {

    ProgressBar bar1;
    ProgressBar bar2;
    ProgressBar bar3;

    TextView text1;
    TextView text2;
    TextView text3;

    private StorageManager storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements_menu);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        bar1 = findViewById(R.id.circularProgressBar);
        bar2 = findViewById(R.id.circularProgressBar2);
        bar3 = findViewById(R.id.circularProgressBar3);

        text1 = findViewById(R.id.textView1);
        text2 = findViewById(R.id.textView2);
        text3 = findViewById(R.id.textView3);

        storage = StorageManager.getInstance();

        updateProgressBars();
    }

    public void updateProgressBars()
    {
        bar1.setProgress(storage.getTotalTouches());
        bar2.setProgress(storage.getTotalTouches());
        bar3.setProgress(storage.getTotalTouches());

        text1.setText(bar1.getProgress() + "/" + bar1.getMax() + " Bounces");
        text2.setText(bar2.getProgress() + "/" + bar2.getMax() + " Bounces");
        text3.setText(bar3.getProgress() + "/" + bar3.getMax() + " Bounces");
    }
}
