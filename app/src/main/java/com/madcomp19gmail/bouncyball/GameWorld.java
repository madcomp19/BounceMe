package com.madcomp19gmail.bouncyball;

import android.content.Context;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.seismic.ShakeDetector;

import java.util.Calendar;


public class GameWorld extends AppCompatActivity implements ShakeDetector.Listener {

    static GameView gameView;

    private static final int background_music_id = R.raw.background_music_2;

    private static StorageManager storageManager;
    private static MediaPlayerManager mediaPlayerManager;
    private static int touches;
    private static int total_bounces_ever;
    private static int gems;

    private static TextView coins_label;
    private static TextView gems_label;
    private static TextView timer_label;
    private static ImageView coins_image;
    private static ImageView gems_image;
    private static ImageView active_boost;

    ImageView game_background;

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_world);

        context = this;

        StorageManager.initialize(this);
        storageManager = StorageManager.getInstance();

        // Get the game background image view
        game_background = findViewById(R.id.game_background);
        gameView = findViewById(R.id.game_view);

        // Get the UI labels
        coins_label = (TextView) findViewById(R.id.coins_label);
        gems_label = (TextView) findViewById(R.id.gems_label);
        timer_label = (TextView) findViewById(R.id.timer_label);
        coins_image = (ImageView) findViewById(R.id.coin_icon);
        gems_image = (ImageView) findViewById(R.id.gem_icon);
        active_boost = (ImageView) findViewById(R.id.active_boost);

        // Set the backgrounds transparency
        coins_label.getBackground().setAlpha(150);
        gems_label.getBackground().setAlpha(150);
        timer_label.getBackground().setAlpha(150);
        active_boost.getBackground().setAlpha(150);

        // Load the coin_icon image into the view
        Glide.with(this).load(R.drawable.coin_icon).into(coins_image);

        // Bring all the UI elements to the front (get drawn after the GameView)
        coins_label.bringToFront();
        gems_label.bringToFront();
        timer_label.bringToFront();
        coins_image.bringToFront();
        gems_image.bringToFront();
        active_boost.bringToFront();

        // Load the background into that view
        int selected_background = storageManager.getSelectedBackground();
        Glide.with(this).load(selected_background).centerCrop().into(game_background);

        // Make it fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set up the shake detector
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.start(sensorManager);
        sd.setSensitivity(11);

        // Get the bounces count from storage
        touches = storageManager.getTotalBounces();
        total_bounces_ever = storageManager.getTotalBouncesEver();
        gems  = 0;

        // Set up game world music
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

        // Load the background (might be different - player might have gone to the background shop)
        int selected_background = storageManager.getSelectedBackground();
        Glide.with(this).load(selected_background).centerCrop().into(game_background);

        // Setup audio
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
        storageManager.setTotalBounces(touches);
        storageManager.setTotalBouncesEver(total_bounces_ever);
        storageManager.addGems(gems);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        storageManager.setTotalBounces(touches);
        storageManager.setTotalBouncesEver(total_bounces_ever);
        storageManager.addGems(gems);


        MainMenu.prev_act_GameWorld = true;


        if(!this.isFinishing() && storageManager.getGameMusicSetting() != 0)
            mediaPlayerManager.pause();
        else
            mediaPlayerManager.changeVolume(1);

        //SoundPoolManager.getInstance().loadSound(R.raw.cash);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        SoundPoolManager.getInstance().release();
        storageManager.setTotalBounces(touches);
        storageManager.setTotalBouncesEver(total_bounces_ever);
        storageManager.addGems(gems);
    }

    public static void updateCoinsLabel(int coins)
    {
        coins_label.setText(coins + "");
    }

    public static void updateGemsLabel(int gems)
    {
        gems_label.setAlpha(1f);
        gems_label.setText(gems + "");
        gems_image.setAlpha(1f);
    }

    public static void updateActiveBoost(int boost, String timer)
    {
        if(boost == 1)
        {
            if(active_boost.getAlpha() == 1f)
            {
                active_boost.getBackground().setAlpha(0);
                active_boost.setAlpha(0);
            }

            if(timer_label.getAlpha() == 1f)
            {
                timer_label.getBackground().setAlpha(0);
                timer_label.setAlpha(0);
            }
        }
        else
        {
            if(active_boost.getAlpha() == 0 || timer_label.getAlpha() == 0)
            {
                active_boost.setAlpha(1f);
                active_boost.getBackground().setAlpha(150);
                timer_label.setAlpha(1f);
                timer_label.getBackground().setAlpha(150);

                switch (boost) {
                    case 2:
                        Glide.with(context).load(R.drawable.boosts_2x_icon).into(active_boost);
                        timer_label.setTextColor(Color.argb(255, 255, 255, 0));
                        break;
                    case 5:
                        Glide.with(context).load(R.drawable.boosts_5x_icon).into(active_boost);
                        timer_label.setTextColor(Color.argb(255, 255, 165, 0));
                        break;
                    case 10:
                        Glide.with(context).load(R.drawable.boosts_10x_icon).into(active_boost);
                        timer_label.setTextColor(Color.argb(255, 255, 0, 0));
                        break;
                    case 50:
                        Glide.with(context).load(R.drawable.boosts_50x_icon).into(active_boost);
                        timer_label.setTextColor(Color.argb(255, 204, 0, 197));
                        break;
                    default:
                        break;
                }

                timer_label.setText(timer);
            }
            else
                timer_label.setText(timer);
        }
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
                gameView.spawnGem(position, 1);
            else
                gameView.spawnCoin(position, value);
        }
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

