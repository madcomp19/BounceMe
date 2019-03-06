package com.madcomp19gmail.bouncyball;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
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

public class MainMenu extends AppCompatActivity implements RewardedVideoAdListener {

    //private static int touches;
    private StorageManager storage;
    private MediaPlayerManager mediaPlayerManager;

    private final int background_music_id = R.raw.background_music_2;

    public static boolean prev_act_GameWorld = false;
    private static int prev_touches;
    TextView coins;
    TextView gems;
    Button adButton;
    private RewardedVideoAd mRewardedVideoAd;

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
        //touches = StorageManager.getInstance().getTotalTouches();*/

        /*mediaPlayer = MediaPlayer.create(this, R.raw.background_music_2);
        mediaPlayer.setLooping(true);

        if(storage.getMusicSetting())
            mediaPlayer.start();*/

        MediaPlayerManager.initialize(this, background_music_id);
        mediaPlayerManager = MediaPlayerManager.getInstance();

        if(storage.getMusicSetting())
        {
            mediaPlayerManager.loadSound(background_music_id);
            mediaPlayerManager.play();
        }

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        loadRewardedVideoAd();
    }

    private void loadRewardedVideoAd() {
        //LEMBRAR MUDAR O ID!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());

        adButton.setEnabled(false);
    }

    public void watchAdMainMenu(View view)
    {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        //StorageManager.getInstance().setTotalTouches(touches);

        if(prev_act_GameWorld)
        {
            int new_touches = Math.abs(StorageManager.getInstance().getTotalTouches() - prev_touches);

            if(new_touches > 300)
            {
                Intent intent = new Intent(this, DoublePointsAlert.class);
                startActivity(intent);
            }

            prev_act_GameWorld = false;
        }

        coins.setText(storage.getTotalTouches() + "");
        gems.setText(storage.getTotalGems() + "");

        if(storage.getMusicSetting())
        {
            mediaPlayerManager.loadSound(background_music_id);
            mediaPlayerManager.play();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(storage.getMusicSetting())
            mediaPlayerManager.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //if(storage.getMusicSetting())
            //mediaPlayerManager.pause();
    }

    public static int getPrevTouches()
    {
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
        Intent intent = new Intent(this, GameWorld.class);
        startActivity(intent);
        prev_touches = StorageManager.getInstance().getTotalTouches();
    }

    public void startChallengeActivity(View view) {
        String todays_date = storage.getTodayDateString();
        String last_daily = storage.getLastDailyChallengeDateString();

        //if the player has played the daily challenge today
        if (todays_date.equals(last_daily)) {
            Toast toast = Toast.makeText(getApplicationContext(), "Come back tomorrow!", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Intent intent = new Intent(this, DailyChallenge.class);
            startActivity(intent);
        }
    }

    public void resetStorage(View view){
        storage.resetStorage();
    }

    public void startShop(View view)
    {
        Intent intent = new Intent(this, ShopMenu.class);
        startActivity(intent);
    }

    public void goFacebookPage(View view)
    {
        Intent goFacebook = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/francisco.teixeira.507"));
        startActivity(goFacebook);
    }

    public void achievementButton(View view)
    {
        Intent goAchievements = new Intent(this, AchievementsMenu.class);
        startActivity(goAchievements);
    }

    public void goToSettings(View view)
    {
        Intent goSettings = new Intent(this, SettingsMenu.class);
        startActivity(goSettings);
    }

    public void rateApp(View view)
    {
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

        loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

        storage.addGems(1);
        gems.setText(storage.getTotalGems() + "");
        Toast.makeText(this, "You earned 1 Gem", Toast.LENGTH_LONG).show();
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

        loadRewardedVideoAd();
    }
}
