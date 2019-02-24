package com.madcomp19gmail.bouncyball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    private static int touches;
    private StorageManager storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        StorageManager.initialize(this);
        storage = StorageManager.getInstance();
        touches = StorageManager.getInstance().getTotalTouches();

        //SoundPoolManager.initialize(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        StorageManager.getInstance().setTotalTouches(touches);
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
        String todays_date = storage.getTodayDateString();
        String last_daily = storage.getLastDailyChallengeDateString();

        //if the player has played the daily challenge today
        if (todays_date.equals(last_daily)) {
            Toast toast = Toast.makeText(getApplicationContext(), "Come back tomorrow!", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Intent intent = new Intent(this, DailyChallenge.class);
            startActivity(intent);
        }
    }

    public void resetStorage(View view){
        storage.resetStorage();
    }

    public void startShop(View view)
    {
        Intent intent = new Intent(this, ShopMenu.class);
        startActivity(intent);
    }
}
