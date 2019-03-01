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
    ProgressBar bar4;
    ProgressBar bar5;
    ProgressBar bar6;
    ProgressBar bar7;
    ProgressBar bar8;
    ProgressBar bar9;
    ProgressBar bar10;
    ProgressBar bar11;
    ProgressBar bar12;
    ProgressBar bar13;
    ProgressBar bar14;
    ProgressBar bar15;

    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    TextView text5;
    TextView text6;
    TextView text7;
    TextView text8;
    TextView text9;
    TextView text10;
    TextView text11;
    TextView text12;
    TextView text13;
    TextView text14;
    TextView text15;

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
        bar4 = findViewById(R.id.circularProgressBar4);
        bar5 = findViewById(R.id.circularProgressBar5);
        bar6 = findViewById(R.id.circularProgressBar6);
        bar7 = findViewById(R.id.circularProgressBar7);
        bar8 = findViewById(R.id.circularProgressBar8);
        bar9 = findViewById(R.id.circularProgressBar9);
        bar10 = findViewById(R.id.circularProgressBar10);
        bar11 = findViewById(R.id.circularProgressBar11);
        bar12 = findViewById(R.id.circularProgressBar12);
        bar13 = findViewById(R.id.circularProgressBar13);
        bar14 = findViewById(R.id.circularProgressBar14);
        bar15 = findViewById(R.id.circularProgressBar15);

        text1 = findViewById(R.id.textView1);
        text2 = findViewById(R.id.textView2);
        text3 = findViewById(R.id.textView3);
        text4 = findViewById(R.id.textView4);
        text5 = findViewById(R.id.textView5);
        text6 = findViewById(R.id.textView6);
        text7 = findViewById(R.id.textView7);
        text8 = findViewById(R.id.textView8);
        text9 = findViewById(R.id.textView9);
        text10 = findViewById(R.id.textView10);
        text11 = findViewById(R.id.textView11);
        text12 = findViewById(R.id.textView12);
        text13 = findViewById(R.id.textView13);
        text14 = findViewById(R.id.textView14);
        text15 = findViewById(R.id.textView15);

        storage = StorageManager.getInstance();

        updateProgressBars();
    }

    public void updateProgressBars()
    {
        bar1.setProgress(storage.getTotalBouncesEver());
        bar2.setProgress(storage.getTotalBouncesEver());
        bar3.setProgress(storage.getTotalBouncesEver());
        bar4.setProgress(storage.getTotalBouncesEver());
        bar5.setProgress(storage.getTotalBouncesEver());
        bar6.setProgress(storage.getTotalBouncesEver());
        bar7.setProgress(storage.getTotalBouncesEver());
        bar8.setProgress(storage.getTotalBouncesEver());
        bar9.setProgress(storage.getTotalBouncesEver());

        text1.setText(bar1.getProgress() + "/" + bar1.getMax() + " Bounces");
        text2.setText(bar2.getProgress() + "/" + bar2.getMax() + " Bounces");
        text3.setText(bar3.getProgress() + "/" + bar3.getMax() + " Bounces");
        text4.setText(bar4.getProgress() + "/" + bar4.getMax() + " Bounces");
        text5.setText(bar5.getProgress() + "/" + bar5.getMax() + " Bounces");
        text6.setText(bar6.getProgress() + "/" + bar6.getMax() + " Bounces");
        text7.setText(bar7.getProgress() + "/" + bar7.getMax() + " Bounces");
        text8.setText(bar8.getProgress() + "/" + bar8.getMax() + " Bounces");
        text9.setText(bar9.getProgress() + "/" + bar9.getMax() + " Bounces");
    }
}
