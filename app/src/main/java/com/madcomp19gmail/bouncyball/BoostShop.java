package com.madcomp19gmail.bouncyball;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static java.lang.Integer.parseInt;

public class BoostShop extends AppCompatActivity {

    private AdView mAdView;
    StorageManager storageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boost_shop);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        storageManager = StorageManager.getInstance();

        mAdView = findViewById(R.id.bannerAdBoostShop);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void onClickPlay(View view){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", true);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void onClickBoost(View view)
    {
        String view_id = view.getResources().getResourceEntryName(view.getId());
        view_id = view_id.split("_Label")[0].split("_Button")[0];
        int boost = parseInt(view_id.split("_")[0].replace("x", ""));
        int time = parseInt(view_id.split("_")[1].replace("m", ""));

        storageManager.setActiveBoost(boost);
    }
}
