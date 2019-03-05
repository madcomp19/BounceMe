package com.madcomp19gmail.bouncyball;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsMenu extends AppCompatActivity {

    private final int background_music_id = R.raw.background_music_2;

    private StorageManager storageManager;
    private MediaPlayerManager mediaPlayerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settins_menu);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        storageManager = StorageManager.getInstance();
        mediaPlayerManager = MediaPlayerManager.getInstance();

        Switch mySwitch = findViewById(R.id.musicSwitch);
        mySwitch.setChecked(StorageManager.getInstance().getMusicSetting());

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                storageManager.setMusicSetting(isChecked);

                if(isChecked)
                    MediaPlayerManager.getInstance().play();
                else
                    MediaPlayerManager.getInstance().pause();
            }
        });

        if(storageManager.getMusicSetting())
        {
            mediaPlayerManager.loadSound(background_music_id);
            mediaPlayerManager.play();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        //StorageManager.getInstance().setTotalTouches(touches);
        if(storageManager.getMusicSetting())
        {
            mediaPlayerManager.loadSound(background_music_id);
            mediaPlayerManager.play();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(storageManager.getMusicSetting())
            mediaPlayerManager.pause();
    }
}
