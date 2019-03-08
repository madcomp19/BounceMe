package com.madcomp19gmail.bouncyball;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class SkinShop extends FragmentActivity {

    private final int background_music_id = R.raw.background_music_1;

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

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(1);
        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipeAdapter);
        viewPager.setCurrentItem(0);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);

        storageManager = StorageManager.getInstance();
        mediaPlayerManager = MediaPlayerManager.getInstance();
        coins = findViewById(R.id.coins);
        gems = findViewById(R.id.gems);

        if(storageManager.getMusicSetting())
        {
            mediaPlayerManager.loadSound(background_music_id);
            mediaPlayerManager.play();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        coins.setText(storageManager.getTotalTouches() + "");
        gems.setText(storageManager.getTotalGems() + "");

        if(storageManager.getMusicSetting())
        {
            mediaPlayerManager.loadSound(background_music_id);
            mediaPlayerManager.play();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(storageManager.getMusicSetting())
            mediaPlayerManager.pause();
    }

    public void onClickSkin(View view) {

        String view_id = view.getResources().getResourceEntryName(view.getId());
        view_id = view_id.split("_Label")[0].split("_Button")[0];
        //Log.i("TEST", view_id);

        int skin_id = this.getResources().getIdentifier(view_id, "drawable", getPackageName());
        int label_id = this.getResources().getIdentifier(view_id + "_Label", "id", getPackageName());
        //region
        /*Log.i("skin", view_id);
        Log.i("label", view_id + "_Label");
        Log.i("skin_id", Integer.toString(skin_id));
        Log.i("label_id", Integer.toString(label_id));
        Log.i("res_id", Integer.toString(view.getId()));*/
        //endregion

        buyOrSetSkin(skin_id, label_id);
    }

    public void buyOrSetSkin(int skin_id, int label_id) {
        StorageManager storageManager = StorageManager.getInstance();

        if (storageManager.getOwnedSkins().contains(skin_id)) {
            storageManager.setSelectedSkin(skin_id);

            ((TextView) findViewById(storageManager.getSelectedSkinLabel())).setText("Owned");

            storageManager.setSelectedSkinLabel(label_id);

            ((TextView) findViewById(label_id)).setText("Selected");
        } else {
            TextView label_text = (TextView) findViewById(label_id);
            int price = Integer.parseInt(label_text.getText() + "");
            int total_touches = storageManager.getTotalTouches();

            if (total_touches >= price) {
                storageManager.setTotalTouches(total_touches - price);
                storageManager.addOwnedSkin(skin_id);
                storageManager.setSelectedSkin(skin_id);
                storageManager.addOwnedSkinLabel(label_id);

                if (storageManager.getSelectedSkinLabel() != 0)
                    ((TextView) findViewById(storageManager.getSelectedSkinLabel())).setText("Owned");

                storageManager.setSelectedSkinLabel(label_id);

                label_text.setText("Selected");
                Toast.makeText(this, "Unlocked", Toast.LENGTH_LONG).show();
                coins.setText(storageManager.getTotalTouches() + "");
            } else
                Toast.makeText(this, "You need " + (price - total_touches) + " more touches", Toast.LENGTH_LONG).show();
        }
    }
}

