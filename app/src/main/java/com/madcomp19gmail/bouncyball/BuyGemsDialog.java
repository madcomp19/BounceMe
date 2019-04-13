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
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

public class BuyGemsDialog implements BillingProcessor.IBillingHandler {

    BillingProcessor bp;

    private final String KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjh3EhNjyRn3NsOk/3u9cpIlQpbJP6V1Mo+Y21Q8enUPgTu1Mfc2Y0aIpLXjlgIOcrGXndLgj7kj2jZTjVzlWv3I3V58pORyaqFrWZIYYvIJV9imlH4Omi7N0UDsW49Ghebzp3oE8TH1oCHkWb9xdRov7OdjhcwBUm/EyehZLpueZ6K4pAsd700S/paW6Kx2j2+UOgO1P+nFlbLRKHw8X27ItoT3JtxDEFCksy9fHs9GSKMGA9CS++nHoCJcurgZE0gVetDAEa38mazreemealoV7ScRSE8jHTf7CKUYuFALIDBvDs7pyOb5B3WDG/kkHeLhaBP7/dVcXiHm6JEEh5QIDAQAB";

    private Dialog gemDialog;

    StorageManager storage;

    TextView gems;

    Context mContext;

    public BuyGemsDialog(Context context)
    {
        mContext = context;

        bp = new BillingProcessor(context, KEY, this);
        bp.initialize();

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
                bp.consumePurchase("gem_pack_1");
            }
        });

        buyGemPack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.consumePurchase("gem_pack_2");
            }
        });

        buyGemPack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.consumePurchase("gem_pack_3");
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

        if(productId.equals("gem_pack_1"))
            storage.addGems(50);
        if(productId.equals("gem_pack_2"))
            storage.addGems(200);
        if(productId.equals("gem_pack_3"))
            storage.addGems(500);

        Toast.makeText(mContext, "Purchase Successful", Toast.LENGTH_SHORT).show();
        gems.setText(storage.getTotalGems() + "");
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

        Toast.makeText(mContext, "Something went wrong with the purchase", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingInitialized() {

    }
    //endregion
}
