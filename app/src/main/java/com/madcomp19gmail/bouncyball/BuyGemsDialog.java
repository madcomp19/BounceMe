package com.madcomp19gmail.bouncyball;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

public class BuyGemsDialog implements BillingProcessor.IBillingHandler {

    private Dialog gemDialog;

    StorageManager storage;

    TextView gems;

    public BuyGemsDialog(Context context)
    {
        gems = (TextView) ((Activity) context).findViewById(R.id.gems);
        gemDialog = new Dialog(context);

        gemDialog.setContentView(R.layout.buy_gems_popup);

        TextView txtClose = (TextView) gemDialog.findViewById(R.id.closeButtonGemsPopup);
        TextView buyGemPack1 = (TextView) gemDialog.findViewById(R.id.gemPack1);
        TextView buyGemPack2 = (TextView) gemDialog.findViewById(R.id.gemPack2);
        TextView buyGemPack3 = (TextView) gemDialog.findViewById(R.id.gemPack3);

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gemDialog.dismiss();
            }
        });

        buyGemPack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bp.consumePurchase("android.test.purchased");
            }
        });

        buyGemPack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bp.consumePurchase("android.test.purchased");
            }
        });

        buyGemPack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bp.consumePurchase("android.test.purchased");
            }
        });
    }

    public void Show()
    {
        gemDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        gemDialog.show();

        Window window = gemDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    //Purchases
    //region
    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {

        //storage.addGems(xxx);
        gems.setText(storage.getTotalGems() + "");
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }
    //endregion
}
