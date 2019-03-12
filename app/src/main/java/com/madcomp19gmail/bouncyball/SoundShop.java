package com.madcomp19gmail.bouncyball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class SoundShop extends AppCompatActivity {

    private final int background_music_id = R.raw.background_music_1;

    private StorageManager storage;
    private MediaPlayerManager mediaPlayerManager;
    TextView coins;
    TextView gems;

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_shop);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setOwnedSoundsPage(this.findViewById(android.R.id.content));

        storage = StorageManager.getInstance();
        mediaPlayerManager = MediaPlayerManager.getInstance();
        coins = findViewById(R.id.coins);
        gems = findViewById(R.id.gems);

        if(storage.getMusicSetting())
        {
            mediaPlayerManager.loadSound(background_music_id);
            mediaPlayerManager.play();
        }

        mAdView = findViewById(R.id.bannerAdTrailShop);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onResume(){
        super.onResume();

        coins.setText(storage.getTotalTouches() + "");
        gems.setText(storage.getTotalGems() + "");

        if(storage.getMusicSetting())
        {
            mediaPlayerManager.loadSound(background_music_id);
            mediaPlayerManager.play();
        }
    }

    //region
    /*public void onClickSound(View view)
    {
        switch (view.getId()) {
            case R.id.trail_1_Button:
            case R.id.trail_1_Label:
                buyOrSetSound(R.integer.trail_1, R.id.trail_1_Label);
                break;

            case R.id.trail_2_Button:
            case R.id.trail_2_Label:
                buyOrSetSound(R.integer.trail_2, R.id.trail_2_Label);
                break;

            case R.id.trail_3_Button:
            case R.id.trail_3_Label:
                buyOrSetSound(R.integer.trail_3, R.id.trail_3_Label);
                break;

            case R.id.trail_4_Button:
            case R.id.trail_4_Label:
                buyOrSetSound(R.integer.trail_4, R.id.trail_4_Label);
                break;

            case R.id.trail_5_Button:
            case R.id.trail_5_Label:
                buyOrSetSound(R.integer.trail_5, R.id.trail_5_Label);
                break;

            case R.id.trail_6_Button:
            case R.id.trail_6_Label:
                buyOrSetSound(R.integer.trail_6, R.id.trail_6_Label);
                break;

            case R.id.trail_7_Button:
            case R.id.trail_7_Label:
                buyOrSetSound(R.integer.trail_7, R.id.trail_7_Label);
                break;

            case R.id.trail_8_Button:
            case R.id.trail_8_Label:
                buyOrSetSound(R.integer.trail_8, R.id.trail_8_Label);
                break;

            case R.id.trail_9_Button:
            case R.id.trail_9_Label:
                buyOrSetSound(R.integer.trail_9, R.id.trail_9_Label);
                break;

            case R.id.trail_10_Button:
            case R.id.trail_10_Label:
                buyOrSetSound(R.integer.trail_10, R.id.trail_10_Label);
                break;

            case R.id.trail_11_Button:
            case R.id.trail_11_Label:
                buyOrSetSound(R.integer.trail_11, R.id.trail_11_Label);
                break;

            case R.id.trail_12_Button:
            case R.id.trail_12_Label:
                buyOrSetSound(R.integer.trail_12, R.id.trail_12_Label);
                break;

            case R.id.trail_13_Button:
            case R.id.trail_13_Label:
                buyOrSetSound(R.integer.trail_13, R.id.trail_13_Label);
                break;

            case R.id.trail_14_Button:
            case R.id.trail_14_Label:
                buyOrSetSound(R.integer.trail_14, R.id.trail_14_Label);
                break;

            case R.id.trail_15_Button:
            case R.id.trail_15_Label:
                buyOrSetSound(R.integer.trail_15, R.id.trail_15_Label);
                break;

            case R.id.trail_16_Button:
            case R.id.trail_16_Label:
                buyOrSetSound(R.integer.trail_16, R.id.trail_16_Label);
                break;

            case R.id.trail_17_Button:
            case R.id.trail_17_Label:
                buyOrSetSound(R.integer.trail_17, R.id.trail_17_Label);
                break;

            case R.id.trail_18_Button:
            case R.id.trail_18_Label:
                buyOrSetSound(R.integer.trail_18, R.id.trail_18_Label);
                break;

            case R.id.trail_19_Button:
            case R.id.trail_19_Label:
                buyOrSetSound(R.integer.trail_19, R.id.trail_19_Label);
                break;

            case R.id.trail_20_Button:
            case R.id.trail_20_Label:
                buyOrSetSound(R.integer.trail_20, R.id.trail_20_Label);
                break;

            case R.id.trail_21_Button:
            case R.id.trail_21_Label:
                buyOrSetSound(R.integer.trail_21, R.id.trail_21_Label);
                break;

            case R.id.trail_22_Button:
            case R.id.trail_22_Label:
                buyOrSetSound(R.integer.trail_22, R.id.trail_22_Label);
                break;

            case R.id.trail_23_Button:
            case R.id.trail_23_Label:
                buyOrSetSound(R.integer.trail_23, R.id.trail_23_Label);
                break;

            case R.id.trail_24_Button:
            case R.id.trail_24_Label:
                buyOrSetSound(R.integer.trail_24, R.id.trail_24_Label);
                break;

            case R.id.trail_25_Button:
            case R.id.trail_25_Label:
                buyOrSetSound(R.integer.trail_25, R.id.trail_25_Label);
                break;
        }
    }*/
    //endregion

    public void onClickHome(View view){
        Intent intent = new Intent(this, GameWorld.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void onClickSound(View view) {

        String view_id = view.getResources().getResourceEntryName(view.getId());
        view_id = view_id.split("_Label")[0].split("_Button")[0];
        //Log.i("TEST", view_id);

        int sound_id;

        if(view_id == "mute")
            sound_id = 0;
        else
            sound_id = this.getResources().getIdentifier(view_id, "raw", getPackageName());


        int label_id = this.getResources().getIdentifier(view_id + "_Label", "id", getPackageName());
        //region
        /*Log.i("skin", view_id);
        Log.i("label", view_id + "_Label");
        Log.i("skin_id", Integer.toString(skin_id));
        Log.i("label_id", Integer.toString(label_id));
        Log.i("res_id", Integer.toString(view.getId()));*/
        //endregion

        buyOrSetSound(sound_id, label_id);
    }

    private void buyOrSetSound(int aSound, int label_id)
    {
        //int sound = getResources().getInteger(aSound);
        int sound = aSound;

        StorageManager storageManager = StorageManager.getInstance();

        if (storageManager.getOwnedSounds().contains(sound)) {
            storageManager.setSelectedSound(sound);

            ((TextView) findViewById(storageManager.getSelectedSoundLabel())).setText("Owned");
            ((TextView) findViewById(storageManager.getSelectedSoundLabel())).setCompoundDrawables(null,null,null,null);
            ((TextView) findViewById(storageManager.getSelectedSoundLabel())).setPadding(0,0,0,0);

            storageManager.setSelectedSoundLabel(label_id);

            ((TextView) findViewById(label_id)).setText("Selected");
            ((TextView) findViewById(label_id)).setText("");
            ((TextView) findViewById(label_id)).setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon, 0, 0);
            ((TextView) findViewById(label_id)).setPadding(0,10,0,0);
        } else {
            TextView label_text = (TextView) findViewById(label_id);
            int price = Integer.parseInt(label_text.getText() + "");
            int total_touches = storageManager.getTotalTouches();
            int total_gems = storageManager.getTotalGems();

            if(aSound == R.id.laser_Button || label_id == R.id.laser_Label
                    || aSound == R.id.pew_Button || label_id == R.id.pew_Label
                    || aSound == R.id.ricochet_Button || label_id == R.id.ricochet_Label
                    || aSound == R.id.gun_Button || label_id == R.id.gun_Label
                    || aSound == R.id.trigger_Button || label_id == R.id.trigger_Label)
            {
                if (total_gems >= price) {
                    storageManager.takeGems(price);
                    storageManager.addOwnedSound(sound);
                    storageManager.setSelectedSound(sound);
                    storageManager.addOwnedSoundLabel(label_id);

                    if (storageManager.getSelectedSoundLabel() != 0)
                    {
                        ((TextView) findViewById(storageManager.getSelectedSoundLabel())).setText("Owned");
                        ((TextView) findViewById(storageManager.getSelectedSoundLabel())).setCompoundDrawables(null,null,null,null);
                        ((TextView) findViewById(storageManager.getSelectedSoundLabel())).setPadding(0,0,0,0);
                    }

                    storageManager.setSelectedSoundLabel(label_id);

                    label_text.setText("");
                    label_text.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon, 0, 0);
                    label_text.setPadding(0,10,0,0);
                    Toast.makeText(this, "Unlocked", Toast.LENGTH_LONG).show();
                    gems.setText(storageManager.getTotalGems() + "");
                } else
                    Toast.makeText(this, "You need " + (price - total_gems) + " more Gems", Toast.LENGTH_LONG).show();
            }
            else
            {
                if (total_touches >= price) {
                    storageManager.setTotalTouches(total_touches - price);
                    storageManager.addOwnedSound(sound);
                    storageManager.setSelectedSound(sound);
                    storageManager.addOwnedSoundLabel(label_id);

                    if (storageManager.getSelectedSoundLabel() != 0)
                    {
                        ((TextView) findViewById(storageManager.getSelectedSoundLabel())).setText("Owned");
                        ((TextView) findViewById(storageManager.getSelectedSoundLabel())).setCompoundDrawables(null,null,null,null);
                        ((TextView) findViewById(storageManager.getSelectedSoundLabel())).setPadding(0,0,0,0);
                    }

                    storageManager.setSelectedSoundLabel(label_id);

                    label_text.setText("");
                    label_text.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon, 0, 0);
                    label_text.setPadding(0,10,0,0);
                    Toast.makeText(this, "Unlocked", Toast.LENGTH_LONG).show();
                    coins.setText(storageManager.getTotalTouches() + "");
                } else
                    Toast.makeText(this, "You need " + (price - total_touches) + " more Bounces", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setOwnedSoundsPage(View view)
    {
        StorageManager storageManager = StorageManager.getInstance();

        ArrayList<Integer> label_ids = storageManager.getOwnedSoundsLabels();

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

                if (storageManager.getSelectedSoundLabel() == v.getId())
                {
                    ((TextView) v).setText("");
                    ((TextView) v).setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon, 0, 0);
                    v.setPadding(0,10,0,0);
                }
            }
            else if(v instanceof ViewGroup)
                setOwnedSoundsPage(v);
        }
    }
}
