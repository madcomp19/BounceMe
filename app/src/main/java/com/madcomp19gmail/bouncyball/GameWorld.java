package com.madcomp19gmail.bouncyball;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class GameWorld extends AppCompatActivity {

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_world);
        setContentView(new MyView(this));

        back = findViewById(R.id.back);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void goBack(View view)
    {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    class MyView extends View {
        //public    Paint c;
        public Paint p;

        private static final int RADIUS = 46;

        private int centerX;
        private int centerY;
        private int speedX = 500;
        private int speedY = 400;
        //private Paint paint;


        public MyView(Context context) {
            super(context);
            setBackgroundColor(Color.parseColor("#000000"));
            p = new Paint();
            p.setColor(Color.GREEN);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldW, int oldH) {
            centerX = w / 2;
            centerY = h / 2;
        }

        protected void onDraw(Canvas c) {
            int w = getWidth() - 50;
            int h = getHeight() - 50;
            centerX += speedX;
            centerY += speedY;
            int rightLimit = w - RADIUS;
            int bottomLimit = h - RADIUS;

            if (centerX >= rightLimit) {
                centerX = rightLimit;
                speedX *= -1;
            }
            if (centerX <= RADIUS) {
                centerX = RADIUS;
                speedX *= -1;
            }
            if (centerY >= bottomLimit) {
                centerY = bottomLimit;
                speedY *= -1;
            }
            if (centerY <= RADIUS) {
                centerY = RADIUS;
                speedY *= -1;
            }

            c.drawCircle(centerX, centerY, RADIUS, p);
            postInvalidateDelayed(10);
        }
    }
}

