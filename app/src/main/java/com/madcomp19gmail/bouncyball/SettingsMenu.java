package com.madcomp19gmail.bouncyball;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SettingsMenu extends AppCompatActivity {

    private final int background_music_id = R.raw.background_music_2;

    private StorageManager storageManager;
    private MediaPlayerManager mediaPlayerManager;

    private AdView mAdView;

    private final String email_address = "madcomp19@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        storageManager = StorageManager.getInstance();
        mediaPlayerManager = MediaPlayerManager.getInstance();

        Switch menuMusicSwitch = findViewById(R.id.menuMusicSwitch);
        Switch shopMusicSwitch = findViewById(R.id.shopMusicSwitch);
        SeekBar gameVolumeBar = findViewById(R.id.gameVolumeBar);
        menuMusicSwitch.setChecked(storageManager.getMenuMusicSetting());
        shopMusicSwitch.setChecked(storageManager.getShopMusicSetting());
        gameVolumeBar.setProgress(storageManager.getGameMusicSetting());

        menuMusicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                storageManager.setMenuMusicSetting(isChecked);

                if(isChecked) {
                    mediaPlayerManager.loadSound(background_music_id, "Menu");
                    mediaPlayerManager.play();
                }
                else
                    mediaPlayerManager.pause();
            }
        });

        shopMusicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                storageManager.setShopMusicSetting(isChecked);
            }
        });

        gameVolumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub

                storageManager.setGameMusicSetting(progress);
            }
        });

        if(storageManager.getMenuMusicSetting())
        {
            mediaPlayerManager.loadSound(background_music_id, "Menu");
            mediaPlayerManager.play();
        }

        if(!storageManager.getNoAds()) {
            mAdView = findViewById(R.id.bannerAdSettings);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        //StorageManager.getInstance().setTotalBounces(touches);
        if(storageManager.getMenuMusicSetting())
        {
            mediaPlayerManager.loadSound(background_music_id, "Menu");
            mediaPlayerManager.play();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(!this.isFinishing() && storageManager.getMenuMusicSetting())
            mediaPlayerManager.pause();
    }

    public void goToFacebook(View view) {
        //changingActivity = true;
        Intent goFacebook = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/francisco.teixeira.507"));
        startActivity(goFacebook);
    }

    public void sendFeedback(View view)
    {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("message/rfc822");
        intent.setData(Uri.parse("mailto:" + email_address));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Bounce It Up - Feedback");
        intent.putExtra(Intent.EXTRA_TEXT   , "");
        try
        {
            startActivity(Intent.createChooser(intent, "Choose an app"));
        }
        catch (android.content.ActivityNotFoundException ex)
        {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
