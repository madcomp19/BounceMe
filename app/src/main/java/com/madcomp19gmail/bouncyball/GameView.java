package com.madcomp19gmail.bouncyball;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


public class GameView extends View
{
    ArrayList<Ball> balls;
    float ball_radius;


    private DisplayMetrics displayMetrics = new DisplayMetrics();
    public static int width;
    public static int height;


    public GameView(Context context)
    {
        super(context);
        setBackgroundColor(Color.parseColor("#000000"));

        ball_radius = 50;

        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);


        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        Paint p = new Paint();
        p.setColor(Color.YELLOW);
        balls = new ArrayList<>();

        Resources res = getResources();
        Bitmap ball_img = BitmapFactory.decodeResource(res, R.drawable.smiley_ball);
        ball_img = getResizedBitmap(ball_img, (int) ball_radius * 2, (int) ball_radius * 2);
        BallAttributes attributes = new BallAttributes(ball_radius, 10, 10, 10, 10, new Vector2(0, 9.8f));
        balls.add(new Ball(width / 2, height / 2, attributes, null, ball_img));
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        Vector2 touchPosition = new Vector2(event.getX(), event.getY());

        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN)
        {
            for(Ball ball : balls)
            {
                if(Vector2.dist(touchPosition, ball.position) < ball.radius + 100)
                {
                    ball.dragged = true;
                    ball.position = touchPosition;
                }
            }
        }
        else if(action == MotionEvent.ACTION_UP)
        {
            for(Ball ball : balls)
            {
                if(ball.dragged)
                {
                    ball.dragged = false;
                    ball.position = touchPosition;
                }
            }
        }
        else if(action == MotionEvent.ACTION_MOVE)
        {
            for(Ball ball : balls)
            {
                if(ball.dragged)
                {
                    ball.velocity = Vector2.sub(touchPosition, ball.position);
                    ball.position = touchPosition;
                }
            }
        }


        return true;
    }

    public void shake()
    {
        Vector2 force = new Vector2((float) randomWithRange(-1000, 1000), (float) randomWithRange(-1000, 1000));
        for(Ball ball : balls)
            ball.applyForce(force);
    }

    double randomWithRange(double min, double max)
    {
        double range = Math.abs(max - min);
        return (Math.random() * range) + (min <= max ? min : max);
    }

    /*@Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH)
    {
        centerX = w / 2;
        centerY = h / 2;
    }*/

    protected void onDraw(Canvas c)
    {
        for(Ball ball : balls)
        {
            ball.move();
            ball.display(c);
        }

        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextAlign(Paint.Align.CENTER);
        p.setTextSize(64);
        c.drawText(MainMenu.getTouches() + "", width / 2, 50, p);



        postInvalidateDelayed(1000/90);
    }
}
