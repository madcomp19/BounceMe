package com.madcomp19gmail.bouncyball;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class DailyChallenge extends AppCompatActivity {

    private ImageView day_counter;
    private int id;
    private int consecutive_days;
    private StorageManager prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_challenge);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        prefs = StorageManager.getInstance();
        consecutive_days = prefs.getConsecutiveDays();
        setStarImages(consecutive_days);
    }

    //sets the stars to the number of consecutive days
    private void setStarImages(int limit) {
        if (limit > 7 || limit < 1) return;
        for (int i = 1; i <= limit; i++) {
            //change the corresponding images
            id = getResources().getIdentifier("consecutive_day_" + i, "id", getPackageName());
            day_counter = findViewById(id);
            day_counter.setImageResource(R.drawable.btn_rating_star_off_pressed);
        }
    }

    public void onClickRightAnswer(View view) {
        //update the number of consecutive days in shared prefs
        setStarImages(++consecutive_days);

        String todays_date = prefs.getTodayDateString();
        prefs.setLastDailyChallengeDate(todays_date);

        if (consecutive_days == 7) {
            prefs.setConsecutiveDays(0);
            showChallengeCompleteDialog();
        }
        else{
            prefs.setConsecutiveDays(consecutive_days);
            showSuccessDialog();
        }
    }


    public void onClickWrongAnswer(View view) {
        //update the number of consecutive days in shared prefs
        consecutive_days = 0;
        clearStars();

        String todays_date = prefs.getTodayDateString();

        prefs.setLastDailyChallengeDate(todays_date);
        prefs.setConsecutiveDays(0);

        showErrorDialog();
    }

    private void clearStars(){
        for (int i = 1; i <= 7; i++) {
            //change the corresponding images
            id = getResources().getIdentifier("consecutive_day_" + i, "id", getPackageName());
            day_counter = findViewById(id);
            day_counter.setImageResource(R.drawable.btn_rating_star_off_normal);
        }
    }

    private void showChallengeCompleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DailyChallenge.this);
        builder.setMessage(R.string.challenge_complete_dialog_message)
                .setTitle(R.string.challenge_complete_dialog_title);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DailyChallenge.this);
        builder.setMessage(R.string.success_dialog_message)
                .setTitle(R.string.success_dialog_title);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DailyChallenge.this);
        builder.setMessage(R.string.error_dialog_message)
                .setTitle(R.string.error_dialog_title);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
