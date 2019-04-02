package com.madcomp19gmail.bouncyball;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.lang.reflect.Field;

import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;

public class MainMenu extends AppCompatActivity implements RewardedVideoAdListener, BillingProcessor.IBillingHandler {

    //private static int touches;
    private StorageManager storage;
    private MediaPlayerManager mediaPlayerManager;

    private final int background_music_id = R.raw.background_music_2;

    private boolean changingActivity;

    public static boolean prev_act_GameWorld = false;
    private static int prev_touches;
    private static int prev_gems;
    TextView coins;
    TextView gems;
    TextView adsLeftToday;
    Button adButton;
    private RewardedVideoAd mRewardedVideoAd;
    private boolean rewarded = false;
    private boolean adsLeft = true;
    private boolean isWatchingDoublePointsAd = false;

    private Dialog coinDialog;
    private Dialog gemDialog;
    private Dialog doublePointsDialog;

    private DisplayMetrics metrics;
    private int width, height;

    private final String default_ball = "3_2_3_4";
    private final int default_ball_label = R.id.b_3_2_3_4_Label;
    private final int default_skin = R.drawable.emoji_0;
    private final int default_skin_label = R.id.emoji_0_Label;
    private final int default_sound = 0;
    private final int default_sound_label = R.id.mute_Label;
    private final int default_trail = -1;
    private final int default_trail_label = R.id.clear_Label;
    private final int default_background = R.drawable.background_1;
    private final int default_background_label = R.id.background_1_Label;

    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        MobileAds.initialize(this, "ca-app-pub-5557351606937995~8015272303");

        SoundPoolManager.initialize(this);
        StorageManager.initialize(this);
        storage = StorageManager.getInstance();
        coins = findViewById(R.id.coins);
        gems = findViewById(R.id.gems);
        adButton = findViewById(R.id.adButton);
        adsLeftToday = findViewById(R.id.adsLeftToday);

        changingActivity = false;

        MediaPlayerManager.initialize(this, background_music_id);
        mediaPlayerManager = MediaPlayerManager.getInstance();

        if (storage.getMenuMusicSetting()) {
            mediaPlayerManager.loadSound(background_music_id, "Menu");
            mediaPlayerManager.play();
        } else
            mediaPlayerManager.pause();

        //SoundPoolManager.getInstance().loadSound(R.raw.cash);

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        if (storage.getAdsAvailableToday() > 0)
            adsLeftToday.setText(storage.getAdsAvailableToday() + " Left Today");
        else {
            adsLeftToday.setText("Come Back Tomorrow");
            adsLeft = false;
            adButton.setEnabled(false);
        }

        loadRewardedVideoAd();

        initializeShops();

        coinDialog = new Dialog(this);
        gemDialog = new Dialog(this);
        doublePointsDialog = new Dialog(this);

        metrics = getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;

        ImageView iv = (ImageView) findViewById(R.id.bounce);
        if (iv.getLayoutParams().height > (int) (0.2 * height)) {
            iv.getLayoutParams().height = (int) (0.2 * height);
            iv.getLayoutParams().width = (int) (0.4 * height);
        }
        Glide.with(this).load(R.drawable.bounce).into(iv);

        bp = new BillingProcessor(this, null, this);
        bp.initialize();

        AppRate.with(this)
                .setInstallDays(5) // default 10, 0 means install day.
                .setLaunchTimes(5) // default 10
                .setRemindInterval(2) // default 1
                .setShowLaterButton(true) // default true
                .setDebug(true) // default false
                .monitor();

        AppRate.showRateDialogIfMeetsConditions(this);
    }

    private void initializeShops() {
        if (storage.getSelectedBall() == "0_0_0_0") {
            storage.addOwnedBall(default_ball);
            storage.addOwnedBallLabel(default_ball_label);
            storage.setSelectedBall(default_ball);
            storage.setSelectedBallLabel(default_ball_label);
        }

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

        if (storage.getSelectedBackground() == 0) {
            storage.addOwnedBackground(default_background);
            storage.addOwnedBackgroundLabel(default_background_label);
            storage.setSelectedBackground(default_background);
            storage.setSelectedBackgroundLabel(default_background_label);
        }
    }

    private void loadRewardedVideoAd() {
        //LEMBRAR MUDAR O ID!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());

        adButton.setEnabled(false);
        adButton.setBackgroundResource(R.drawable.roulette_custom_background_disabled);
    }

    public void watchAdMainMenu(View view) {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //StorageManager.getInstance().setTotalBounces(touches);
        mediaPlayerManager.changeVolume(1.0f);

        if (prev_act_GameWorld) {

            int new_touches = Math.abs(StorageManager.getInstance().getTotalBounces() - prev_touches);

            if (new_touches > 300) {
                showDoublePointsDialog();
            }

            prev_act_GameWorld = false;
        }

        coins.setText(storage.getTotalBounces() + "");
        gems.setText(storage.getTotalGems() + "");

        if (!storage.getLastAdDateString().equals(storage.getTodayDateString())) {
            storage.resetAdAvailableToday();
        }

        if (storage.getAdsAvailableToday() > 0)
            adsLeftToday.setText(storage.getAdsAvailableToday() + " Left Today");
        else {
            adsLeftToday.setText("Come Back Tomorrow");
            adsLeft = false;
            adButton.setEnabled(false);
            adButton.setBackgroundResource(R.drawable.roulette_custom_background_disabled);
        }

        if (storage.getMenuMusicSetting()) {
            mediaPlayerManager.pause();
            mediaPlayerManager.loadSound(background_music_id, "Menu");
            mediaPlayerManager.play();
        } else
            mediaPlayerManager.pause();

        //SoundPoolManager.getInstance().loadSound(R.raw.cash);
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

        if (bp != null) {
            bp.release();
        }
        super.onDestroy();

        //if(storage.getMenuMusicSetting())
        //mediaPlayerManager.pause();
    }

    public static int getPrevTouches() {
        return prev_touches;
    }

    public static int getPrevGems() {
        return prev_gems;
    }

    /*public static void addTouch() {
        touches++;
        storage.setTotalBounces(touches);
    }

    public static int getTouches() {
        return touches;
    }*/

    public void playGame(View view) {
        changingActivity = true;
        Intent intent = new Intent(this, GameWorld.class);
        startActivity(intent);
        prev_touches = storage.getTotalBounces();
        prev_gems = storage.getTotalGems();
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
                    prev_touches = StorageManager.getInstance().getTotalBounces();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onClickDailyChallenge(View view) {
        changingActivity = true;
        Intent intent = new Intent(this, DailyChallenge.class);
        startActivity(intent);
    }

    public void resetStorage(View view) {
        storage.resetStorage();
        coins.setText(storage.getTotalBounces() + "");
        gems.setText(storage.getTotalGems() + "");
        initializeShops(); //set defaults
    }

    public void startShop(View view) {
        changingActivity = true;
        Intent intent = new Intent(this, ShopMenu.class);
        startActivityForResult(intent, 2);
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

    // ads
    // region
    @Override
    public void onRewardedVideoAdLoaded() {

        if (adsLeft) {
            adButton.setEnabled(true);
            adButton.setBackgroundResource(R.drawable.roulette_custom_background);
        }
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        isWatchingDoublePointsAd = false;
        loadRewardedVideoAd();

        if (rewarded)
            Toast.makeText(this, "You earned 1 Gem", Toast.LENGTH_SHORT).show();

        rewarded = false;
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

        if (isWatchingDoublePointsAd) {
            storage.setTotalBounces(storage.getTotalBounces() + GameWorld.getTouches() - getPrevTouches());
            TextView test = ((TextView) doublePointsDialog.findViewById(R.id.numberBounces));
            int result = 2 * (GameWorld.getTouches() - getPrevTouches());
            test.setText(result + " " + "Bounces");
            coins.setText(storage.getTotalBounces() + result + "");
            isWatchingDoublePointsAd = true;
            Button watchAdButton = (Button) doublePointsDialog.findViewById(R.id.watchAdButton);
            watchAdButton.setEnabled(false);
            watchAdButton.setBackgroundResource(R.drawable.rounded_button6_vector);
        } else {
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

            storage.setLastAdDate(storage.getTodayDateString());
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

    // endregion


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
        storage.setTotalBounces(100000);
        storage.addGems(100000);
        coins.setText(storage.getTotalBounces() + "");
        gems.setText(storage.getTotalGems() + "");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////


    // buy everything
    //region
    public void buyEverything(View v) {

        SoundPoolManager.getInstance().playSound();

        Thread t = new Thread(new Runnable() {
            public void run() {

                buyAllBalls();
                buyAllSkins();
                buyAllTrails();
                buyAllBackgrounds();
                buyAllSounds();

            }
        });

        t.start();
    }

    private void buyAllBalls() {

        Field[] drawableFields = R.drawable.class.getFields();

        for (Field field : drawableFields) {
            try {
                String name = field.getName();

                if (!name.startsWith("b_"))
                    continue;

                int id_label = this.getResources().getIdentifier(name + "_Label", "id", getPackageName());
                storage.addOwnedBallLabel(id_label);

                name = name.substring(2);
                storage.addOwnedBall(name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void buyAllSkins() {
        Field[] drawablesFields = R.drawable.class.getFields();

        for (Field field : drawablesFields) {
            try {
                String name = field.getName();
                if (name.contains("vector") || name.contains("icon") || name.contains("reactive") || name.contains("rainbow") ||
                        name.contains("colored") || name.contains("menu") || name.contains("title") || name.contains("background")
                        || name.contains("clear") || name.contains("spectrum"))
                    continue;

                int id_label = this.getResources().getIdentifier(name + "_Label", "id", getPackageName());
                int id = this.getResources().getIdentifier(name, "drawable", getPackageName());
                storage.addOwnedSkinLabel(id_label);
                storage.addOwnedSkin(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void buyAllTrails() {
        int res_id;
        int trail;
        int id_label;

        for (int i = 0; i < 21; i++) {

            res_id = this.getResources().getIdentifier("colored_" + (i + 1), "integer", getPackageName());
            trail = this.getResources().getInteger(res_id);
            id_label = this.getResources().getIdentifier("colored_" + (i + 1) + "_Label", "id", getPackageName());
            storage.addOwnedTrail(trail);
            storage.addOwnedTrailLabel(id_label);
        }

        res_id = this.getResources().getIdentifier("reactive", "integer", getPackageName());
        trail = this.getResources().getInteger(res_id);
        id_label = this.getResources().getIdentifier("reactive" + "_Label", "id", getPackageName());
        storage.addOwnedTrail(trail);
        storage.addOwnedTrailLabel(id_label);

        res_id = this.getResources().getIdentifier("rainbow", "integer", getPackageName());
        trail = this.getResources().getInteger(res_id);
        id_label = this.getResources().getIdentifier("rainbow" + "_Label", "id", getPackageName());
        storage.addOwnedTrail(trail);
        storage.addOwnedTrailLabel(id_label);

        res_id = this.getResources().getIdentifier("spectrum", "integer", getPackageName());
        trail = this.getResources().getInteger(res_id);
        id_label = this.getResources().getIdentifier("spectrum" + "_Label", "id", getPackageName());
        storage.addOwnedTrail(trail);
        storage.addOwnedTrailLabel(id_label);

        res_id = this.getResources().getIdentifier("clear", "integer", getPackageName());
        trail = this.getResources().getInteger(res_id);
        id_label = this.getResources().getIdentifier("clear" + "_Label", "id", getPackageName());
        storage.addOwnedTrail(trail);
        storage.addOwnedTrailLabel(id_label);
    }

    private void buyAllBackgrounds() {

        Field[] drawableFields = R.drawable.class.getFields();

        for (Field field : drawableFields) {
            try {
                String name = field.getName();
                if (!name.startsWith("background") || name.contains("icon") || name.contains("title"))
                    continue;

                int id_label = this.getResources().getIdentifier(name + "_Label", "id", getPackageName());
                int id = this.getResources().getIdentifier(name, "drawable", getPackageName());
                storage.addOwnedBackgroundLabel(id_label);
                storage.addOwnedBackground(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void buyAllSounds() {
        Field[] rawFields = R.raw.class.getFields();

        for (Field field : rawFields) {
            try {
                String name = field.getName();
                if (name.contains("background"))
                    continue;

                int id_label = this.getResources().getIdentifier(name + "_Label", "id", getPackageName());
                int id = this.getResources().getIdentifier(name, "raw", getPackageName());
                storage.addOwnedSoundLabel(id_label);
                storage.addOwnedSound(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // endregion

    // buy coins & buy gems dialogs
    //region
    public void buyCoins(View view) {

        coinDialog.setContentView(R.layout.buy_coins_popup);

        TextView txtClose = (TextView) coinDialog.findViewById(R.id.closeButtonCoinsPopup);
        final TextView buyCoinPack1 = (TextView) coinDialog.findViewById(R.id.buyCoin1);
        final TextView buyCoinPack2 = (TextView) coinDialog.findViewById(R.id.buyCoin2);
        final TextView buyCoinPack3 = (TextView) coinDialog.findViewById(R.id.buyCoin3);

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coinDialog.dismiss();
            }
        });

        buyCoinPack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String text = buyCoinPack1.getText().toString();
                int price = Integer.valueOf(buyCoinPack1.getText().toString());
                int total_gems = storage.getTotalGems();

                if (total_gems > price) {
                    buyCoinPack1.setEnabled(false);
                    buyCoinPack1.setCompoundDrawables(null, null, null, null);
                    buyCoinPack1.setPadding(0, 0, 0, 0);
                    buyCoinPack1.setText("Bought");
                    storage.takeGems(price);
                    storage.setTotalBounces(storage.getTotalBounces() + 10000);
                    coins.setText(storage.getTotalBounces() + "");
                    gems.setText(storage.getTotalGems() + "");

                    SoundPoolManager.getInstance().playSound();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            buyCoinPack1.setEnabled(true);
                            buyCoinPack1.setText(text);
                            buyCoinPack1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.gem_icon_small, 0);
                            buyCoinPack1.setPadding(55, 0, 55, 0);
                        }
                    }, 2000);
                } else
                    buyGems(getWindow().getDecorView());
            }
        });

        buyCoinPack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String text = buyCoinPack2.getText().toString();
                int price = Integer.valueOf(buyCoinPack2.getText().toString());
                int total_gems = storage.getTotalGems();

                if (total_gems > price) {
                    buyCoinPack2.setEnabled(false);
                    buyCoinPack2.setCompoundDrawables(null, null, null, null);
                    buyCoinPack2.setPadding(0, 0, 0, 0);
                    buyCoinPack2.setText("Bought");
                    storage.takeGems(price);
                    storage.setTotalBounces(storage.getTotalBounces() + 75000);
                    coins.setText(storage.getTotalBounces() + "");
                    gems.setText(storage.getTotalGems() + "");

                    SoundPoolManager.getInstance().playSound();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            buyCoinPack2.setEnabled(true);
                            buyCoinPack2.setText(text);
                            buyCoinPack2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.gem_icon_small, 0);
                            buyCoinPack2.setPadding(55, 0, 55, 0);
                        }
                    }, 2000);
                } else
                    buyGems(getWindow().getDecorView());
            }
        });

        buyCoinPack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String text = buyCoinPack3.getText().toString();
                int price = Integer.valueOf(buyCoinPack3.getText().toString());
                int total_gems = storage.getTotalGems();

                if (total_gems > price) {
                    buyCoinPack3.setEnabled(false);
                    buyCoinPack3.setCompoundDrawables(null, null, null, null);
                    buyCoinPack3.setPadding(0, 0, 0, 0);
                    buyCoinPack3.setText("Bought");
                    storage.takeGems(price);
                    storage.setTotalBounces(storage.getTotalBounces() + 200000);
                    coins.setText(storage.getTotalBounces() + "");
                    gems.setText(storage.getTotalGems() + "");

                    SoundPoolManager.getInstance().playSound();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            buyCoinPack3.setEnabled(true);
                            buyCoinPack3.setText(text);
                            buyCoinPack3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.gem_icon_small, 0);
                            buyCoinPack3.setPadding(46, 0, 46, 0);
                        }
                    }, 2000);
                } else
                    buyGems(getWindow().getDecorView());
            }
        });

        coinDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        coinDialog.show();

        Window window = coinDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void buyGems(View view) {

        gemDialog.setContentView(R.layout.buy_gems_popup);

        TextView txtClose = (TextView) gemDialog.findViewById(R.id.closeButtonGemsPopup);
        TextView buyGemPack1 = (TextView) gemDialog.findViewById(R.id.gemPack1);
        TextView buyGemPack2 = (TextView) gemDialog.findViewById(R.id.gemPack2);
        TextView buyGemPack3 = (TextView) gemDialog.findViewById(R.id.gemPack3);

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gemDialog.dismiss();
            }
        });

        buyGemPack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(MainMenu.this, "android.test.purchased");
                //bp.consumePurchase("android.test.purchased");
            }
        });

        gemDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        gemDialog.show();

        Window window = gemDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
    //endregion

    private void showDoublePointsDialog() {
        doublePointsDialog.setContentView(R.layout.double_points_dialog);
        int n_bounces = GameWorld.getTouches() - MainMenu.getPrevTouches();

        Button okButton = (Button) doublePointsDialog.findViewById(R.id.okButton);
        final Button watchAdButton = (Button) doublePointsDialog.findViewById(R.id.watchAdButton);
        TextView bounces = (TextView) doublePointsDialog.findViewById(R.id.numberBounces);

        bounces.setText(n_bounces + " Bounces");

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doublePointsDialog.dismiss();
            }
        });

        watchAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRewardedVideoAd.isLoaded()) {
                    isWatchingDoublePointsAd = true;
                    mRewardedVideoAd.show();
                } else {
                    Toast.makeText(getApplicationContext(), "Can't watch an ad now", Toast.LENGTH_SHORT).show();
                }
            }
        });

        doublePointsDialog.getWindow().setLayout(width, height);
        doublePointsDialog.setCancelable(false);
        doublePointsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        doublePointsDialog.show();
    }

    public void buyNoAds(View v) {
        bp.purchase(MainMenu.this, "android.test.purchased");
    }

    // In app purchases
    // region

    @Override
    public void onBillingInitialized() {
        /*
         * Called when BillingProcessor was initialized and it's ready to purchase
         */
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        Toast.makeText(this, "Purchase successful", Toast.LENGTH_LONG).show();
        bp.consumePurchase("android.test.purchased");

        storage.addGems(50);
        gems.setText(storage.getTotalGems() + "");
    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        Toast.makeText(this, "Something went wrong with the purchase", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPurchaseHistoryRestored() {
        /*
         * Called when purchase history was restored and the list of all owned PRODUCT ID's
         * was loaded from Google Play
         */
    }

    // endregion
}
