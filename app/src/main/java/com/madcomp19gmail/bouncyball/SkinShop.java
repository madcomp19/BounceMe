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

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SkinShop extends FragmentActivity implements BillingProcessor.IBillingHandler{

    private TextView coins;
    private TextView gems;
    private StorageManager storageManager;
    private MediaPlayerManager mediaPlayerManager;

    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_shop);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        storageManager = StorageManager.getInstance();
        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(2);
        SwipeAdapterSkins swipeAdapter = new SwipeAdapterSkins(getSupportFragmentManager());
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

        if (!storageManager.getNoAds()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            ((AdView) findViewById(R.id.bannerAdTrailShop)).loadAd(adRequest);
        }

        bp = new BillingProcessor(this, MainMenu.KEY, this);
        bp.initialize();
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
    protected void onDestroy() {

        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (!this.isFinishing() && storageManager.getShopMusicSetting())
            mediaPlayerManager.pause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onClickSkin(View view) {
        String view_id = view.getResources().getResourceEntryName(view.getId());
        view_id = view_id.split("_Label")[0].split("_Button")[0];

        int skin_id = this.getResources().getIdentifier(view_id, "drawable", getPackageName());
        int label_id = this.getResources().getIdentifier(view_id + "_Label", "id", getPackageName());
        int temp_id = ((View) view.getParent()).getId();
        String temp_string = view.getResources().getResourceEntryName(temp_id);
        int pageNumber_temp = Integer.parseInt(temp_string.split("_")[0].split("linearLayoutSkins")[1]);

        buyOrSetSkin(skin_id, label_id, pageNumber_temp - 1);
    }

    public void buyOrSetSkin(int skin_id, int label_id, int pageNumber) {
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
            } else {
                BuyCoinsDialog dialog = new BuyCoinsDialog(this);
                dialog.Show();
            }
            //Toast.makeText(this, "You need " + (price - total_touches) + " more Bounces", Toast.LENGTH_SHORT).show();
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

        if(productId.equals("gem_pack_1"))
            storageManager.addGems(50);

        if(productId.equals("gem_pack_2"))
            storageManager.addGems(200);

        if(productId.equals("gem_pack_3"))
            storageManager.addGems(500);

        Toast.makeText(this, "Purchase Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        Toast.makeText(this, "Something went wrong with the purchase", Toast.LENGTH_SHORT).show();
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

