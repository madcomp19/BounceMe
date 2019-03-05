package com.madcomp19gmail.bouncyball;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class AchievementsMenu extends AppCompatActivity {

    ProgressBar bar1;
    ProgressBar bar2;
    ProgressBar bar3;
    ProgressBar bar4;
    ProgressBar bar5;
    ProgressBar bar6;
    ProgressBar bar7;
    ProgressBar bar8;
    ProgressBar bar9;
    ProgressBar bar10;
    ProgressBar bar11;
    ProgressBar bar12;
    ProgressBar bar13;
    ProgressBar bar14;
    ProgressBar bar15;

    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    TextView text5;
    TextView text6;
    TextView text7;
    TextView text8;
    TextView text9;
    TextView text10;
    TextView text11;
    TextView text12;
    TextView text13;
    TextView text14;
    TextView text15;

    TextView reward1;
    TextView reward2;
    TextView reward3;
    TextView reward4;
    TextView reward5;
    TextView reward6;
    TextView reward7;
    TextView reward8;
    TextView reward9;
    TextView reward10;
    TextView reward11;
    TextView reward12;
    TextView reward13;
    TextView reward14;
    TextView reward15;

    private final int background_music_id = R.raw.background_music_2;

    private StorageManager storage;
    private MediaPlayerManager mediaPlayerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements_menu);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        bar1 = findViewById(R.id.circularProgressBar);
        bar2 = findViewById(R.id.circularProgressBar2);
        bar3 = findViewById(R.id.circularProgressBar3);
        bar4 = findViewById(R.id.circularProgressBar4);
        bar5 = findViewById(R.id.circularProgressBar5);
        bar6 = findViewById(R.id.circularProgressBar6);
        bar7 = findViewById(R.id.circularProgressBar7);
        bar8 = findViewById(R.id.circularProgressBar8);
        bar9 = findViewById(R.id.circularProgressBar9);
        bar10 = findViewById(R.id.circularProgressBar10);
        bar11 = findViewById(R.id.circularProgressBar11);
        bar12 = findViewById(R.id.circularProgressBar12);
        bar13 = findViewById(R.id.circularProgressBar13);
        bar14 = findViewById(R.id.circularProgressBar14);
        bar15 = findViewById(R.id.circularProgressBar15);

        text1 = findViewById(R.id.textView1);
        text2 = findViewById(R.id.textView2);
        text3 = findViewById(R.id.textView3);
        text4 = findViewById(R.id.textView4);
        text5 = findViewById(R.id.textView5);
        text6 = findViewById(R.id.textView6);
        text7 = findViewById(R.id.textView7);
        text8 = findViewById(R.id.textView8);
        text9 = findViewById(R.id.textView9);
        text10 = findViewById(R.id.textView10);
        text11 = findViewById(R.id.textView11);
        text12 = findViewById(R.id.textView12);
        text13 = findViewById(R.id.textView13);
        text14 = findViewById(R.id.textView14);
        text15 = findViewById(R.id.textView15);

        reward1 = findViewById(R.id.reward1);
        reward2 = findViewById(R.id.reward2);
        reward3 = findViewById(R.id.reward3);
        reward4 = findViewById(R.id.reward4);
        reward5 = findViewById(R.id.reward5);
        reward6 = findViewById(R.id.reward6);
        reward7 = findViewById(R.id.reward7);
        reward8 = findViewById(R.id.reward8);
        reward9 = findViewById(R.id.reward9);
        reward10 = findViewById(R.id.reward10);
        reward11 = findViewById(R.id.reward11);
        reward12 = findViewById(R.id.reward12);
        reward13 = findViewById(R.id.reward13);
        reward14 = findViewById(R.id.reward14);
        reward15 = findViewById(R.id.reward15);

        storage = StorageManager.getInstance();
        mediaPlayerManager = MediaPlayerManager.getInstance();

        if(storage.getMusicSetting())
        {
            mediaPlayerManager.loadSound(background_music_id);
            mediaPlayerManager.play();
        }

        updateProgressBars();

        updateRewardText();
    }

    @Override
    protected void onResume() {
        super.onResume();

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

    public void updateProgressBars()
    {
        bar1.setProgress(storage.getTotalBouncesEver());
        bar2.setProgress(storage.getTotalBouncesEver());
        bar3.setProgress(storage.getTotalBouncesEver());
        bar4.setProgress(storage.getTotalBouncesEver());
        bar5.setProgress(storage.getTotalBouncesEver());
        bar6.setProgress(storage.getTotalBouncesEver());
        bar7.setProgress(storage.getTotalBouncesEver());
        bar8.setProgress(storage.getTotalBouncesEver());
        bar9.setProgress(storage.getTotalBouncesEver());

        bar10.setProgress(storage.getNumberOfOwnedSkins());
        bar11.setProgress(storage.getNumberOfOwnedSkins());
        bar12.setProgress(storage.getNumberOfOwnedSkins());
        bar13.setProgress(storage.getNumberOfOwnedSkins());
        bar14.setProgress(storage.getNumberOfOwnedSkins());
        bar15.setProgress(storage.getNumberOfOwnedSkins());

        text1.setText(bar1.getProgress() + "/" + bar1.getMax() + " Bounces");
        text2.setText(bar2.getProgress() + "/" + bar2.getMax() + " Bounces");
        text3.setText(bar3.getProgress() + "/" + bar3.getMax() + " Bounces");
        text4.setText(bar4.getProgress() + "/" + bar4.getMax() + " Bounces");
        text5.setText(bar5.getProgress() + "/" + bar5.getMax() + " Bounces");
        text6.setText(bar6.getProgress() + "/" + bar6.getMax() + " Bounces");
        text7.setText(bar7.getProgress() + "/" + bar7.getMax() + " Bounces");
        text8.setText(bar8.getProgress() + "/" + bar8.getMax() + " Bounces");
        text9.setText(bar9.getProgress() + "/" + bar9.getMax() + " Bounces");
        text10.setText(bar10.getProgress() + "/" + bar10.getMax() + " Skins");
        text11.setText(bar11.getProgress() + "/" + bar11.getMax() + " Skins");
        text12.setText(bar12.getProgress() + "/" + bar12.getMax() + " Skins");
        text13.setText(bar13.getProgress() + "/" + bar13.getMax() + " Skins");
        text14.setText(bar14.getProgress() + "/" + bar14.getMax() + " Skins");
        text15.setText(bar15.getProgress() + "/" + bar15.getMax() + " Skins");
    }

    public void updateRewardText()
    {
        //Reward 1 ---------------------------------------------------------
        if(bar1.getProgress() == bar1.getMax() && !storage.getCollectedAchievements().contains(reward1.getId()))
        {
            canCollectAchievement(reward1);
        }
        if(storage.getCollectedAchievements().contains(reward1.getId()))
        {
            achievementCollected(reward1);
        }

        //Reward 2 -----------------------------------------------------------
        if(bar2.getProgress() == bar2.getMax() && !storage.getCollectedAchievements().contains(reward2.getId()))
        {
            canCollectAchievement(reward2);
        }
        if(storage.getCollectedAchievements().contains(reward2.getId()))
        {
            achievementCollected(reward2);
        }

        //Reward 3 -------------------------------------------------------------
        if(bar3.getProgress() == bar3.getMax() && !storage.getCollectedAchievements().contains(reward3.getId()))
        {
            canCollectAchievement(reward3);
        }
        if(storage.getCollectedAchievements().contains(reward3.getId()))
        {
            achievementCollected(reward3);
        }

        //Reward 4 ---------------------------------------------------------------
        if(bar4.getProgress() == bar4.getMax() && !storage.getCollectedAchievements().contains(reward4.getId()))
        {
            canCollectAchievement(reward4);
        }
        if(storage.getCollectedAchievements().contains(reward4.getId()))
        {
            achievementCollected(reward4);
        }

        //Reward 5 ---------------------------------------------------------------
        if(bar5.getProgress() == bar5.getMax() && !storage.getCollectedAchievements().contains(reward5.getId()))
        {
            canCollectAchievement(reward5);
        }
        if(storage.getCollectedAchievements().contains(reward5.getId()))
        {
            achievementCollected(reward5);
        }

        //Reward 6 ---------------------------------------------------------------
        if(bar6.getProgress() == bar6.getMax() && !storage.getCollectedAchievements().contains(reward6.getId()))
        {
            canCollectAchievement(reward6);
        }
        if(storage.getCollectedAchievements().contains(reward6.getId()))
        {
            achievementCollected(reward6);
        }

        //Reward 7 ---------------------------------------------------------------
        if(bar7.getProgress() == bar7.getMax() && !storage.getCollectedAchievements().contains(reward7.getId()))
        {
            canCollectAchievement(reward7);
        }
        if(storage.getCollectedAchievements().contains(reward7.getId()))
        {
            achievementCollected(reward7);
        }

        //Reward 8 ---------------------------------------------------------------
        if(bar8.getProgress() == bar8.getMax() && !storage.getCollectedAchievements().contains(reward8.getId()))
        {
            canCollectAchievement(reward8);
        }
        if(storage.getCollectedAchievements().contains(reward8.getId()))
        {
            achievementCollected(reward8);
        }

        //Reward 9 ---------------------------------------------------------------
        if(bar9.getProgress() == bar9.getMax() && !storage.getCollectedAchievements().contains(reward9.getId()))
        {
            canCollectAchievement(reward9);
        }
        if(storage.getCollectedAchievements().contains(reward9.getId()))
        {
            achievementCollected(reward9);
        }

        //Reward 10 ---------------------------------------------------------------
        if(bar10.getProgress() == bar10.getMax() && !storage.getCollectedAchievements().contains(reward10.getId()))
        {
            canCollectAchievement(reward10);
        }
        if(storage.getCollectedAchievements().contains(reward10.getId()))
        {
            achievementCollected(reward10);
        }

        //Reward 11 ---------------------------------------------------------------
        if(bar11.getProgress() == bar11.getMax() && !storage.getCollectedAchievements().contains(reward11.getId()))
        {
            canCollectAchievement(reward11);
        }
        if(storage.getCollectedAchievements().contains(reward11.getId()))
        {
            achievementCollected(reward11);
        }

        //Reward 12 ---------------------------------------------------------------
        if(bar12.getProgress() == bar12.getMax() && !storage.getCollectedAchievements().contains(reward12.getId()))
        {
            canCollectAchievement(reward12);
        }
        if(storage.getCollectedAchievements().contains(reward12.getId()))
        {
            achievementCollected(reward12);
        }

        //Reward 13 ---------------------------------------------------------------
        if(bar13.getProgress() == bar13.getMax() && !storage.getCollectedAchievements().contains(reward13.getId()))
        {
            canCollectAchievement(reward13);
        }
        if(storage.getCollectedAchievements().contains(reward13.getId()))
        {
            achievementCollected(reward13);
        }

        //Reward 14 ---------------------------------------------------------------
        if(bar14.getProgress() == bar14.getMax() && !storage.getCollectedAchievements().contains(reward14.getId()))
        {
            canCollectAchievement(reward14);
        }
        if(storage.getCollectedAchievements().contains(reward14.getId()))
        {
            achievementCollected(reward14);
        }

        //Reward 15 ---------------------------------------------------------------
        if(bar15.getProgress() == bar15.getMax() && !storage.getCollectedAchievements().contains(reward15.getId()))
        {
            canCollectAchievement(reward15);
        }
        if(storage.getCollectedAchievements().contains(reward15.getId()))
        {
            achievementCollected(reward15);
        }

    }

    public void canCollectAchievement(TextView reward)
    {
        reward.setText("COLLECT");
        reward.setBackgroundColor(Color.parseColor("#00FF00"));
        reward.setTextColor(Color.BLACK);
    }

    public void achievementCollected(TextView reward)
    {
        reward.setText("COLLECTED");
        reward.setBackgroundColor(Color.parseColor("#0B3B0B"));
        reward.setTextColor(Color.WHITE);
    }

    public void rewardClick1(View view)
    {
        if (reward1.getText() == "COLLECT")
        {
            storage.addGems(2);
            Toast.makeText(this, "You won 2 Gems", Toast.LENGTH_LONG).show();
            reward1.setText("COLLECTED");
            reward1.setBackgroundColor(Color.parseColor("#0B3B0B"));
            reward1.setTextColor(Color.WHITE);
            storage.addCollectedAchievement(reward1.getId());
            return;
        }

        if(reward1.getText() == "COLLECTED")
        {
            Toast.makeText(this, "You already claimed this reward", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "You haven't unlocked this achievement yet", Toast.LENGTH_SHORT).show();
    }

    public void rewardClick2(View view)
    {
        if (reward2.getText() == "COLLECT")
        {
            storage.addGems(5);
            Toast.makeText(this, "You won 5 Gems", Toast.LENGTH_LONG).show();
            reward2.setText("COLLECTED");
            reward2.setBackgroundColor(Color.parseColor("#0B3B0B"));
            reward2.setTextColor(Color.WHITE);
            storage.addCollectedAchievement(reward2.getId());
            return;
        }

        if(reward2.getText() == "COLLECTED")
        {
            Toast.makeText(this, "You already claimed this reward", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "You haven't unlocked this achievement yet", Toast.LENGTH_SHORT).show();
    }

    public void rewardClick3(View view)
    {
        if (reward3.getText() == "COLLECT")
        {
            storage.addGems(5);
            Toast.makeText(this, "You won 5 Gems", Toast.LENGTH_LONG).show();
            reward3.setText("COLLECTED");
            reward3.setBackgroundColor(Color.parseColor("#0B3B0B"));
            reward3.setTextColor(Color.WHITE);
            storage.addCollectedAchievement(reward3.getId());
            return;
        }

        if(reward3.getText() == "COLLECTED")
        {
            Toast.makeText(this, "You already claimed this reward", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "You haven't unlocked this achievement yet", Toast.LENGTH_SHORT).show();
    }

    public void rewardClick4(View view)
    {
        if (reward4.getText() == "COLLECT")
        {
            storage.addGems(5);
            Toast.makeText(this, "You won 5 Gems", Toast.LENGTH_LONG).show();
            reward4.setText("COLLECTED");
            reward4.setBackgroundColor(Color.parseColor("#0B3B0B"));
            reward4.setTextColor(Color.WHITE);
            storage.addCollectedAchievement(reward4.getId());
            return;
        }

        if(reward4.getText() == "COLLECTED")
        {
            Toast.makeText(this, "You already claimed this reward", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "You haven't unlocked this achievement yet", Toast.LENGTH_SHORT).show();
    }

    public void rewardClick5(View view)
    {
        if (reward5.getText() == "COLLECT")
        {
            storage.addGems(5);
            Toast.makeText(this, "You won 5 Gems", Toast.LENGTH_LONG).show();
            reward5.setText("COLLECTED");
            reward5.setBackgroundColor(Color.parseColor("#0B3B0B"));
            reward5.setTextColor(Color.WHITE);
            storage.addCollectedAchievement(reward5.getId());
            return;
        }

        if(reward5.getText() == "COLLECTED")
        {
            Toast.makeText(this, "You already claimed this reward", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "You haven't unlocked this achievement yet", Toast.LENGTH_SHORT).show();
    }

    public void rewardClick6(View view)
    {
        if (reward6.getText() == "COLLECT")
        {
            storage.addGems(5);
            Toast.makeText(this, "You won 5 Gems", Toast.LENGTH_LONG).show();
            reward6.setText("COLLECTED");
            reward6.setBackgroundColor(Color.parseColor("#0B3B0B"));
            reward6.setTextColor(Color.WHITE);
            storage.addCollectedAchievement(reward6.getId());
            return;
        }

        if(reward6.getText() == "COLLECTED")
        {
            Toast.makeText(this, "You already claimed this reward", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "You haven't unlocked this achievement yet", Toast.LENGTH_SHORT).show();
    }

    public void rewardClick7(View view)
    {
        if (reward7.getText() == "COLLECT")
        {
            storage.addGems(10);
            Toast.makeText(this, "You won 5 Gems", Toast.LENGTH_LONG).show();
            reward7.setText("COLLECTED");
            reward7.setBackgroundColor(Color.parseColor("#0B3B0B"));
            reward7.setTextColor(Color.WHITE);
            storage.addCollectedAchievement(reward7.getId());
            return;
        }

        if(reward7.getText() == "COLLECTED")
        {
            Toast.makeText(this, "You already claimed this reward", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "You haven't unlocked this achievement yet", Toast.LENGTH_SHORT).show();
    }

    public void rewardClick8(View view)
    {
        if (reward8.getText() == "COLLECT")
        {
            storage.addGems(50);
            Toast.makeText(this, "You won 5 Gems", Toast.LENGTH_LONG).show();
            reward8.setText("COLLECTED");
            reward8.setBackgroundColor(Color.parseColor("#0B3B0B"));
            reward8.setTextColor(Color.WHITE);
            storage.addCollectedAchievement(reward8.getId());
            return;
        }

        if(reward8.getText() == "COLLECTED")
        {
            Toast.makeText(this, "You already claimed this reward", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "You haven't unlocked this achievement yet", Toast.LENGTH_SHORT).show();
    }

    public void rewardClick9(View view)
    {
        if (reward9.getText() == "COLLECT")
        {
            storage.addGems(100);
            Toast.makeText(this, "You won 100 Gems", Toast.LENGTH_LONG).show();
            reward9.setText("COLLECTED");
            reward9.setBackgroundColor(Color.parseColor("#0B3B0B"));
            reward9.setTextColor(Color.WHITE);
            storage.addCollectedAchievement(reward9.getId());
            return;
        }

        if(reward9.getText() == "COLLECTED")
        {
            Toast.makeText(this, "You already claimed this reward", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "You haven't unlocked this achievement yet", Toast.LENGTH_SHORT).show();
    }

    public void rewardClick10(View view)
    {
        if (reward10.getText() == "COLLECT")
        {
            storage.addGems(2);
            Toast.makeText(this, "You won 2 Gems", Toast.LENGTH_LONG).show();
            reward10.setText("COLLECTED");
            reward10.setBackgroundColor(Color.parseColor("#0B3B0B"));
            reward10.setTextColor(Color.WHITE);
            storage.addCollectedAchievement(reward10.getId());
            return;
        }

        if(reward10.getText() == "COLLECTED")
        {
            Toast.makeText(this, "You already claimed this reward", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "You haven't unlocked this achievement yet", Toast.LENGTH_SHORT).show();
    }

    public void rewardClick11(View view)
    {
        if (reward11.getText() == "COLLECT")
        {
            storage.addGems(5);
            Toast.makeText(this, "You won 5 Gems", Toast.LENGTH_LONG).show();
            reward11.setText("COLLECTED");
            reward11.setBackgroundColor(Color.parseColor("#0B3B0B"));
            reward11.setTextColor(Color.WHITE);
            storage.addCollectedAchievement(reward11.getId());
            return;
        }

        if(reward11.getText() == "COLLECTED")
        {
            Toast.makeText(this, "You already claimed this reward", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "You haven't unlocked this achievement yet", Toast.LENGTH_SHORT).show();
    }

    public void rewardClick12(View view)
    {
        if (reward12.getText() == "COLLECT")
        {
            storage.addGems(5);
            Toast.makeText(this, "You won 5 Gems", Toast.LENGTH_LONG).show();
            reward12.setText("COLLECTED");
            reward12.setBackgroundColor(Color.parseColor("#0B3B0B"));
            reward12.setTextColor(Color.WHITE);
            storage.addCollectedAchievement(reward12.getId());
            return;
        }

        if(reward12.getText() == "COLLECTED")
        {
            Toast.makeText(this, "You already claimed this reward", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "You haven't unlocked this achievement yet", Toast.LENGTH_SHORT).show();
    }

    public void rewardClick13(View view)
    {
        if (reward13.getText() == "COLLECT")
        {
            storage.addGems(5);
            Toast.makeText(this, "You won 5 Gems", Toast.LENGTH_LONG).show();
            reward13.setText("COLLECTED");
            reward13.setBackgroundColor(Color.parseColor("#0B3B0B"));
            reward13.setTextColor(Color.WHITE);
            storage.addCollectedAchievement(reward13.getId());
            return;
        }

        if(reward13.getText() == "COLLECTED")
        {
            Toast.makeText(this, "You already claimed this reward", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "You haven't unlocked this achievement yet", Toast.LENGTH_SHORT).show();
    }

    public void rewardClick14(View view)
    {
        if (reward14.getText() == "COLLECT")
        {
            storage.addGems(10);
            Toast.makeText(this, "You won 2 Gems", Toast.LENGTH_LONG).show();
            reward14.setText("COLLECTED");
            reward14.setBackgroundColor(Color.parseColor("#0B3B0B"));
            reward14.setTextColor(Color.WHITE);
            storage.addCollectedAchievement(reward14.getId());
            return;
        }

        if(reward14.getText() == "COLLECTED")
        {
            Toast.makeText(this, "You already claimed this reward", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "You haven't unlocked this achievement yet", Toast.LENGTH_SHORT).show();
    }

    public void rewardClick15(View view)
    {
        if (reward15.getText() == "COLLECT")
        {
            storage.addGems(50);
            Toast.makeText(this, "You won 50 Gems", Toast.LENGTH_LONG).show();
            reward15.setText("COLLECTED");
            reward15.setBackgroundColor(Color.parseColor("#0B3B0B"));
            reward15.setTextColor(Color.WHITE);
            storage.addCollectedAchievement(reward15.getId());
            return;
        }

        if(reward15.getText() == "COLLECTED")
        {
            Toast.makeText(this, "You already claimed this reward", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "You haven't unlocked this achievement yet", Toast.LENGTH_SHORT).show();
    }
}
