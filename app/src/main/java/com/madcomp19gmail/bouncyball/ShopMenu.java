package com.madcomp19gmail.bouncyball;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class ShopMenu extends AppCompatActivity {

    private final int background_music_id = R.raw.background_music_1;

    private StorageManager storage;
    private MediaPlayerManager mediaPlayerManager;
    TextView coins;
    TextView gems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_menu);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        storage = StorageManager.getInstance();
        mediaPlayerManager = MediaPlayerManager.getInstance();
        coins = findViewById(R.id.coins);
        gems = findViewById(R.id.gems);

        if (storage.getMusicSetting()) {
            mediaPlayerManager.loadSound(background_music_id);
            mediaPlayerManager.play();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        coins.setText(storage.getTotalTouches() + "");
        gems.setText(storage.getTotalGems() + "");

        if (storage.getMusicSetting()) {
            mediaPlayerManager.loadSound(background_music_id);
            mediaPlayerManager.play();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (storage.getMusicSetting())
            mediaPlayerManager.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //if(storage.getMusicSetting())
        //mediaPlayerManager.stop();
    }

    public void onShopMenuButtonClick(View view) {

        if (view.getId() == R.id.skinsButton) {
            Intent intent = new Intent(this, SkinShop.class);
            startActivityForResult(intent, 1);
        }

        if (view.getId() == R.id.trailsButton) {
            Intent intent = new Intent(this, TrailShop.class);
            startActivityForResult(intent, 1);
        }

        if (view.getId() == R.id.soundButton) {
            Intent intent = new Intent(this, SoundShop.class);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                boolean result = data.getBooleanExtra("result", false);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", result);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
