package com.madcomp19gmail.bouncyball;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Integer.parseInt;

public class BoostShop extends AppCompatActivity {

    private AdView mAdView;
    StorageManager storageManager;
    TextView coins;
    TextView gems;

    int number = 1;

    public int time;
    public int seconds;
    public int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boost_shop);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        storageManager = StorageManager.getInstance();
        coins = findViewById(R.id.coins);
        gems = findViewById(R.id.gems);

        coins.setText(storageManager.getTotalTouches() + "");
        gems.setText(storageManager.getTotalGems() + "");

        mAdView = findViewById(R.id.bannerAdBoostShop);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        initializeImageButtons(findViewById(R.id.boostShopLinearLayout));
    }

    private void initializeImageButtons(View view)
    {
        ViewGroup viewGroup = (ViewGroup) view;

        int childCount = viewGroup.getChildCount();

        for(int i = 0; i < childCount; i++)
        {
            View v = viewGroup.getChildAt(i);

            if(v instanceof ImageButton)
            {
                ImageButton imageButton = (ImageButton) v;

                int image_id = getResources().getIdentifier("boosts_" + number, "drawable", getPackageName());

                number++;

                Glide.with(this).load(image_id).into(imageButton);
            }
            else if(v instanceof ViewGroup)
                initializeImageButtons(v);
        }
    }

    public void onClickPlay(View view){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", true);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void onClickBoost(View view)
    {
        String view_id = view.getResources().getResourceEntryName(view.getId());
        view_id = view_id.split("_Label")[0].split("_Button")[0];

        String label = view_id + "_Label";
        int id = getResources().getIdentifier(label, "id", getApplicationContext().getPackageName());

        //If there is a boost already active, nothing happens
        if(Calendar.getInstance().getTimeInMillis() < storageManager.getActiveBoostTime())
        {
            Toast.makeText(this, "Wait for the active boost to end", Toast.LENGTH_SHORT).show();
            return;
        }

        int boost = parseInt(view_id.split("_")[0].replace("x", ""));
        time = parseInt(view_id.split("_")[1].replace("m", ""));
        seconds = 1;

        final TextView label_text = findViewById(id);
        if(label_text.getText() == "")
            return;
        price = Integer.parseInt(label_text.getText() + "");
        int total_gems = storageManager.getTotalGems();
        if(total_gems > price)
        {
            storageManager.takeGems(price);
            gems.setText(storageManager.getTotalGems() + "");
            storageManager.setActiveBoost(boost);
            storageManager.setActiveBoostTime(Calendar.getInstance().getTimeInMillis() + time * 60000);
            Toast.makeText(this, "Boost is now active", Toast.LENGTH_SHORT).show();
            label_text.setCompoundDrawables(null, null, null, null);

//            label_text.setText("");
//            label_text.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon_vector, 0, 0);
//            label_text.setPadding(0,10,0,0);

            Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            label_text.setText(String.valueOf(time)+" : "+String.valueOf(seconds));
                            seconds -= 1;

                            if(seconds <= 0)
                            {
                                label_text.setText(String.valueOf(time)+":"+String.valueOf(seconds));

                                seconds=59;
                                time = time -1;

                            }

                            if(time < 0)
                            {
                                label_text.setText(price + "");
                                label_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.gem_icon_small, 0);
                                label_text.setPadding(110, 0, 110, 0);
                                cancel();
                            }
                        }
                    });
                }

            }, 0, 1000);
        }
        else
            Toast.makeText(this, "You need " + (price - total_gems) + " more Gems!", Toast.LENGTH_LONG).show();
    }
}
