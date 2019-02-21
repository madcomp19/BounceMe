package com.madcomp19gmail.bouncyball;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class GameView extends View {
    //public    Paint c;

    ArrayList<Ball> balls;
    float ball_radius;

    public Paint p;

    private static final int RADIUS = 46;

    private int centerX;
    private int centerY;
    private int speedX = 500;
    private int speedY = 400;
    //private Paint paint;

    private DisplayMetrics displayMetrics = new DisplayMetrics();
    public static int width;
    public static int height;


    private Vector2 dragStartLoc;
    private Vector2 dragEndLoc;


    public GameView(Context context) {
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
        balls = new ArrayList<Ball>();

        BallAttributes attributes = new BallAttributes(ball_radius, 10, 10, 10, 10, new Vector2(0, 9.8f));
        balls.add(new Ball(width / 2, height / 2, attributes, p));

        dragStartLoc = new Vector2(0, 0);
        dragEndLoc = new Vector2(0, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Vector2 touchPosition = new Vector2(event.getX(), event.getY());

        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN)
        {
            for(Ball ball : balls)
            {
                if(Vector2.dist(touchPosition, ball.position) < ball.radius)
                {
                    ball.dragged = true;
                    ball.position = touchPosition;
                    dragStartLoc = touchPosition;
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
                    dragEndLoc = touchPosition;

                    Vector2 force = Vector2.sub(dragEndLoc, dragStartLoc);
                    //ball.applyForce(force);
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
