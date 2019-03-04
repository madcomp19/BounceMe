package com.madcomp19gmail.bouncyball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class ShopMenu extends AppCompatActivity {

    private StorageManager storage;
    TextView coins;
    TextView gems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_menu);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        storage = StorageManager.getInstance();
        coins = findViewById(R.id.coins);
        gems = findViewById(R.id.gems);
    }

    @Override
    protected void onResume(){
        super.onResume();

        coins.setText(storage.getTotalTouches() + "");
        gems.setText(storage.getTotalGems() + "");
    }

    public void onShopMenuButtonClick(View view)
    {
        if(view.getId() == R.id.skinsButton)
        {
            Intent intent = new Intent(this, SkinShop.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.trailsButton)
        {
            Intent intent = new Intent(this, TrailShop.class);
            startActivity(intent);
        }
    }
}
