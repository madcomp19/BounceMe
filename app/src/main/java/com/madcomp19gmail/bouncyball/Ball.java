package com.madcomp19gmail.bouncyball;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.util.ArrayList;

public class Ball
{

    Vector2 position;
    Vector2 velocity;
    Vector2 acceleration;
    //Vector2 gravity;
    float angle;
    BallAttributes attributes;

    float radius;
    Bitmap image;
    Bitmap trail;

    ArrayList<Vector2> trailPositions;

    boolean dragged;


    public Ball(float x, float y, BallAttributes ba, Bitmap img, Bitmap aTrail)
    {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 0);
        //gravity = new Vector2(0, 9.8f);
        angle = 0;
        attributes = ba;

        radius = attributes.radius;

        if(img != null)
            image = img;

        trail = aTrail;
        if(trail != null)
            trail = getResizedBitmap(trail, (int) radius, (int) radius);
        trailPositions = new ArrayList<>();

        dragged = false;
    }

    public void move()
    {
        if(dragged)
            return;

        float prev_velY = velocity.y;
        float rightLimit = GameView.width - radius;
        float bottomLimit = GameView.height - radius;

        acceleration.add(attributes.gravity);
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
            applyForce(new Vector2(velocity.x / 10, 0));
            velocity.x *= -1;
            GameWorld.addTouch();
            //Play sound effect
            playSound();
        }
        if (position.x <= radius) {
            position.x = radius;
            applyForce(new Vector2(velocity.x / 10, 0));
            velocity.x *= -1;
            GameWorld.addTouch();
            //Play sound effect
            playSound();
        }
        if (position.y >= bottomLimit)
        {
            position.y = bottomLimit;

            applyForce(new Vector2(0,velocity.y / 10));
            velocity.y *= -1;

            if(Math.abs(prev_velY - velocity.y) > 50f)
            {
                GameWorld.addTouch();
                playSound();
            }
            else if(Math.abs(prev_velY - velocity.y) > 10f)
                playSound();
            else if(Math.abs(prev_velY - velocity.y) < 1f)
                applyForce(new Vector2(-velocity.x / 30, 0));
        }
        if (position.y <= radius) {
            position.y = radius;
            applyForce(new Vector2(0,velocity.y / 10));
            velocity.y *= -1;
            GameWorld.addTouch();
            //Play sound effect
            playSound();
        }

        if(position.x <= radius && velocity.x < 1)
            position.x = 0 + radius + 1;
        else if(position.x >= rightLimit && velocity.x < 1)
            position.x = rightLimit - 1;


        /*if(trail == null)
            return;

        if(iter == 5000)
        {
            iter = 0;
            trailPositions.add(position);
        }
        else
            iter++;

        if(trailPositions.size() > 500)
            trailPositions.remove(0);*/
    }

    public void applyForce(Vector2 force)
    {
        acceleration.add(force);
    }

    private void playSound()
    {
        //SoundPoolManager.getInstance().playSound(R.raw.bubble);
        SoundPoolManager.getInstance().playSound();
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
            /*if(trail != null)
            {
                for(int i = 0; i < trailPositions.size(); i++)
                {
                    //Bitmap trail_img = getResizedBitmap(trail, (int) radius - i * 2, (int) radius - i * 2);
                    canvas.drawBitmap(trail, trailPositions.get(i).x - radius / 2, trailPositions.get(i).y - radius / 2, null);
                }
            }*/


            canvas.save(); //Saving the canvas and later restoring it so only this image will be rotated.

            canvas.rotate(angle, position.x, position.y);

            canvas.drawBitmap(image, position.x - radius, position.y - radius, null);

            canvas.restore();
        }
    }
}
