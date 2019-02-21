package com.madcomp19gmail.bouncyball;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Calendar;

public class MainMenu extends AppCompatActivity {

    private static int touches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        touches = 0;
    }

    public static void addTouch() {
        touches++;
    }

    public static int getTouches() {
        return touches;
    }

    public void playGame(View view) {
        Intent intent = new Intent(this, GameWorld.class);
        startActivity(intent);
    }

    public void startChallengeActivity(View view) {
        Calendar date = Calendar.getInstance();
        String year = Integer.toString(date.get(Calendar.YEAR));
        String month = Integer.toString(date.get(Calendar.MONTH));
        String day = Integer.toString(date.get(Calendar.DAY_OF_MONTH));
        String todays_date = day + month + year;

        SharedPreferences prefs = getSharedPreferences(getString(R.string.shared_prefs_filename), MODE_PRIVATE);
        String last_daily_date_string = prefs.getString(getString(R.string.last_daily_challenge_date), "");

        //if the player has played the daily challenge today
        if (todays_date.equals(last_daily_date_string)) {
            Toast toast = Toast.makeText(getApplicationContext(), "Come back tomorrow!", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Intent intent = new Intent(this, DailyChallenge.class);
            startActivity(intent);
        }
    }

    public void resetStorage(View view){
        getSharedPreferences(getString(R.string.shared_prefs_filename), MODE_PRIVATE).edit().clear().apply();
    }
}
