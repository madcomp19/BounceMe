package com.madcomp19gmail.bouncyball;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Calendar;

public class DailyChallenge extends AppCompatActivity {

    private ImageView day_counter;
    private int id;
    private int consecutive_days;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_challenge);

        prefs = getSharedPreferences(getString(R.string.shared_prefs_filename), MODE_PRIVATE);
        consecutive_days = prefs.getInt(getString(R.string.consecutive_days_daily_challenge), 0);
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

        Calendar date = Calendar.getInstance();
        String year = Integer.toString(date.get(Calendar.YEAR));
        String month = Integer.toString(date.get(Calendar.MONTH));
        String day = Integer.toString(date.get(Calendar.DAY_OF_MONTH));
        String todays_date = day + month + year;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.last_daily_challenge_date), todays_date);

        if (consecutive_days == 7) {
            editor.putInt(getString(R.string.consecutive_days_daily_challenge), 0);
            editor.apply();
            showChallengeCompleteDialog();
        }
        else{
            editor.putInt(getString(R.string.consecutive_days_daily_challenge), consecutive_days);
            editor.apply();
            showSuccessDialog();
        }
    }


    public void onClickWrongAnswer(View view) {
        //update the number of consecutive days in shared prefs
        consecutive_days = 0;
        clearStars();

        Calendar date = Calendar.getInstance();
        String year = Integer.toString(date.get(Calendar.YEAR));
        String month = Integer.toString(date.get(Calendar.MONTH));
        String day = Integer.toString(date.get(Calendar.DAY_OF_MONTH));
        String todays_date = day + month + year;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.last_daily_challenge_date), todays_date);
        editor.putInt(getString(R.string.consecutive_days_daily_challenge), 0);
        editor.apply();

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
