package com.madcomp19gmail.bouncyball;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class BackgroundShop extends AppCompatActivity implements BillingProcessor.IBillingHandler{

    private final int background_music_id = R.raw.background_music_1;

    private StorageManager storage;
    private MediaPlayerManager mediaPlayerManager;
    TextView coins;
    TextView gems;

    SoundPoolManager soundPool;

    private AdView mAdView;

    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_shop);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        storage = StorageManager.getInstance();
        mediaPlayerManager = MediaPlayerManager.getInstance();
        coins = findViewById(R.id.coins);
        gems = findViewById(R.id.gems);

        soundPool = SoundPoolManager.getInstance();
        int id = this.getResources().getIdentifier("cash", "raw", getPackageName());

        if(!storage.getNoAds()) {
            mAdView = findViewById(R.id.bannerAdTrailShop);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }

        initializeImageButtons(this.findViewById(R.id.background_shop_ConstraintLayout));

        setOwnedBackgroundsPage(this.findViewById(android.R.id.content));

        bp = new BillingProcessor(this, MainMenu.KEY, this);
        bp.initialize();
    }

    private void initializeImageButtons(View view)
    {
        ViewGroup viewGroup = (ViewGroup) view;

        int childCount = viewGroup.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View v = viewGroup.getChildAt(i);

            if (v instanceof ImageButton) {

                if(v.getId() == R.id.play_icon_vector)
                    continue;

                ImageButton imageButton = (ImageButton) v;

                String id = getResources().getResourceName(imageButton.getId()).split("/")[1].replace("_Button", "");

                int image_id = getResources().getIdentifier(id, "drawable", getPackageName());

                //Glide.with(this).load(image_id).fitCenter().into(imageButton);
                Glide.with(this).load(image_id).centerCrop().into(imageButton);
            }
            else if(v instanceof ImageView)
            {
                ImageView imageView = (ImageView) v;

                String id = getResources().getResourceName(imageView.getId()).split("/")[1];

                if(id.equals("gem_icon") || id.equals("add_gems") || id.equals("add_coins"))
                    continue;

                int image_id = getResources().getIdentifier(id, "drawable", getPackageName());

                Glide.with(this).load(image_id).into(imageView);
            }
            else if (v instanceof ViewGroup)
                initializeImageButtons((ViewGroup) v);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

        coins.setText(storage.getTotalBounces() + "");
        gems.setText(storage.getTotalGems() + "");

        if(storage.getShopMusicSetting())
        {
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

        if(!this.isFinishing() && storage.getShopMusicSetting())
            mediaPlayerManager.pause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onClickPlay(View view){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", true);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void onClickBackground(View view)
    {
        String view_id = view.getResources().getResourceEntryName(view.getId());
        view_id = view_id.split("_Label")[0].split("_Button")[0];

        int background_id = this.getResources().getIdentifier(view_id, "drawable", getPackageName());

        int label_id = this.getResources().getIdentifier(view_id + "_Label", "id", getPackageName());

        buyOrSetBackground(background_id, label_id);
    }

    private void buyOrSetBackground(int aBackground, int label_id)
    {
        int background = aBackground;

        StorageManager storageManager = StorageManager.getInstance();

        if (storageManager.getOwnedBackgrounds().contains(background)) {
            storageManager.setSelectedBackground(background);

            ((TextView) findViewById(storageManager.getSelectedBackgroundLabel())).setText("Owned");
            ((TextView) findViewById(storageManager.getSelectedBackgroundLabel())).setCompoundDrawables(null,null,null,null);
            ((TextView) findViewById(storageManager.getSelectedBackgroundLabel())).setPadding(0,0,0,0);

            storageManager.setSelectedBackgroundLabel(label_id);

            ((TextView) findViewById(label_id)).setText("");
            ((TextView) findViewById(label_id)).setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon_vector, 0, 0);
            ((TextView) findViewById(label_id)).setPadding(0,10,0,0);
        } else {
            TextView label_text = (TextView) findViewById(label_id);
            int price = Integer.parseInt(label_text.getText() + "");
            int total_touches = storageManager.getTotalBounces();
            int total_gems = storageManager.getTotalGems();

            if(aBackground == R.id.background_36_Button || label_id == R.id.background_36_Label
                    || aBackground == R.id.background_37_Button || label_id == R.id.background_37_Label
                    || aBackground == R.id.background_38_Button || label_id == R.id.background_38_Label
                    || aBackground == R.id.background_39_Button || label_id == R.id.background_39_Label
                    || aBackground == R.id.background_40_Button || label_id == R.id.background_40_Label)
            {
                if (total_gems >= price) {
                    storageManager.takeGems(price);
                    storageManager.addOwnedBackground(background);
                    storageManager.setSelectedBackground(background);
                    storageManager.addOwnedBackgroundLabel(label_id);

                    if (storageManager.getSelectedBackgroundLabel() != 0)
                    {
                        ((TextView) findViewById(storageManager.getSelectedBackgroundLabel())).setText("Owned");
                        ((TextView) findViewById(storageManager.getSelectedBackgroundLabel())).setCompoundDrawables(null,null,null,null);
                        ((TextView) findViewById(storageManager.getSelectedBackgroundLabel())).setPadding(0,0,0,0);
                    }

                    storageManager.setSelectedBackgroundLabel(label_id);

                    label_text.setText("");
                    label_text.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon_vector, 0, 0);
                    label_text.setPadding(0,10,0,0);
                    Toast.makeText(this, "Unlocked", Toast.LENGTH_SHORT).show();
                    gems.setText(storageManager.getTotalGems() + "");

                    SoundPoolManager.getInstance().playSound();
                } else
                {
                    BuyGemsDialog dialog = new BuyGemsDialog(this);
                    dialog.Show();
                }
                    //Toast.makeText(this, "You need " + (price - total_gems) + " more Gems", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (total_touches >= price) {
                    //soundPool.playSound();
                    storageManager.setTotalBounces(total_touches - price);
                    storageManager.addOwnedBackground(background);
                    storageManager.setSelectedBackground(background);
                    storageManager.addOwnedBackgroundLabel(label_id);

                    if (storageManager.getSelectedBackgroundLabel() != 0)
                    {
                        ((TextView) findViewById(storageManager.getSelectedBackgroundLabel())).setText("Owned");
                        ((TextView) findViewById(storageManager.getSelectedBackgroundLabel())).setCompoundDrawables(null,null,null,null);
                        ((TextView) findViewById(storageManager.getSelectedBackgroundLabel())).setPadding(0,0,0,0);
                    }

                    storageManager.setSelectedBackgroundLabel(label_id);

                    label_text.setText("");
                    label_text.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon_vector, 0, 0);
                    label_text.setPadding(0,10,0,0);
                    Toast.makeText(this, "Unlocked", Toast.LENGTH_SHORT).show();
                    coins.setText(storageManager.getTotalBounces() + "");

                    SoundPoolManager.getInstance().playSound();
                } else
                {
                    BuyCoinsDialog dialog = new BuyCoinsDialog(this);
                    dialog.Show();
                }
                    //Toast.makeText(this, "You need " + (price - total_touches) + " more Bounces", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setOwnedBackgroundsPage(View view)
    {
        StorageManager storageManager = StorageManager.getInstance();

        ArrayList<Integer> label_ids = storageManager.getOwnedBackgroundsLabels();

        ViewGroup viewGroup = (ViewGroup) view;

        int childCount = viewGroup.getChildCount();

        for(int i = 0; i < childCount; i++)
        {
            View v = viewGroup.getChildAt(i);

            if(v instanceof TextView)
            {
                if(label_ids.contains(v.getId()))
                {
                    ((TextView) v).setText("Owned");
                    ((TextView) v).setCompoundDrawables(null,null,null,null);
                    ((TextView) v).setPadding(0,0,0,0);
                }

                if (storageManager.getSelectedBackgroundLabel() == v.getId())
                {
                    ((TextView) v).setText("");
                    ((TextView) v).setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon_vector, 0, 0);
                    v.setPadding(0,10,0,0);
                }
            }
            else if(v instanceof ViewGroup)
                setOwnedBackgroundsPage(v);
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
            storage.addGems(50);

        if(productId.equals("gem_pack_2"))
            storage.addGems(200);

        if(productId.equals("gem_pack_3"))
            storage.addGems(500);

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
