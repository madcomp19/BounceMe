package com.madcomp19gmail.bouncyball;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class DoublePointsAlert extends AppCompatActivity implements RewardedVideoAdListener {

    TextView numberBouncesEarned;
    int bouncesEarned;
    StorageManager storage;
    Button watchAdButton;

    private RewardedVideoAd mRewardedVideoAd;

    private MediaPlayerManager mediaPlayerManager;
    private final int background_music_id = R.raw.background_music_2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_points_alert);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.8f), (int) (height * 0.4f));

        bouncesEarned = GameWorld.getTouches() - MainMenu.getPrevTouches();

        watchAdButton = findViewById(R.id.watchAdButton);

        storage = StorageManager.getInstance();
        mediaPlayerManager = MediaPlayerManager.getInstance();

        numberBouncesEarned = findViewById(R.id.numberBounces);
        numberBouncesEarned.setText(bouncesEarned + " Bounces");

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        loadRewardedVideoAd();
    }

    public void watchAd(View view)
    {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }

    public void closePopup(View view)
    {
        finish();
    }

    private void loadRewardedVideoAd() {
        //LEMBRAR MUDAR O ID!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());

        watchAdButton.setEnabled(false);
    }

    @Override
    public void onRewardedVideoAdLoaded() {

        watchAdButton.setEnabled(true);
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

        storage.setTotalTouches(storage.getTotalTouches() + bouncesEarned);
        watchAdButton.setVisibility(View.INVISIBLE);
        numberBouncesEarned.setText(bouncesEarned * 2 + " Bounces");
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

    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();

        if(storage.getMusicSetting())
        {
            mediaPlayerManager.loadSound(background_music_id);
            mediaPlayerManager.play();
        }
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();

        if(storage.getMusicSetting())
            mediaPlayerManager.pause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }
}
