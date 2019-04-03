package com.madcomp19gmail.bouncyball;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class AchievementsMenu extends AppCompatActivity {

    ArrayList<TextView> texts;
    ArrayList<ProgressBar> bars;
    ArrayList<TextView> rewards;

    private final int background_music_id = R.raw.background_music_2;

    private StorageManager storage;
    private MediaPlayerManager mediaPlayerManager;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements_menu);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        texts = new ArrayList<>();
        bars = new ArrayList<>();
        rewards = new ArrayList<>();

        for(int i=1; i<=24; i++)
        {
            String text = "textView" + i;
            texts.add((TextView) findViewById(getResources().getIdentifier(text, "id", getPackageName())));
            //texts.set(i, (TextView) findViewById(getResources().getIdentifier(text, "id", getPackageName())));

            String bar = "circularProgressBar" + i;
            bars.add((ProgressBar) findViewById(getResources().getIdentifier(bar, "id", getPackageName())));
            //bars.set(i, (ProgressBar) findViewById(getResources().getIdentifier(bar, "id", getPackageName())));

            String reward = "reward" + i;
            rewards.add((TextView) findViewById(getResources().getIdentifier(reward, "id", getPackageName())));
            //rewards.set(i, (TextView) findViewById(getResources().getIdentifier(reward, "id", getPackageName())));
        }

        storage = StorageManager.getInstance();
        mediaPlayerManager = MediaPlayerManager.getInstance();

        /*if(storage.getMenuMusicSetting())
        {
            mediaPlayerManager.loadSound(background_music_id, "Menu");
            mediaPlayerManager.play();
        }*/

        updateProgressBars();

        updateRewardText();

        if(!storage.getNoAds()) {
            mAdView = findViewById(R.id.bannerAdTrailShop);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(storage.getMenuMusicSetting())
        {
            mediaPlayerManager.loadSound(background_music_id, "Menu");
            mediaPlayerManager.play();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(!this.isFinishing() && storage.getMenuMusicSetting())
            mediaPlayerManager.pause();
    }

    public void updateProgressBars()
    {
        //Progress bars
        for(int i = 0; i < 9; i++)
        {
            bars.get(i).setProgress(storage.getTotalBouncesEver());
        }

        for(int i = 9; i < 15; i++)
        {
            bars.get(i).setProgress(storage.getNumberOfOwnedSkins());
        }

        for(int i = 15; i < 17; i++)
        {
            bars.get(i).setProgress(storage.getNumberOfOwnedTrails());
        }

        for(int i = 17; i < 21; i++)
        {
            bars.get(i).setProgress(storage.getNumberOfOwnedSounds());
        }

        for(int i = 21; i < 24; i++)
        {
            bars.get(i).setProgress(storage.getNumberOfOwnedBackgrounds());
        }

        int i = 0;

        for(TextView text : texts)
        {
            if(text.getText().toString().contains("Bounces"))
                text.setText(bars.get(i).getProgress() + "/" + bars.get(i).getMax() + " Bounces");

            if(text.getText().toString().contains("Skins"))
                text.setText(bars.get(i).getProgress() + "/" + bars.get(i).getMax() + " Skins");

            if(text.getText().toString().contains("Trails"))
                text.setText(bars.get(i).getProgress() + "/" + bars.get(i).getMax() + " Trails");

            if(text.getText().toString().contains("Sounds"))
                text.setText(bars.get(i).getProgress() + "/" + bars.get(i).getMax() + " Sounds");

            if(text.getText().toString().contains("Backgrounds"))
                text.setText(bars.get(i).getProgress() + "/" + bars.get(i).getMax() + " Backgrounds");

            i++;
        }
    }

    public void updateRewardText()
    {
        int i = 0;

        for(ProgressBar bar : bars)
        {
            if(bar.getProgress() == bar.getMax() && !storage.getCollectedAchievements().contains(rewards.get(i).getId()))
                canCollectAchievement(rewards.get(i));

            if(storage.getCollectedAchievements().contains(rewards.get(i).getId()))
                achievementCollected(rewards.get(i));

            i++;
        }
    }

    public void canCollectAchievement(TextView reward)
    {
        reward.setText("COLLECT");
        reward.setBackgroundResource(R.drawable.rounded_corners2_vector);
        reward.setTextColor(Color.BLACK);
    }

    public void achievementCollected(TextView reward)
    {
        reward.setText("COLLECTED");
        reward.setBackgroundResource(R.drawable.rounded_corners3_vector);
        reward.setTextColor(Color.WHITE);
    }

    public void rewardClick(View view)
    {
        TextView reward = (TextView) findViewById(view.getId());
        String name = view.getResources().getResourceEntryName(view.getId());
        int gems;

        // Getting the right reward/gems
        //region
        switch (name)
        {
            case "reward1":
            case "reward10":
                gems = 2;
                break;
            case "reward2":
            case "reward3":
            case "reward4":
            case "reward5":
            case "reward6":
            case "reward11":
            case "reward12":
            case "reward13":
            case "reward16":
            case "reward19":
            case "reward22":
                gems = 5;
                break;
            case "reward7":
            case "reward14":
            case "reward17":
                gems = 10;
                break;
            case "reward20":
            case "reward23":
                gems = 20;
                break;
            case "reward18":
                gems = 25;
                break;
            case "reward8":
            case "reward15":
            case "reward21":
            case "reward24":
                gems = 50;
                break;
            case "reward9":
                gems = 100;
                break;
            default:
                gems = 0;
                 break;
        }
        //endregion

        if (reward.getText() == "COLLECT")
        {
            storage.addGems(gems);
            Toast.makeText(this, "You won " + gems + " Gems", Toast.LENGTH_LONG).show();
            reward.setText("COLLECTED");
            reward.setBackgroundResource(R.drawable.rounded_corners3_vector);
            reward.setTextColor(Color.WHITE);
            storage.addCollectedAchievement(reward.getId());
            return;
        }

        if(reward.getText() == "COLLECTED")
        {
            Toast.makeText(this, "You already claimed this reward", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "You haven't unlocked this achievement yet", Toast.LENGTH_SHORT).show();
    }
}
