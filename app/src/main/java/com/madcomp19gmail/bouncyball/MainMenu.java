package com.madcomp19gmail.bouncyball;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.lang.reflect.Field;

public class MainMenu extends AppCompatActivity implements RewardedVideoAdListener {

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
    private boolean watchedDoublePointsAd = false;
    private boolean isWatchingDoublePointsAd = false;

    private Dialog coinDialog;
    private Dialog gemDialog;
    private Dialog doublePointsDialog;

    private DisplayMetrics metrics;
    private int width, height;

    private final int default_skin = R.drawable.emoji_0;
    private final int default_skin_label = R.id.emoji_0_Label;
    private final int default_sound = 0;
    private final int default_sound_label = R.id.mute_Label;
    private final int default_trail = -1;
    private final int default_trail_label = R.id.clear_Label;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        MobileAds.initialize(this, "ca-app-pub-5557351606937995~8015272303");

        ImageView iv = (ImageView) findViewById(R.id.bounce);

        Glide.with(this).load(R.drawable.bounce).into(iv);

        StorageManager.initialize(this);
        storage = StorageManager.getInstance();
        coins = findViewById(R.id.coins);
        gems = findViewById(R.id.gems);
        adButton = findViewById(R.id.adButton);
        adsLeftToday = findViewById(R.id.adsLeftToday);
        //touches = StorageManager.getInstance().getTotalBounces();*/

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
        } else
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

        coinDialog = new Dialog(this);
        gemDialog = new Dialog(this);
        doublePointsDialog = new Dialog(this);

        metrics = getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
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
        //StorageManager.getInstance().setTotalBounces(touches);

        if (prev_act_GameWorld) {
            int new_touches = Math.abs(StorageManager.getInstance().getTotalBounces() - prev_touches);

            if (new_touches > 300) {
                showDoublePointsDialog();
            }

            prev_act_GameWorld = false;
        }

        coins.setText(storage.getTotalBounces() + "");
        gems.setText(storage.getTotalGems() + "");

        if(!storage.getLastAdDateString().equals(storage.getTodayDateString()))
        {
            storage.resetAdAvailableToday();
        }

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
        } else
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

        if (adsLeft || watchedDoublePointsAd) {
            loadRewardedVideoAd();
            watchedDoublePointsAd = false;
            isWatchingDoublePointsAd = false;
        }

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
            watchedDoublePointsAd = true;
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


    //region
    public void buyEverything(View v) {
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
        TextView txtClose;

        gemDialog.setContentView(R.layout.buy_gems_popup);

        txtClose = (TextView) gemDialog.findViewById(R.id.closeButtonGemsPopup);

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gemDialog.dismiss();
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
}
