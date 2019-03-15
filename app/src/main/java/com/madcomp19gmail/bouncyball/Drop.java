package com.madcomp19gmail.bouncyball;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.util.Random;

public class Drop
{

    Vector2 position;
    Vector2 velocity;
    Vector2 acceleration;
    Vector2 gravity;
    float angle;

    float radius;
    Bitmap image;

    int health;
    int type;


    public Drop(float x, float y, float r, int aType, Bitmap img)
    {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 0);
        gravity = new Vector2(0, 9.8f);
        angle = 0;

        //if(sound!=0)
        //    SoundPoolManager.getInstance().loadSound(sound);

        radius = r;

        image = img;

        type = aType;
        health = 255;
    }

    public void move()
    {
        float prev_velY = velocity.y;
        float rightLimit = GameView.width - radius;
        float bottomLimit = GameView.height - radius;

        acceleration.add(gravity);
        velocity.add(acceleration);
        position.add(velocity);


        if(angle + velocity.x > 360)
        {
            float a = 360 - angle;
            float velX = velocity.x - a;
            angle = velX;
        }
        else if(angle + velocity.x < -360)
        {
            float a = -360 - angle;
            float velX = velocity.x + a;
            angle = velX;
        }
        else
            angle += velocity.x;


        acceleration.mult(0);


        if (position.x >= rightLimit) {
            position.x = rightLimit;
            applyForce(new Vector2(velocity.x / 2, 0));
            velocity.x *= -1;

            removeHealth();
        }
        if (position.x <= radius) {
            position.x = radius;
            applyForce(new Vector2(velocity.x / 2, 0));
            velocity.x *= -1;

            removeHealth();
        }
        if (position.y >= bottomLimit)
        {
            position.y = bottomLimit;

            applyForce(new Vector2(0,velocity.y / 2));
            velocity.y *= -1;

            if(Math.abs(prev_velY - velocity.y) < 1f) {
                applyForce(new Vector2(-velocity.x / 30, 0));
                removeHealth();
            }
        }
        if (position.y <= radius) {
            position.y = radius;
            applyForce(new Vector2(0,velocity.y / 2));
            velocity.y *= -1;

            removeHealth();
        }

        if(position.x <= radius && velocity.x < 1)
            position.x = 0 + radius + 1;
        else if(position.x >= rightLimit && velocity.x < 1)
            position.x = rightLimit - 1;
    }

    private void removeHealth()
    {
        if(type == 1)
            health -= 4;
        else
            health -= 1;
    }

    public void applyForce(Vector2 force)
    {
        acceleration.add(force);
    }

    private void playSound()
    {
        //SoundPoolManager.getInstance().playSound(R.raw.bubble);
        //if(sound != 0)
        //    SoundPoolManager.getInstance().playSound();
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight)
    {
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

    public void display(Canvas canvas)
    {
        if(image != null)
        {
            Paint p = new Paint();
            p.setAlpha(health);

            canvas.save(); //Saving the canvas and later restoring it so only this image will be rotated.

            canvas.rotate(angle, position.x, position.y);

            canvas.drawBitmap(image, position.x - radius, position.y - radius, p);

            canvas.restore();
        }
    }
}