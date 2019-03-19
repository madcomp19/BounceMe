package com.madcomp19gmail.bouncyball;

import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.squareup.seismic.ShakeDetector;


public class GameWorld extends AppCompatActivity implements ShakeDetector.Listener {

    static GameView gameView;
    //public static MediaPlayer mediaPlayer;
    /*SoundPool soundPool;
    HashMap<Integer, Integer> soundPoolMap;
    int soundID = 1;*/

    private static final int background_music_id = R.raw.background_music_2;

    private static StorageManager storageManager;
    private static MediaPlayerManager mediaPlayerManager;
    private static int touches;
    private static int total_bounces_ever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game_world);
        SoundPoolManager.initialize(this);
        gameView = new GameView(this);
        setContentView(gameView);


        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //mediaPlayer = MediaPlayer.create(this, R.raw.bubble);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.start(sensorManager);
        sd.setSensitivity(11);


        StorageManager.initialize(this);
        storageManager = StorageManager.getInstance();
        touches = storageManager.getTotalTouches();
        total_bounces_ever = storageManager.getTotalBouncesEver();


        if(storageManager.getGameMusicSetting() != 0)
        {
            mediaPlayerManager.pause();
            mediaPlayerManager.changeVolume((float) storageManager.getGameMusicSetting() / 10);
            mediaPlayerManager.loadSound(background_music_id, "Game");
            mediaPlayerManager.play();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(storageManager.getGameMusicSetting() != 0)
        {
            mediaPlayerManager.pause();
            mediaPlayerManager.changeVolume((float) storageManager.getGameMusicSetting() / 10);
            mediaPlayerManager.loadSound(background_music_id, "Game");
            mediaPlayerManager.play();
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        storageManager.setTotalTouches(touches);
        storageManager.setTotalBouncesEver(total_bounces_ever);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        storageManager.setTotalTouches(touches);
        storageManager.setTotalBouncesEver(total_bounces_ever);


        MainMenu.prev_act_GameWorld = true;


        if(!this.isFinishing() && storageManager.getGameMusicSetting() != 0)
        {
            //mediaPlayerManager.changeVolume(1);
            mediaPlayerManager.pause();
        }
        else
            mediaPlayerManager.changeVolume(1);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        SoundPoolManager.getInstance().release();
        storageManager.setTotalTouches(touches);
        storageManager.setTotalBouncesEver(total_bounces_ever);

        //if(storageManager.getMenuMusicSetting())
        //    mediaPlayerManager.pause();
    }

    public static void addTouch()
    {
        touches++;
        total_bounces_ever++;
    }

    public static void addDrop(Vector2 position)
    {
        if(Math.random() * 100 < 1) {
            gameView.addGem(position);
            storageManager.addGems(1);
        }
        else
            gameView.addCoin(position);
    }

    public static int getTouches()
    {
        return touches;
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

