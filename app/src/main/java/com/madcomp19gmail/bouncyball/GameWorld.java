package com.madcomp19gmail.bouncyball;

import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import com.squareup.seismic.ShakeDetector;


public class GameWorld extends AppCompatActivity implements ShakeDetector.Listener {

    GameView gameView;
    //public static MediaPlayer mediaPlayer;
    /*SoundPool soundPool;
    HashMap<Integer, Integer> soundPoolMap;
    int soundID = 1;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game_world);

        gameView = new GameView(this);
        setContentView(gameView);


        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //mediaPlayer = MediaPlayer.create(this, R.raw.bubble);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.start(sensorManager);
        sd.setSensitivity(11);

        SoundPoolManager.initialize(this);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        SoundPoolManager.getInstance().release();
    }

    @Override public void hearShake()
    {
        gameView.shake();
    }

    /*public void playSound()
    {
        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float leftVolume = curVolume/maxVolume;
        float rightVolume = curVolume/maxVolume;
        int priority = 1;
        int no_loop = 0;
        float normal_playback_rate = 1f;
        soundPool.play(soundID, leftVolume, rightVolume, priority, no_loop, normal_playback_rate);
    }*/
}

