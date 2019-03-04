package com.madcomp19gmail.bouncyball;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class SkinShop extends FragmentActivity {

    private TextView coins;
    private TextView gems;
    private StorageManager storageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_shop);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(2);
        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipeAdapter);
        viewPager.setCurrentItem(0);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);

        storageManager = StorageManager.getInstance();
        coins = findViewById(R.id.coins);
        gems = findViewById(R.id.gems);
    }

    @Override
    protected void onResume() {
        super.onResume();

        coins.setText(storageManager.getTotalTouches() + "");
        gems.setText(storageManager.getTotalGems() + "");
    }

    public void onClickSkin(View view) {
        switch (view.getId()) {
            case R.id.emoji_1_Button:
            case R.id.emoji_1_Label:
                buyOrSetSkin(R.drawable.emoji_1, R.id.emoji_1_Label);
                break;

            case R.id.emoji_2_Button:
            case R.id.emoji_2_Label:
                buyOrSetSkin(R.drawable.emoji_2, R.id.emoji_2_Label);
                break;

            case R.id.emoji_3_Button:
            case R.id.emoji_3_Label:
                buyOrSetSkin(R.drawable.emoji_3, R.id.emoji_3_Label);
                break;

            case R.id.emoji_4_Button:
            case R.id.emoji_4_Label:
                buyOrSetSkin(R.drawable.emoji_4, R.id.emoji_4_Label);
                break;

            case R.id.emoji_5_Button:
            case R.id.emoji_5_Label:
                buyOrSetSkin(R.drawable.emoji_5, R.id.emoji_5_Label);
                break;

            case R.id.emoji_6_Button:
            case R.id.emoji_6_Label:
                buyOrSetSkin(R.drawable.emoji_6, R.id.emoji_6_Label);
                break;

            case R.id.emoji_7_Button:
            case R.id.emoji_7_Label:
                buyOrSetSkin(R.drawable.emoji_7, R.id.emoji_7_Label);
                break;

            case R.id.emoji_8_Button:
            case R.id.emoji_8_Label:
                buyOrSetSkin(R.drawable.emoji_8, R.id.emoji_8_Label);
                break;

            case R.id.emoji_9_Button:
            case R.id.emoji_9_Label:
                buyOrSetSkin(R.drawable.emoji_9, R.id.emoji_9_Label);
                break;

            case R.id.emoji_10_Button:
            case R.id.emoji_10_Label:
                buyOrSetSkin(R.drawable.emoji_10, R.id.emoji_10_Label);
                break;

            /*case R.id.emoji_11_Button:
            case R.id.emoji_11_Label:
                buyorsetskin(R.drawable.emoji_11); break;
            case R.id.emoji_12_Button:
            case R.id.emoji_12_Label:
                buyorsetskin(R.drawable.emoji_12); break;
            case R.id.emoji_13_Button:
            case R.id.emoji_13_Label:
                buyorsetskin(R.drawable.emoji_13); break;
            case R.id.emoji_14_Button:
            case R.id.emoji_14_Label:
                buyorsetskin(R.drawable.emoji_14); break;
            case R.id.emoji_15_Button:
            case R.id.emoji_15_Label:
                buyorsetskin(R.drawable.emoji_15); break;
            case R.id.emoji_16_Button:
            case R.id.emoji_16_Label:
                buyorsetskin(R.drawable.emoji_16); break;
            case R.id.emoji_17_Button:
            case R.id.emoji_17_Label:
                buyorsetskin(R.drawable.emoji_17); break;
            case R.id.emoji_18_Button:
            case R.id.emoji_18_Label:
                buyorsetskin(R.drawable.emoji_18); break;
            case R.id.emoji_19_Button:
            case R.id.emoji_19_Label:
                buyorsetskin(R.drawable.emoji_19); break;
            case R.id.emoji_20_Button:
            case R.id.emoji_20_Label:
                buyorsetskin(R.drawable.emoji_20); break;
            case R.id.emoji_21_Button:
            case R.id.emoji_21_Label:
                buyorsetskin(R.drawable.emoji_21); break;
            case R.id.emoji_22_Button:
            case R.id.emoji_22_Label:
                buyorsetskin(R.drawable.emoji_22); break;
            case R.id.emoji_23_Button:
            case R.id.emoji_23_Label:
                buyorsetskin(R.drawable.emoji_23); break;
            case R.id.emoji_24_Button:
            case R.id.emoji_24_Label:
                buyorsetskin(R.drawable.emoji_24); break;
            case R.id.emoji_25_Button:
            case R.id.emoji_25_Label:
                buyorsetskin(R.drawable.emoji_25); break;*/
        }
    }

    public void buyOrSetSkin(int skin_id, int label_id) {
        StorageManager storageManager = StorageManager.getInstance();

        if (storageManager.getOwnedSkins().contains(skin_id)) {
            storageManager.setSelectedSkin(skin_id);

            ((TextView) findViewById(storageManager.getSelectedLabel())).setText("Owned");

            storageManager.setSelectedLabel(label_id);

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

                if (storageManager.getSelectedLabel() != 0)
                    ((TextView) findViewById(storageManager.getSelectedLabel())).setText("Owned");

                storageManager.setSelectedLabel(label_id);

                label_text.setText("Selected");
                Toast.makeText(this, "Unlocked", Toast.LENGTH_LONG).show();
                coins.setText(storageManager.getTotalTouches() + "");
            } else
                Toast.makeText(this, "You need " + (price - total_touches) + " more touches", Toast.LENGTH_LONG).show();
        }
    }
}

