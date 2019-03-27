package com.madcomp19gmail.bouncyball;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsMenu extends AppCompatActivity {

    private final int background_music_id = R.raw.background_music_2;

    private StorageManager storageManager;
    private MediaPlayerManager mediaPlayerManager;

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
}
