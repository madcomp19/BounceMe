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

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class TrailShop extends AppCompatActivity {

    private final int background_music_id = R.raw.background_music_1;

    private StorageManager storage;
    private MediaPlayerManager mediaPlayerManager;
    TextView coins;
    TextView gems;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_shop);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setOwnedTrailsPage(this.findViewById(android.R.id.content));

        storage = StorageManager.getInstance();
        mediaPlayerManager = MediaPlayerManager.getInstance();
        coins = findViewById(R.id.coins);
        gems = findViewById(R.id.gems);

        /*if(storage.getShopMusicSetting())
        {
            mediaPlayerManager.loadSound(background_music_id);
            mediaPlayerManager.play();
        }*/

        if(!storage.getNoAds()) {
            mAdView = findViewById(R.id.bannerAdTrailShop);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }

        initializeImageButtons(findViewById(R.id.trail_shop_ConstraintLayout));
    }

    private void initializeImageButtons(View view)
    {
        ViewGroup viewGroup = (ViewGroup) view;

        int childCount = viewGroup.getChildCount();

        for(int i = 0; i < childCount; i++)
        {
            View v = viewGroup.getChildAt(i);

            if(v instanceof ImageButton)
            {
                if(v.getId() == R.id.play_icon_vector)
                    continue;

                ImageButton imageButton = (ImageButton) v;

                String id = getResources().getResourceName(imageButton.getId()).split("/")[1].replace("_Button", "");

                int image_id = getResources().getIdentifier(id, "drawable", getPackageName());

                Glide.with(this).load(image_id).fitCenter().into(imageButton);
            }
            else if(v instanceof ImageView)
            {
                ImageView imageView = (ImageView) v;

                String id = getResources().getResourceName(imageView.getId()).split("/")[1];

                if(id == "gem_icon" || id == "add_gems" || id == "add_coins")
                    continue;

                int image_id = getResources().getIdentifier(id, "drawable", getPackageName());

                Glide.with(this).load(image_id).into(imageView);
            }
            else if(v instanceof ViewGroup)
                initializeImageButtons(v);
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
    protected void onPause() {
        super.onPause();

        if(!this.isFinishing() && storage.getShopMusicSetting())
            mediaPlayerManager.pause();
    }

    public void onClickPlay(View view){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", true);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void onClickTrail(View view)
    {
        String view_id = view.getResources().getResourceEntryName(view.getId());
        view_id = view_id.split("_Label")[0].split("_Button")[0];
        //Log.i("TEST", view_id);

        int trail_id = this.getResources().getIdentifier(view_id, "integer", getPackageName());
        int label_id = this.getResources().getIdentifier(view_id + "_Label", "id", getPackageName());

        buyOrSetTrail(trail_id, label_id);
    }

    private void buyOrSetTrail(int aTrail, int label_id)
    {
        int trail = getResources().getInteger(aTrail);

        StorageManager storageManager = StorageManager.getInstance();

        if (storageManager.getOwnedTrails().contains(trail)) {
            storageManager.setSelectedTrail(trail);

            ((TextView) findViewById(storageManager.getSelectedTrailLabel())).setText("Owned");
            ((TextView) findViewById(storageManager.getSelectedTrailLabel())).setCompoundDrawables(null,null,null,null);
            ((TextView) findViewById(storageManager.getSelectedTrailLabel())).setPadding(0,0,0,0);

            storageManager.setSelectedTrailLabel(label_id);

            ((TextView) findViewById(label_id)).setText("");
            ((TextView) findViewById(label_id)).setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon_vector, 0, 0);
            ((TextView) findViewById(label_id)).setPadding(0,10,0,0);
        } else {
            TextView label_text = (TextView) findViewById(label_id);
            int price = Integer.parseInt(label_text.getText() + "");
            int total_touches = storageManager.getTotalBounces();
            int total_gems = storageManager.getTotalGems();

            if(aTrail == R.id.reactive_Button || label_id == R.id.reactive_Label
                    || aTrail == R.id.spectrum_Button || label_id == R.id.spectrum_Label
                    || aTrail == R.id.rainbow_Button || label_id == R.id.rainbow_Label)
            {
                if (total_gems >= price) {
                    storageManager.takeGems(price);
                    storageManager.addOwnedTrail(trail);
                    storageManager.setSelectedTrail(trail);
                    storageManager.addOwnedTrailLabel(label_id);

                    if (storageManager.getSelectedTrailLabel() != 0)
                    {
                        ((TextView) findViewById(storageManager.getSelectedTrailLabel())).setText("Owned");
                        ((TextView) findViewById(storageManager.getSelectedTrailLabel())).setCompoundDrawables(null,null,null,null);
                        ((TextView) findViewById(storageManager.getSelectedTrailLabel())).setPadding(0,0,0,0);
                    }

                    storageManager.setSelectedTrailLabel(label_id);

                    label_text.setText("");
                    label_text.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon_vector, 0, 0);
                    label_text.setPadding(0,10,0,0);
                    Toast.makeText(this, "Unlocked", Toast.LENGTH_LONG).show();
                    gems.setText(storageManager.getTotalGems() + "");

                    SoundPoolManager.getInstance().playSound();
                } else
                {
                    BuyGemsDialog dialog = new BuyGemsDialog(this);
                    dialog.Show();
                }
                    //Toast.makeText(this, "You need " + (price - total_gems) + " more Gems!", Toast.LENGTH_LONG).show();
            }
            else
            {
                if (total_touches >= price) {
                    storageManager.setTotalBounces(total_touches - price);
                    storageManager.addOwnedTrail(trail);
                    storageManager.setSelectedTrail(trail);
                    storageManager.addOwnedTrailLabel(label_id);

                    if (storageManager.getSelectedTrailLabel() != 0)
                    {
                        ((TextView) findViewById(storageManager.getSelectedTrailLabel())).setText("Owned");
                        ((TextView) findViewById(storageManager.getSelectedTrailLabel())).setCompoundDrawables(null,null,null,null);
                        ((TextView) findViewById(storageManager.getSelectedTrailLabel())).setPadding(0,0,0,0);
                    }

                    storageManager.setSelectedTrailLabel(label_id);

                    label_text.setText("");
                    label_text.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon_vector, 0, 0);
                    label_text.setPadding(0,10,0,0);
                    Toast.makeText(this, "Unlocked", Toast.LENGTH_LONG).show();
                    coins.setText(storageManager.getTotalBounces() + "");

                    SoundPoolManager.getInstance().playSound();
                } else
                {
                    BuyCoinsDialog dialog = new BuyCoinsDialog(this);
                    dialog.Show();
                }
                    //Toast.makeText(this, "You need " + (price - total_touches) + " more Bounces!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setOwnedTrailsPage(View view)
    {
        StorageManager storageManager = StorageManager.getInstance();

        ArrayList<Integer> label_ids = storageManager.getOwnedTrailsLabels();

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
                if (storageManager.getSelectedTrailLabel() == v.getId())
                {
                    ((TextView) v).setText("");
                    ((TextView) v).setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon_vector, 0, 0);
                    v.setPadding(0,10,0,0);
                }
            }
            else if(v instanceof ViewGroup)
                setOwnedTrailsPage(v);
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
