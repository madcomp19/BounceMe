package com.madcomp19gmail.bouncyball;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class BallShop extends AppCompatActivity {

    private final int background_music_id = R.raw.background_music_1;

    private StorageManager storage;
    private MediaPlayerManager mediaPlayerManager;
    TextView coins;
    TextView gems;

    SoundPoolManager soundPool;

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_shop);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        storage = StorageManager.getInstance();
        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(2);
        SwipeAdapterBalls swipeAdapter = new SwipeAdapterBalls(getSupportFragmentManager());
        viewPager.setAdapter(swipeAdapter);
        viewPager.setCurrentItem(storage.getBallPageNumber());

        TabLayout tabLayout = findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);

        mediaPlayerManager = MediaPlayerManager.getInstance();
        coins = findViewById(R.id.coins);
        gems = findViewById(R.id.gems);

        /*if (storageManager.getShopMusicSetting()) {
            mediaPlayerManager.loadSound(background_music_id);
            mediaPlayerManager.play();
        }*/

        soundPool = SoundPoolManager.getInstance();
        //int id = this.getResources().getIdentifier("cash", "raw", getPackageName());
        //if(id != 0)
        //soundPool.loadSound(id);

        if (!storage.getNoAds()) {
            mAdView = findViewById(R.id.bannerAdBallShop);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        coins.setText(storage.getTotalBounces() + "");
        gems.setText(storage.getTotalGems() + "");

        if (storage.getShopMusicSetting()) {
            mediaPlayerManager.loadSound(background_music_id, "Shop");
            mediaPlayerManager.play();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (!this.isFinishing() && storage.getShopMusicSetting())
            mediaPlayerManager.pause();
    }

    public void onClickPlay(View view) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", true);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void onClickBall(View view) {
        String ball = view.getResources().getResourceEntryName(view.getId());
        ball = ball.split("_Label")[0].split("_Button")[0].substring(2);
        int label_id = this.getResources().getIdentifier("b_" + ball + "_Label", "id", getPackageName());
        int temp_id = ((View) view.getParent()).getId();
        String temp_string = view.getResources().getResourceEntryName(temp_id);
        int pageNumber_temp = Integer.parseInt(temp_string.split("_")[0].split("linearLayoutBalls")[1]);

        buyOrSetBall(ball, label_id, pageNumber_temp - 1);
    }

    public void buyOrSetBall(String ball_id, int label_id, int pageNumber) {
        int selectedLabelId = storage.getSelectedBallLabel();
        TextView new_label = findViewById(label_id);
        TextView old_label = findViewById(selectedLabelId);
        storage.setBallPageNumber(pageNumber);

        //if the ball is already owned, set the label as "Selected"
        if (storage.getOwnedBalls().contains(ball_id)) {
            //save the new selected ball id
            storage.setSelectedBall(ball_id);

            //set the previous selected ball label as "Owned"
            if (old_label != null) {
                old_label.setText("Owned");
                old_label.setCompoundDrawables(null, null, null, null);
                old_label.setPadding(0, 0, 0, 0);
            }

            //save the new selected label id
            storage.setSelectedBallLabel(label_id);

            //set the new label as "Selected"
            //new_label.setText("Selected");
            new_label.setText("");
            new_label.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.selected_icon_vector, 0, 0);
            new_label.setPadding(0, 10, 0, 0);

        } else { //if the ball is not owned
            int price = Integer.parseInt(new_label.getText() + ""); //get price
            int total_touches = storage.getTotalBounces(); //get money
            int total_gems = storage.getTotalGems();

            if(ball_id.contains("special"))
            {
                if (total_gems >= price) {
                    storage.takeGems(price);
                    storage.addOwnedBall(ball_id);
                    storage.addOwnedBallLabel(label_id);
                    storage.setSelectedBall(ball_id);
                    storage.setSelectedBallLabel(label_id);

                    //if (storageManager.getSelectedBallLabel() != 0)
                    //  ((TextView) findViewById(storageManager.getSelectedBallLabel())).setText("Owned");
                    if (old_label != null) {
                        old_label.setText("Owned");
                        old_label.setCompoundDrawables(null, null, null, null);
                        old_label.setPadding(0, 0, 0, 0);
                    }

                    //new_label.setText("Selected");
                    new_label.setText("");
                    new_label.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.selected_icon_vector, 0, 0);
                    new_label.setPadding(0, 10, 0, 0);
                    Toast.makeText(this, "Unlocked", Toast.LENGTH_SHORT).show();
                    gems.setText(storage.getTotalGems() + "");

                    SoundPoolManager.getInstance().playSound();
                } else {
                    BuyGemsDialog dialog = new BuyGemsDialog(this);
                    dialog.Show();
                }
            }
            else
            {
                if (total_touches >= price) {
                    storage.setTotalBounces(total_touches - price);
                    storage.addOwnedBall(ball_id);
                    storage.addOwnedBallLabel(label_id);
                    storage.setSelectedBall(ball_id);
                    storage.setSelectedBallLabel(label_id);

                    //if (storageManager.getSelectedBallLabel() != 0)
                    //  ((TextView) findViewById(storageManager.getSelectedBallLabel())).setText("Owned");
                    if (old_label != null) {
                        old_label.setText("Owned");
                        old_label.setCompoundDrawables(null, null, null, null);
                        old_label.setPadding(0, 0, 0, 0);
                    }

                    //new_label.setText("Selected");
                    new_label.setText("");
                    new_label.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.selected_icon_vector, 0, 0);
                    new_label.setPadding(0, 10, 0, 0);
                    Toast.makeText(this, "Unlocked", Toast.LENGTH_SHORT).show();
                    coins.setText(storage.getTotalBounces() + "");

                    SoundPoolManager.getInstance().playSound();
                } else {
                    BuyCoinsDialog dialog = new BuyCoinsDialog(this);
                    dialog.Show();
                }
                //Toast.makeText(this, "You need " + (price - total_touches) + " more Bounces", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void buyCoins(View view) {
        BuyCoinsDialog dialog = new BuyCoinsDialog(this);
        dialog.Show();
    }

    public void buyGems(View view) {
        BuyGemsDialog dialog = new BuyGemsDialog(this);
        dialog.Show();
    }
}
