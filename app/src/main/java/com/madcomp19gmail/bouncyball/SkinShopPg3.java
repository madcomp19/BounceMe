package com.madcomp19gmail.bouncyball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

public class SkinShopPg3 extends AppCompatActivity {

    float prevX;
    TextView coins;
    private StorageManager storageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_shop_pg3);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        storageManager = StorageManager.getInstance();
        coins = findViewById(R.id.coins);
    }

    @Override
    protected void onResume(){
        super.onResume();

        coins.setText(storageManager.getTotalTouches() + "");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN)
        {
            prevX = event.getX();
        }
        else if(action == MotionEvent.ACTION_UP)
        {
            if(event.getX() > prevX)
                swipeRight();

            else if(event.getX() < prevX)
                swipeLeft();
        }

        return true;
    }

    private void swipeLeft()
    {
        //
    }

    private void swipeRight()
    {
        Intent intent = new Intent(this, SkinShopPg2.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
