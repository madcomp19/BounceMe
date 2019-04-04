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
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class GameView extends View
{
    ArrayList<Ball> balls;

    static ArrayList<Drop> drops;
    float coin_radius;
    float gem_radius;
    Bitmap coin_img;
    Bitmap coin_2x_img;
    Bitmap coin_5x_img;
    Bitmap coin_10x_img;
    Bitmap coin_50x_img;
    Bitmap gem_img;
    Bitmap gem_icon;

    private DisplayMetrics displayMetrics = new DisplayMetrics();
    public static int width;
    public static int height;


    private final int TICKS_PER_SECOND = 50;
    private final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
    private final int MAX_FRAMESKIP = 5;

    private long next_game_tick = System.currentTimeMillis();
    int loops;
    float interpolation;

    StorageManager storageManager;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        storageManager = StorageManager.getInstance();

        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);


        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        // Outer min's should take effect in high resolution screens - values not tested
        coin_radius = Math.min(Math.max(width * 0.025f, 5f), 40f);
        gem_radius = Math.min(Math.max(width * 0.05f, 10f), 80f);

        balls = new ArrayList<>();

        Resources res = getResources();

        drops = new ArrayList<>();

        coin_img = BitmapFactory.decodeResource(res, R.drawable.coin_icon);
        coin_img = getResizedBitmap(coin_img, (int) coin_radius * 2, (int) coin_radius * 2);

        coin_2x_img = BitmapFactory.decodeResource(res, R.drawable.coin_icon_2x);
        coin_2x_img = getResizedBitmap(coin_2x_img, (int) coin_radius * 2, (int) coin_radius * 2);

        coin_5x_img = BitmapFactory.decodeResource(res, R.drawable.coin_icon_5x);
        coin_5x_img = getResizedBitmap(coin_5x_img, (int) coin_radius * 2, (int) coin_radius * 2);

        coin_10x_img = BitmapFactory.decodeResource(res, R.drawable.coin_icon_10x);
        coin_10x_img = getResizedBitmap(coin_10x_img, (int) coin_radius * 2, (int) coin_radius * 2);

        coin_50x_img = BitmapFactory.decodeResource(res, R.drawable.coin_icon_50x);
        coin_50x_img = getResizedBitmap(coin_50x_img, (int) coin_radius * 2, (int) coin_radius * 2);

        gem_img = BitmapFactory.decodeResource(res, R.drawable.gem_icon);
        gem_img = getResizedBitmap(gem_img, (int) gem_radius * 2, (int) gem_radius * 2);

        gem_icon = BitmapFactory.decodeResource(res, R.drawable.gem_icon);
        gem_icon = getResizedBitmap(gem_icon, (int) coin_radius * 2, (int) coin_radius * 2);

        int selected_skin = StorageManager.getInstance().getSelectedSkin();
        int selected_trail = StorageManager.getInstance().getSelectedTrail();
        int selected_sound = StorageManager.getInstance().getSelectedSound();

        Bitmap ball_img;

        if(selected_skin != 0)
            ball_img = BitmapFactory.decodeResource(res, selected_skin);
        else
            ball_img = BitmapFactory.decodeResource(res, R.drawable.eye);

        BallAttributes attributes = new BallAttributes(storageManager.getSelectedBall(), width);

        ball_img = getResizedBitmap(ball_img, (int) attributes.radius * 2, (int) attributes.radius * 2);

        balls.add(new Ball(width / 2, height / 2, attributes, ball_img, selected_trail, selected_sound));
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
                ball.dragged = true;
                ball.trailPositions.clear();
                ball.position = touchPosition;
                ball.velocity.mult(0);
                ball.acceleration.mult(0);
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
                    ball.velocity.mult(1.3f);
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

    public void spawnCoin(Vector2 position, int value)
    {
        int boost = storageManager.getActiveBoost();
        Bitmap coin = Bitmap.createBitmap((int) coin_radius * 2, (int) coin_radius * 2, Bitmap.Config.ARGB_8888);

        switch (boost)
        {
            case 1: coin = coin_img; break;
            case 2: coin = coin_2x_img; break;
            case 5: coin = coin_5x_img; break;
            case 10: coin = coin_10x_img; break;
            case 50: coin = coin_50x_img; break;
        }

        Drop drop = new Drop(position.x, position.y, coin_radius, 1, value, coin);
        drop.applyForce(new Vector2((float) randomWithRange(-40, 40), (float) randomWithRange(0, 200)));
        drops.add(drop);
    }

    public void spawnGem(Vector2 position, int value)
    {
        Drop drop = new Drop(position.x, position.y, gem_radius, 0, value, gem_img);
        drop.applyForce(new Vector2((float) randomWithRange(-40, 40), (float) randomWithRange(0, 200)));
        drops.add(drop);
    }

    protected void onDraw(Canvas c)
    {
        loops = 0;

        while (System.currentTimeMillis() > next_game_tick && loops < MAX_FRAMESKIP)
        {
            updateGame();

            next_game_tick += SKIP_TICKS;
            loops++;
        }

        interpolation = ((float) System.currentTimeMillis() + SKIP_TICKS - next_game_tick) / (float) SKIP_TICKS;

        displayGame(c, interpolation);

        postInvalidate();
    }

    private void updateGame()
    {
        for(Ball ball : balls)
            ball.move();

        for(int i = drops.size() - 1; i >= 0; i--) {

            Drop drop = drops.get(i);

            drop.move();

            for(Ball ball : balls)
            {
                float dist = Vector2.dist(ball.position, drop.position);

                if(dist <= ball.attributes.radius + drop.radius && drop.health < 1)
                {
                    if (drop.type == 1) {
                        GameWorld.addTouches(drop.value);
                        GameWorld.updateCoinsLabel(GameWorld.getTouches() - MainMenu.getPrevTouches());
                    }
                    else if (drop.type == 0) {
                        GameWorld.addGems(drop.value);
                        GameWorld.updateGemsLabel(GameWorld.getGems());
                    }

                    drops.remove(drop);
                }
                else if(dist < ball.attributes.radius + drop.radius + 300 && drop.health < 1)
                {
                    Vector2 force = Vector2.sub(ball.position, drop.position);
                    force.mult(4f / dist);
                    force.y -= 9.8f;
                    drop.applyForce(force);
                }
            }
        }
    }

    private void displayGame(Canvas canvas, float interpolation)
    {
        for(Drop drop : drops)
            drop.display(canvas);

        for(Ball ball : balls)
            ball.display(canvas);

        long boost_time = storageManager.getActiveBoostTime() - Calendar.getInstance().getTimeInMillis();

        if(boost_time >= 0)
        {
            int boost = storageManager.getActiveBoost();

            Calendar time = Calendar.getInstance();
            time.setTimeInMillis(boost_time);

            String second = time.get(Calendar.SECOND) + "";
            String millisecond = time.get(Calendar.MILLISECOND) + "";
            if(second.length() == 1)
                second = "0" + second;
            if(millisecond.length() == 1)
                millisecond = "00" + millisecond;
            else if(millisecond.length() == 2)
                millisecond = "0" + millisecond;

            GameWorld.updateActiveBoost(boost, time.get(Calendar.MINUTE) + ":" + second + "." + millisecond);
        }
        else
            GameWorld.updateActiveBoost(1, null);
    }
}
