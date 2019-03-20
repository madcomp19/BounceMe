package com.madcomp19gmail.bouncyball;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainMenu extends AppCompatActivity implements RewardedVideoAdListener {

    //private static int touches;
    private StorageManager storage;
    private MediaPlayerManager mediaPlayerManager;

    private final int background_music_id = R.raw.background_music_2;

    private boolean changingActivity;

    public static boolean prev_act_GameWorld = false;
    private static int prev_touches;
    TextView coins;
    TextView gems;
    TextView adsLeftToday;
    Button adButton;
    private RewardedVideoAd mRewardedVideoAd;
    boolean rewarded = false;
    boolean adsLeft = true;

    private final int default_skin = R.drawable.emoji_0;
    private final int default_skin_label = R.id.emoji_0_Label;
    private final int default_sound = 0;
    private final int default_sound_label = R.id.mute_Label;
    private final int default_trail = -1;
    private final int default_trail_label = R.id.trail_25_Label;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        MobileAds.initialize(this, "ca-app-pub-5557351606937995~8015272303");

        StorageManager.initialize(this);
        storage = StorageManager.getInstance();
        coins = findViewById(R.id.coins);
        gems = findViewById(R.id.gems);
        adButton = findViewById(R.id.adButton);
        adsLeftToday = findViewById(R.id.adsLeftToday);
        //touches = StorageManager.getInstance().getTotalTouches();*/

        /*mediaPlayer = MediaPlayer.create(this, R.raw.background_music_2);
        mediaPlayer.setLooping(true);

        if(storage.getMenuMusicSetting())
            mediaPlayer.start();*/

        changingActivity = false;

        MediaPlayerManager.initialize(this, background_music_id);
        mediaPlayerManager = MediaPlayerManager.getInstance();

        if (storage.getMenuMusicSetting()) {
            mediaPlayerManager.loadSound(background_music_id, "Menu");
            mediaPlayerManager.play();
        }
        else
            mediaPlayerManager.pause();

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        if (storage.getAdsAvailableToday() > 0)
            adsLeftToday.setText(storage.getAdsAvailableToday() + " Left Today");
        else {
            adsLeftToday.setText("Come Back Tomorrow");
            adsLeft = false;
            adButton.setEnabled(false);
        }

        if (adsLeft)
            loadRewardedVideoAd();


        initializeShops();
    }

    private void initializeShops() {
        if (storage.getSelectedSkin() == 0) {
            storage.addOwnedSkin(default_skin);
            storage.addOwnedSkinLabel(default_skin_label);
            storage.setSelectedSkin(default_skin);
            storage.setSelectedSkinLabel(default_skin_label);
        }

        if (storage.getSelectedSound() == 0) {
            storage.addOwnedSound(default_sound);
            storage.addOwnedSoundLabel(default_sound_label);
            storage.setSelectedSound(default_sound);
            storage.setSelectedSoundLabel(default_sound_label);
        }

        if (storage.getSelectedTrail() == -10) {
            storage.addOwnedTrail(default_trail);
            storage.addOwnedTrailLabel(default_trail_label);
            storage.setSelectedTrail(default_trail);
            storage.setSelectedTrailLabel(default_trail_label);
        }
    }

    private void loadRewardedVideoAd() {
        //LEMBRAR MUDAR O ID!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());

        adButton.setEnabled(false);
    }

    public void watchAdMainMenu(View view) {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //StorageManager.getInstance().setTotalTouches(touches);

        if (prev_act_GameWorld) {
            int new_touches = Math.abs(StorageManager.getInstance().getTotalTouches() - prev_touches);

            if (new_touches > 300) {
                changingActivity = true;
                Intent intent = new Intent(this, DoublePointsAlert.class);
                startActivity(intent);
            }

            prev_act_GameWorld = false;
        }

        coins.setText(storage.getTotalTouches() + "");
        gems.setText(storage.getTotalGems() + "");

        if (storage.getAdsAvailableToday() > 0)
            adsLeftToday.setText(storage.getAdsAvailableToday() + " Left Today");
        else {
            adsLeftToday.setText("Come Back Tomorrow");
            adsLeft = false;
            adButton.setEnabled(false);
        }

        if (storage.getMenuMusicSetting()) {
            mediaPlayerManager.pause();
            mediaPlayerManager.loadSound(background_music_id, "Menu");
            mediaPlayerManager.play();
        }
        else
            mediaPlayerManager.pause();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (!changingActivity && storage.getMenuMusicSetting())
            mediaPlayerManager.pause();

        changingActivity = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //if(storage.getMenuMusicSetting())
        //mediaPlayerManager.pause();
    }

    public static int getPrevTouches() {
        return prev_touches;
    }

    /*public static void addTouch() {
        touches++;
        storage.setTotalTouches(touches);
    }

    public static int getTouches() {
        return touches;
    }*/

    public void playGame(View view) {
        changingActivity = true;
        Intent intent = new Intent(this, GameWorld.class);
        startActivity(intent);
        prev_touches = StorageManager.getInstance().getTotalTouches();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                boolean result = data.getBooleanExtra("result", false);

                if (result) {
                    changingActivity = true;
                    Intent intent = new Intent(this, GameWorld.class);
                    startActivityForResult(intent, 1);
                    prev_touches = StorageManager.getInstance().getTotalTouches();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public void onClickDailyChallenge(View view) {
        changingActivity = true;
        Intent intent = new Intent(this, DailyChallenge.class);
        startActivity(intent);
    }

    public void resetStorage(View view) {
        storage.resetStorage();
        coins.setText(storage.getTotalTouches() + "");
        gems.setText(storage.getTotalGems() + "");
        initializeShops(); //set defaults
    }

    public void startShop(View view) {
        changingActivity = true;
        Intent intent = new Intent(this, ShopMenu.class);
        startActivityForResult(intent,2);
    }

    public void goFacebookPage(View view) {
        changingActivity = true;
        Intent goFacebook = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/francisco.teixeira.507"));
        startActivity(goFacebook);
    }

    public void achievementButton(View view) {
        changingActivity = true;
        Intent goAchievements = new Intent(this, AchievementsMenu.class);
        startActivity(goAchievements);
    }

    public void goToSettings(View view) {
        changingActivity = true;
        Intent goSettings = new Intent(this, SettingsMenu.class);
        startActivity(goSettings);
    }

    public void rateApp(View view) {
        changingActivity = true;
        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {

        adButton.setEnabled(true);
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

        if (adsLeft)
            loadRewardedVideoAd();

        if (rewarded)
            Toast.makeText(this, "You earned 1 Gem", Toast.LENGTH_LONG).show();

        rewarded = false;
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

        storage.addGems(1);
        gems.setText(storage.getTotalGems() + "");
        rewarded = true;

        storage.removeAdsAvailableToday();
        if (storage.getAdsAvailableToday() > 0)
            adsLeftToday.setText(storage.getAdsAvailableToday() + " Left Today");
        else {
            adsLeftToday.setText("Come Back Tomorrow");
            adsLeft = false;
            adButton.setEnabled(false);
        }
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    /*private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener(){

        public void onAudioFocusChange(int focusChange)
        {
            if(focusChange <= 0)
                mediaPlayerManager.pause(); //LOSS -> PAUSE
            else
                mediaPlayerManager.play(); //GAIN -> PLAY
        }
    };
    AudioManager audioManager = (AudioManager)getApplicationContext(). getSystemService(this.AUDIO_SERVICE);

    int result = audioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
            AudioManager.AUDIOFOCUS_GAIN);
    @Override
    public void onAudioFocusChange(int focusChange)
    {
        if(focusChange <= 0)
            mediaPlayerManager.pause(); //LOSS -> PAUSE
        else
            mediaPlayerManager.play(); //GAIN -> PLAY
    }*/


    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void godMode(View v) {
        storage.setTotalTouches(100000);
        storage.addGems(100000);
        coins.setText(storage.getTotalTouches() + "");
        gems.setText(storage.getTotalGems() + "");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void buyEverything(View v)
    {
        buyAllBalls();
        buyAllSkins();
        buyAllTrails();
        buyAllBackgrounds();
        buyAllSounds();
    }

    private void buyAllBalls()
    {

    }

    private void buyAllSkins()
    {
        Field[] drawablesFields = R.drawable.class.getFields();

        for (Field field : drawablesFields) {
            try
            {
                String name = field.getName();
                if(name.contains("vector") || name.contains("icon") || name.contains("reactive") || name.contains("rainbow") ||
                        name.contains("colored") || name.contains("menu") || name.contains("title") || name.contains("background")
                        || name.contains("clear") || name.contains("spectrum"))
                    continue;

                int id_label = this.getResources().getIdentifier(name + "_Label", "id", getPackageName());
                int id = this.getResources().getIdentifier(name, "drawable", getPackageName());
                storage.addOwnedSkinLabel(id_label);
                storage.addOwnedSkin(id);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }


    }

    private void buyAllTrails()
    {

    }

    private void buyAllBackgrounds()
    {

    }

    private void buyAllSounds()
    {
        Field[] rawFields = R.raw.class.getFields();

        for (Field field : rawFields) {
            try
            {
                String name = field.getName();
                if(name.contains("background"))
                    continue;

                int id_label = this.getResources().getIdentifier(name + "_Label", "id", getPackageName());
                int id = this.getResources().getIdentifier(name, "raw", getPackageName());
                storage.addOwnedSoundLabel(id_label);
                storage.addOwnedSound(id);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
