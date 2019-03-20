package com.madcomp19gmail.bouncyball;

import android.app.Activity;
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

    SoundPoolManager soundPool;

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

        /*if(storage.getShopMusicSetting())
        {
            mediaPlayerManager.loadSound(background_music_id);
            mediaPlayerManager.play();
        }*/

        soundPool = SoundPoolManager.getInstance();
//        int id = this.getResources().getIdentifier("cash.wav", "raw", getPackageName());
//        if(id != 0)
//            soundPool.loadSound(id);

        mAdView = findViewById(R.id.bannerAdTrailShop);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onResume(){
        super.onResume();

        coins.setText(storage.getTotalTouches() + "");
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
            ((TextView) findViewById(label_id)).setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon_vector, 0, 0);
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
                    label_text.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon_vector, 0, 0);
                    label_text.setPadding(0,10,0,0);
                    Toast.makeText(this, "Unlocked", Toast.LENGTH_LONG).show();
                    gems.setText(storageManager.getTotalGems() + "");
                } else
                    Toast.makeText(this, "You need " + (price - total_gems) + " more Gems", Toast.LENGTH_LONG).show();
            }
            else
            {
                if (total_touches >= price) {
                   //soundPool.playSound();
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
                    label_text.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon_vector, 0, 0);
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
                    ((TextView) v).setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon_vector, 0, 0);
                    v.setPadding(0,10,0,0);
                }
            }
            else if(v instanceof ViewGroup)
                setOwnedSoundsPage(v);
        }
    }
}
