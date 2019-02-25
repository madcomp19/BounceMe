package com.madcomp19gmail.bouncyball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;

public class SkinShopPg2 extends AppCompatActivity {

    float prevX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_shop_pg2);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        Intent intent = new Intent(this, SkinShopPg3.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void swipeRight()
    {
        Intent intent = new Intent(this, SkinShopPg1.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
