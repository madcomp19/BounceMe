package com.madcomp19gmail.bouncyball;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Calendar;

import static java.lang.Integer.parseInt;

public class BoostShop extends AppCompatActivity {

    private AdView mAdView;
    StorageManager storageManager;
    TextView coins;
    TextView gems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boost_shop);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        storageManager = StorageManager.getInstance();
        coins = findViewById(R.id.coins);
        gems = findViewById(R.id.gems);

        coins.setText(storageManager.getTotalTouches() + "");
        gems.setText(storageManager.getTotalGems() + "");

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

        String label = view_id + "_Label";
        int id = getResources().getIdentifier(label, "id", getApplicationContext().getPackageName());
        TextView label_text = findViewById(id);
        if(label_text.getText() == "")
            return;
        int price = Integer.parseInt(label_text.getText() + "");
        int total_gems = storageManager.getTotalGems();
        if(total_gems > price)
        {
            storageManager.takeGems(price);
            gems.setText(storageManager.getTotalGems() + "");
            storageManager.setActiveBoost(boost);
            storageManager.setActiveBoostTime(Calendar.getInstance().getTimeInMillis() + time * 60000);
            Toast.makeText(this, "Boost is now active", Toast.LENGTH_SHORT).show();
            label_text.setText("");
            label_text.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.selected_icon_vector, 0, 0);
            label_text.setPadding(0,10,0,0);
        }
        else
            Toast.makeText(this, "You need " + (price - total_gems) + " more Gems!", Toast.LENGTH_LONG).show();


    }
}
