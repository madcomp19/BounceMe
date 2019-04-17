package com.madcomp19gmail.bouncyball;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class BuyCoinsDialog {

    private Dialog coinDialog;

    StorageManager storage;

    TextView coins;
    TextView gems;

    public BuyCoinsDialog(final Context context)
    {
        storage = StorageManager.getInstance();
        coinDialog = new Dialog(context);

        coins = (TextView) ((Activity) context).findViewById(R.id.coins);
        gems = (TextView) ((Activity) context).findViewById(R.id.gems);

        coinDialog.setContentView(R.layout.buy_coins_popup);

        TextView txtClose = (TextView) coinDialog.findViewById(R.id.closeButtonCoinsPopup);
        final TextView buyCoinPack1 = (TextView) coinDialog.findViewById(R.id.buyCoin1);
        final TextView buyCoinPack2 = (TextView) coinDialog.findViewById(R.id.buyCoin2);
        final TextView buyCoinPack3 = (TextView) coinDialog.findViewById(R.id.buyCoin3);

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coinDialog.dismiss();
            }
        });

        buyCoinPack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String text = buyCoinPack1.getText().toString();
                int price = Integer.valueOf(buyCoinPack1.getText().toString());
                int total_gems = storage.getTotalGems();

                if (total_gems >= price) {
                    buyCoinPack1.setEnabled(false);
                    buyCoinPack1.setCompoundDrawables(null, null, null, null);
                    buyCoinPack1.setPadding(0, 0, 0, 0);
                    buyCoinPack1.setText("Bought");
                    storage.takeGems(price);
                    storage.setTotalBounces(storage.getTotalBounces() + 10000);
                    coins.setText(storage.getTotalBounces() + "");
                    gems.setText(storage.getTotalGems() + "");

                    SoundPoolManager.getInstance().playSound();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            buyCoinPack1.setEnabled(true);
                            buyCoinPack1.setText(text);
                            buyCoinPack1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.gem_icon_small, 0);
                            buyCoinPack1.setPadding(55, 0, 55, 0);
                        }
                    }, 2000);
                } else{
                    BuyGemsDialog dialog = new BuyGemsDialog(context);
                    dialog.Show();
                }
                    //buyGems(getWindow().getDecorView());
            }
        });

        buyCoinPack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String text = buyCoinPack2.getText().toString();
                int price = Integer.valueOf(buyCoinPack2.getText().toString());
                int total_gems = storage.getTotalGems();

                if (total_gems >= price) {
                    buyCoinPack2.setEnabled(false);
                    buyCoinPack2.setCompoundDrawables(null, null, null, null);
                    buyCoinPack2.setPadding(0, 0, 0, 0);
                    buyCoinPack2.setText("Bought");
                    storage.takeGems(price);
                    storage.setTotalBounces(storage.getTotalBounces() + 75000);
                    coins.setText(storage.getTotalBounces() + "");
                    gems.setText(storage.getTotalGems() + "");

                    SoundPoolManager.getInstance().playSound();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            buyCoinPack2.setEnabled(true);
                            buyCoinPack2.setText(text);
                            buyCoinPack2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.gem_icon_small, 0);
                            buyCoinPack2.setPadding(55, 0, 55, 0);
                        }
                    }, 2000);
                } else{
                    BuyGemsDialog dialog = new BuyGemsDialog(context);
                    dialog.Show();
                }
                    //buyGems(getWindow().getDecorView());
            }
        });

        buyCoinPack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String text = buyCoinPack3.getText().toString();
                int price = Integer.valueOf(buyCoinPack3.getText().toString());
                int total_gems = storage.getTotalGems();

                if (total_gems >= price) {
                    buyCoinPack3.setEnabled(false);
                    buyCoinPack3.setCompoundDrawables(null, null, null, null);
                    buyCoinPack3.setPadding(0, 0, 0, 0);
                    buyCoinPack3.setText("Bought");
                    storage.takeGems(price);
                    storage.setTotalBounces(storage.getTotalBounces() + 200000);
                    coins.setText(storage.getTotalBounces() + "");
                    gems.setText(storage.getTotalGems() + "");

                    SoundPoolManager.getInstance().playSound();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            buyCoinPack3.setEnabled(true);
                            buyCoinPack3.setText(text);
                            buyCoinPack3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.gem_icon_small, 0);
                            buyCoinPack3.setPadding(46, 0, 46, 0);
                        }
                    }, 2000);
                } else {
                    BuyGemsDialog dialog = new BuyGemsDialog(context);
                    dialog.Show();
                }
                    //buyGems(getWindow().getDecorView());
            }
        });
    }

    public void Show()
    {
        coinDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        coinDialog.show();

        Window window = coinDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}
