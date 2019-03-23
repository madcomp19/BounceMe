package com.madcomp19gmail.bouncyball;

import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.squareup.seismic.ShakeDetector;

import java.util.Calendar;


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
    private static int gems;

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
        gems  = storageManager.getTotalGems();


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
        storageManager.setTotalGems(gems);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        storageManager.setTotalTouches(touches);
        storageManager.setTotalBouncesEver(total_bounces_ever);
        storageManager.setTotalGems(gems);


        MainMenu.prev_act_GameWorld = true;


        if(!this.isFinishing() && storageManager.getGameMusicSetting() != 0)
            mediaPlayerManager.pause();
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
        storageManager.setTotalGems(gems);
    }

    public static void addTouches(int t)
    {
        touches += t;
        total_bounces_ever++;
    }

    public static void addGems(int g)
    {
        gems += g;
    }

    public static void addDrop(Vector2 position)
    {
        int boost;
        int value = 1;

        if(Calendar.getInstance().getTimeInMillis() < storageManager.getActiveBoostTime())
            boost = storageManager.getActiveBoost();
        else
        {
            boost = 1;
            storageManager.setActiveBoost(boost);
        }

        switch (boost)
        {
            case 1: boost = 1; value = 1; break;
            case 2: boost = 1; value = 2; break;
            case 5: boost = 1; value = 5; break;
            case 10: boost = 2; value = 5; break;
            case 50: boost = 2; value = 25; break;
            default: break;
        }

        for(int i = 0; i < boost; i++)
        {
            if(Math.random() * 100 < 1)
                gameView.spawnGem(position, value);
            else
                gameView.spawnCoin(position, value);
        }
//        if(Math.random() * 100 < 1)
//            gameView.addGems(position);
//        else
//            gameView.spawnCoin(position);
    }

    public static int getTouches()
    {
        return touches;
    }

    public static int getGems()
    {
        return gems;
    }

    @Override public void hearShake()
    {
        gameView.shake();
    }
}

