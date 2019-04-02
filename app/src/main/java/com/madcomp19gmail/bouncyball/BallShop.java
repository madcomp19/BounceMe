package com.madcomp19gmail.bouncyball;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class BallShop extends AppCompatActivity {

    private final int background_music_id = R.raw.background_music_1;

    private StorageManager storage;
    private MediaPlayerManager mediaPlayerManager;
    TextView coins;
    TextView gems;

    SoundPoolManager soundPool;

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_shop);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setOwnedBallsPage(this.findViewById(android.R.id.content));

        storage = StorageManager.getInstance();
        mediaPlayerManager = MediaPlayerManager.getInstance();
        coins = findViewById(R.id.coins);
        gems = findViewById(R.id.gems);

        soundPool = SoundPoolManager.getInstance();
        //int id = this.getResources().getIdentifier("cash", "raw", getPackageName());
        //if(id != 0)
            //soundPool.loadSound(id);

        mAdView = findViewById(R.id.bannerAdBallShop);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        initializeImageButtons(this.findViewById(R.id.ball_shop_ConstraintLayout));
    }

    private void initializeImageButtons(View view)
    {
        ViewGroup viewGroup = (ViewGroup) view;

        int childCount = viewGroup.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View v = viewGroup.getChildAt(i);

            if (v instanceof ImageButton) {

                if(v.getId() == R.id.play_icon_vector)
                    continue;

                ImageButton imageButton = (ImageButton) v;

                String id = getResources().getResourceName(imageButton.getId()).split("/")[1].replace("_Button", "");

                int image_id = getResources().getIdentifier(id, "drawable", getPackageName());

                Glide.with(this).load(image_id).into(imageButton);
            }
            else if(v instanceof ImageView)
            {
                ImageView imageView = (ImageView) v;

                String id = getResources().getResourceName(imageView.getId()).split("/")[1];

                if(id == "gem_icon")
                    continue;

                int image_id = getResources().getIdentifier(id, "drawable", getPackageName());

                Glide.with(this).load(image_id).into(imageView);
            }
            else if (v instanceof ViewGroup)
                initializeImageButtons((ViewGroup) v);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

        coins.setText(storage.getTotalBounces() + "");
        gems.setText(storage.getTotalGems() + "");

        if(storage.getShopMusicSetting())
        {
            mediaPlayerManager.loadSound(background_music_id, "Shop");
            mediaPlayerManager.play();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(!this.isFinishing() && storage.getShopMusicSetting())
            mediaPlayerManager.pause();
    }

    public void onClickPlay(View view){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", true);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void onClickBall(View view) {

        String ball = view.getResources().getResourceEntryName(view.getId());
        ball = ball.split("_Label")[0].split("_Button")[0].substring(2);

        int label_id = this.getResources().getIdentifier("b_" + ball + "_Label", "id", getPackageName());

        buyOrSetBall(ball, label_id);
    }

    private void buyOrSetBall(String aBall, int label_id)
    {
        StorageManager storageManager = StorageManager.getInstance();

        if (storageManager.getOwnedBalls().contains(aBall)) {
            storageManager.setSelectedBall(aBall);

            ((TextView) findViewById(storageManager.getSelectedBallLabel())).setText("Owned");
            ((TextView) findViewById(storageManager.getSelectedBallLabel())).setCompoundDrawables(null,null,null,null);
            ((TextView) findViewById(storageManager.getSelectedBallLabel())).setPadding(0,0,0,0);

            storageManager.setSelectedBallLabel(label_id);

            ((TextView) findViewById(label_id)).setText("Selected");
            ((TextView) findViewById(label_id)).setText("");
            ((TextView) findViewById(label_id)).setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon_vector, 0, 0);
            ((TextView) findViewById(label_id)).setPadding(0,10,0,0);
        } else {
            TextView label_text = (TextView) findViewById(label_id);
            int price = Integer.parseInt(label_text.getText() + "");
            int total_touches = storageManager.getTotalBounces();
            int total_gems = storageManager.getTotalGems();


            /*

            DECIDE WHICH BALLS WILL COST GEMS AND UPDATE THIS

            if(aBall == id || label_id == id
                    || aBall == id || label_id == id
                    || aBall == id || label_id == id
                    || aBall == id || label_id == id
                    || aBall == id || label_id == id)
            {
                if (total_gems >= price) {
                    storageManager.takeGems(price);
                    storageManager.addOwnedBall(aBall);
                    storageManager.setSelectedBall(aBall);
                    storageManager.addOwnedBallLabel(label_id);

                    if (storageManager.getSelectedBallLabel() != 0)
                    {
                        ((TextView) findViewById(storageManager.getSelectedBallLabel())).setText("Owned");
                        ((TextView) findViewById(storageManager.getSelectedBallLabel())).setCompoundDrawables(null,null,null,null);
                        ((TextView) findViewById(storageManager.getSelectedBallLabel())).setPadding(0,0,0,0);
                    }

                    storageManager.setSelectedBallLabel(label_id);

                    label_text.setText("");
                    label_text.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon_vector, 0, 0);
                    label_text.setPadding(0,10,0,0);
                    Toast.makeText(this, "Unlocked", Toast.LENGTH_LONG).show();
                    gems.setText(storageManager.getTotalGems() + "");

                    SoundPoolManager.getInstance().playSound();
                } else
                    Toast.makeText(this, "You need " + (price - total_gems) + " more Gems", Toast.LENGTH_LONG).show();
            }

            */



            //else
            //{
                if (total_touches >= price) {
                    storageManager.setTotalBounces(total_touches - price);
                    storageManager.addOwnedBall(aBall);
                    storageManager.setSelectedBall(aBall);
                    storageManager.addOwnedBallLabel(label_id);

                    if (storageManager.getSelectedBallLabel() != 0)
                    {
                        ((TextView) findViewById(storageManager.getSelectedBallLabel())).setText("Owned");
                        ((TextView) findViewById(storageManager.getSelectedBallLabel())).setCompoundDrawables(null,null,null,null);
                        ((TextView) findViewById(storageManager.getSelectedBallLabel())).setPadding(0,0,0,0);
                    }

                    storageManager.setSelectedBallLabel(label_id);

                    label_text.setText("");
                    label_text.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon_vector, 0, 0);
                    label_text.setPadding(0,10,0,0);
                    Toast.makeText(this, "Unlocked", Toast.LENGTH_LONG).show();
                    coins.setText(storageManager.getTotalBounces() + "");

                    SoundPoolManager.getInstance().playSound();
                } else
                    Toast.makeText(this, "You need " + (price - total_touches) + " more Bounces", Toast.LENGTH_LONG).show();
            //}
        }
    }

    private void setOwnedBallsPage(View view)
    {
        StorageManager storageManager = StorageManager.getInstance();

        ArrayList<Integer> label_ids = storageManager.getOwnedBallsLabels();

        ViewGroup viewGroup = (ViewGroup) view;

        int childCount = viewGroup.getChildCount();

        for(int i = 0; i < childCount; i++)
        {
            View v = viewGroup.getChildAt(i);

            if(v instanceof TextView)
            {
                if(label_ids.contains(v.getId()))
                {
                    ((TextView) v).setText("Owned");
                    ((TextView) v).setCompoundDrawables(null,null,null,null);
                    ((TextView) v).setPadding(0,0,0,0);
                }

                if (storageManager.getSelectedBallLabel() == v.getId())
                {
                    ((TextView) v).setText("");
                    ((TextView) v).setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon_vector, 0, 0);
                    v.setPadding(0,10,0,0);
                }
            }
            else if(v instanceof ViewGroup)
                setOwnedBallsPage(v);
        }
    }
}
