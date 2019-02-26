package com.madcomp19gmail.bouncyball;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    //private static int touches;
    private StorageManager storage;

    public static boolean prev_act_GameWorld = false;
    private static int prev_touches;
    TextView coins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        StorageManager.initialize(this);
        storage = StorageManager.getInstance();
        coins = findViewById(R.id.coins);
        //touches = StorageManager.getInstance().getTotalTouches();*/

        //SoundPoolManager.initialize(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        //StorageManager.getInstance().setTotalTouches(touches);

        if(prev_act_GameWorld)
        {
            int new_touches = Math.abs(StorageManager.getInstance().getTotalTouches() - prev_touches);

            if(new_touches > 300)
            {
                Intent intent = new Intent(this, DoublePointsAlert.class);
                startActivity(intent);
            }

            prev_act_GameWorld = false;
        }

        coins.setText(storage.getTotalTouches() + "");
    }

    public static int getPrevTouches()
    {
        return prev_touches;
    }

    /*public static void addTouch() {
        touches++;
        storage.setTotalTouches(touches);
    }

    public static int getTouches() {
        return touches;
    }*/

    public void playGame(View view) {
        Intent intent = new Intent(this, GameWorld.class);
        startActivity(intent);
        prev_touches = StorageManager.getInstance().getTotalTouches();
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

    public void goFacebookPage(View view)
    {
        Intent goFacebook = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/francisco.teixeira.507"));
        startActivity(goFacebook);
    }
}
