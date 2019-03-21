package com.madcomp19gmail.bouncyball;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

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
        switch (view.getId()) {
            case R.id.x2_Button:
            case R.id.x2_Label:
                storageManager.setActiveBoost(2);
                break;

            case R.id.x5_Button:
            case R.id.x5_Label:
                storageManager.setActiveBoost(5);
                break;

            case R.id.x10_Button:
            case R.id.x10_Label:
                storageManager.setActiveBoost(10);
                break;

            case R.id.x20_Button:
            case R.id.x20_Label:
                storageManager.setActiveBoost(20);
                break;
            case R.id.x50_Button:
            case R.id.x50_Label:
                storageManager.setActiveBoost(50);
                break;

            case R.id.x100_Button:
            case R.id.x100_Label:
                storageManager.setActiveBoost(100);
                break;
            case R.id.x200_Button:
            case R.id.x200_Label:
                storageManager.setActiveBoost(200);
                break;

            case R.id.x500_Button:
            case R.id.x500_Label:
                storageManager.setActiveBoost(500);
                break;

            case R.id.x501_Button:
            case R.id.x501_Label:
                storageManager.setActiveBoost(500);
                break;

            case R.id.x502_Button:
            case R.id.x502_Label:
                storageManager.setActiveBoost(1);
                break;
        }
    }
}
