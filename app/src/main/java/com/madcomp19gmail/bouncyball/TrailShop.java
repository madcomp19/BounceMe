package com.madcomp19gmail.bouncyball;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class TrailShop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_shop);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        updateLabels();
    }

    public void onClickTrail(View view)
    {
        switch (view.getId()) {
            case R.id.trail_1_Button:
            case R.id.trail_1_Label:
                buyOrSetTrail(R.drawable.colored_1, R.id.trail_1_Label);
                break;

            case R.id.trail_2_Button:
            case R.id.trail_2_Label:
                buyOrSetTrail(R.drawable.colored_2, R.id.trail_2_Label);
                break;

            case R.id.trail_3_Button:
            case R.id.trail_3_Label:
                buyOrSetTrail(R.drawable.colored_3, R.id.trail_3_Label);
                break;

            case R.id.trail_4_Button:
            case R.id.trail_4_Label:
                buyOrSetTrail(R.drawable.colored_4, R.id.trail_4_Label);
                break;

            case R.id.trail_5_Button:
            case R.id.trail_5_Label:
                buyOrSetTrail(R.drawable.colored_5, R.id.trail_5_Label);
                break;

            case R.id.trail_6_Button:
            case R.id.trail_6_Label:
                buyOrSetTrail(R.drawable.colored_6, R.id.trail_6_Label);
                break;

            case R.id.trail_7_Button:
            case R.id.trail_7_Label:
                buyOrSetTrail(R.drawable.colored_7, R.id.trail_7_Label);
                break;

            case R.id.trail_8_Button:
            case R.id.trail_8_Label:
                buyOrSetTrail(R.drawable.colored_8, R.id.trail_8_Label);
                break;

            case R.id.trail_9_Button:
            case R.id.trail_9_Label:
                buyOrSetTrail(R.drawable.colored_9, R.id.trail_9_Label);
                break;

            case R.id.trail_10_Button:
            case R.id.trail_10_Label:
                buyOrSetTrail(R.drawable.colored_10, R.id.trail_10_Label);
                break;

            case R.id.trail_11_Button:
            case R.id.trail_11_Label:
                buyOrSetTrail(R.drawable.colored_11, R.id.trail_11_Label);
                break;

            case R.id.trail_12_Button:
            case R.id.trail_12_Label:
                buyOrSetTrail(R.drawable.colored_12, R.id.trail_12_Label);
                break;

            case R.id.trail_13_Button:
            case R.id.trail_13_Label:
                buyOrSetTrail(R.drawable.colored_13, R.id.trail_13_Label);
                break;

            case R.id.trail_14_Button:
            case R.id.trail_14_Label:
                buyOrSetTrail(R.drawable.colored_14, R.id.trail_14_Label);
                break;

            case R.id.trail_15_Button:
            case R.id.trail_15_Label:
                buyOrSetTrail(R.drawable.colored_15, R.id.trail_15_Label);
                break;

            case R.id.trail_16_Button:
            case R.id.trail_16_Label:
                buyOrSetTrail(R.drawable.colored_16, R.id.trail_16_Label);
                break;

            case R.id.trail_17_Button:
            case R.id.trail_17_Label:
                buyOrSetTrail(R.drawable.colored_17, R.id.trail_17_Label);
                break;

            case R.id.trail_18_Button:
            case R.id.trail_18_Label:
                buyOrSetTrail(R.drawable.colored_18, R.id.trail_18_Label);
                break;

            case R.id.trail_19_Button:
            case R.id.trail_19_Label:
                buyOrSetTrail(R.drawable.colored_19, R.id.trail_19_Label);
                break;

            case R.id.trail_20_Button:
            case R.id.trail_20_Label:
                buyOrSetTrail(R.drawable.colored_20, R.id.trail_20_Label);
                break;

            case R.id.trail_21_Button:
            case R.id.trail_21_Label:
                buyOrSetTrail(R.drawable.colored_21, R.id.trail_21_Label);
                break;

            case R.id.trail_22_Button:
            case R.id.trail_22_Label:
                buyOrSetTrail(R.drawable.rainbow, R.id.trail_22_Label);
                break;

            case R.id.trail_23_Button:
            case R.id.trail_23_Label:
                buyOrSetTrail(R.drawable.spectrum, R.id.trail_23_Label);
                break;

            case R.id.trail_24_Button:
            case R.id.trail_24_Label:
                buyOrSetTrail(R.drawable.reactive, R.id.trail_24_Label);
                break;

            case R.id.trail_25_Button:
            case R.id.trail_25_Label:
                buyOrSetTrail(R.drawable.clear, R.id.trail_25_Label);
                break;
        }
    }

    private void buyOrSetTrail(int trail, int label)
    {
        /*StorageManager storageManager = StorageManager.getInstance();

        if (storageManager.getOwnedTrails().contains(trail)) {
            storageManager.setSelectedTrail(trail);

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
        }*/
    }

    private void updateLabels()
    {

    }
}
