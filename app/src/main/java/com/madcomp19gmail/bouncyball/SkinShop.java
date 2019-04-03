package com.madcomp19gmail.bouncyball;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SkinShop extends FragmentActivity {

    private TextView coins;
    private TextView gems;
    private StorageManager storageManager;
    private MediaPlayerManager mediaPlayerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_shop);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        storageManager = StorageManager.getInstance();
        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(2);
        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipeAdapter);
        viewPager.setCurrentItem(storageManager.getSkinPageNumber());

        TabLayout tabLayout = findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);

        mediaPlayerManager = MediaPlayerManager.getInstance();
        coins = findViewById(R.id.coins);
        gems = findViewById(R.id.gems);

        /*if (storageManager.getShopMusicSetting()) {
            mediaPlayerManager.loadSound(background_music_id);
            mediaPlayerManager.play();
        }*/

        if(!storageManager.getNoAds()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            ((AdView) findViewById(R.id.bannerAdTrailShop)).loadAd(adRequest);
        }
    }

    public void onClickPlay(View view) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", true);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    protected void onResume() {
        int background_music_id = R.raw.background_music_1;
        super.onResume();

        String temp1 = storageManager.getTotalBounces() + "";
        String temp2 = storageManager.getTotalGems() + "";

        coins.setText(temp1);
        gems.setText(temp2);

        if (storageManager.getShopMusicSetting()) {
            mediaPlayerManager.loadSound(background_music_id, "Shop");
            mediaPlayerManager.play();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (!this.isFinishing() && storageManager.getShopMusicSetting())
            mediaPlayerManager.pause();
    }

    public void onClickSkin(View view) {
        String view_id = view.getResources().getResourceEntryName(view.getId());
        view_id = view_id.split("_Label")[0].split("_Button")[0];
        //Log.i("TEST", view_id);

        int skin_id = this.getResources().getIdentifier(view_id, "drawable", getPackageName());
        int label_id = this.getResources().getIdentifier(view_id + "_Label", "id", getPackageName());
        int temp_id = ((View) view.getParent()).getId();
        String temp_string = view.getResources().getResourceEntryName(temp_id);
        int pageNumber_temp = Integer.parseInt(temp_string.split("_")[0].split("linearLayoutSkins")[1]);


        //region
        /*Log.i("skin", view_id);
        Log.i("label", view_id + "_Label");
        Log.i("skin_id", Integer.toString(skin_id));
        Log.i("label_id", Integer.toString(label_id));
        Log.i("res_id", Integer.toString(view.getId()));*/
        //endregion

        buyOrSetSkin(skin_id, label_id, pageNumber_temp - 1);
    }

    public void buyOrSetSkin(int skin_id, int label_id, int pageNumber) {
        StorageManager storageManager = StorageManager.getInstance();
        int selectedLabelId = storageManager.getSelectedSkinLabel();
        TextView new_label = findViewById(label_id);
        TextView old_label = findViewById(selectedLabelId);
        storageManager.setSkinPageNumber(pageNumber);

        //if the skin is already owned, set the label as "Selected"
        if (storageManager.getOwnedSkins().contains(skin_id)) {
            //save the new selected skin id
            storageManager.setSelectedSkin(skin_id);

            //set the previous selected skin label as "Owned"
            if (old_label != null) {
                old_label.setText("Owned");
                old_label.setCompoundDrawables(null, null, null, null);
                old_label.setPadding(0, 0, 0, 0);
            }

            //save the new selected label id
            storageManager.setSelectedSkinLabel(label_id);

            //set the new label as "Selected"
            //new_label.setText("Selected");
            new_label.setText("");
            new_label.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.selected_icon_vector, 0, 0);
            new_label.setPadding(0, 10, 0, 0);

        } else { //if the skin is not owned
            int price = Integer.parseInt(new_label.getText() + ""); //get price
            int total_touches = storageManager.getTotalBounces(); //get money

            if (total_touches >= price) {
                storageManager.setTotalBounces(total_touches - price);
                storageManager.addOwnedSkin(skin_id);
                storageManager.addOwnedSkinLabel(label_id);
                storageManager.setSelectedSkin(skin_id);
                storageManager.setSelectedSkinLabel(label_id);

                //if (storageManager.getSelectedSkinLabel() != 0)
                //  ((TextView) findViewById(storageManager.getSelectedSkinLabel())).setText("Owned");
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
                coins.setText(storageManager.getTotalBounces() + "");

                SoundPoolManager.getInstance().playSound();
            } else
            {
                BuyCoinsDialog dialog = new BuyCoinsDialog(this);
                dialog.Show();
            }
                //Toast.makeText(this, "You need " + (price - total_touches) + " more Bounces", Toast.LENGTH_SHORT).show();
        }
    }

    public void buyCoins(View view)
    {
        BuyCoinsDialog dialog = new BuyCoinsDialog(this);
        dialog.Show();
    }

    public void buyGems(View view)
    {
        BuyGemsDialog dialog = new BuyGemsDialog(this);
        dialog.Show();
    }
}

