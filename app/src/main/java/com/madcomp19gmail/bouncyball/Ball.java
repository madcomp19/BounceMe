package com.madcomp19gmail.bouncyball;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

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
    int sound;
    int trail;

    ArrayList<Vector2> trailPositions;

    boolean dragged;

    int iteration;
    boolean reactive;


    public Ball(float x, float y, BallAttributes ba, Bitmap img, int trailHue, int sound)
    {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 0);
        //gravity = new Vector2(0, 9.8f);
        angle = 0;
        attributes = ba;
        this.sound = sound;

        if(sound!=0)
            SoundPoolManager.getInstance().loadSound(sound);

        radius = attributes.radius;

        if(img != null)
            image = img;

        trail = trailHue;

        reactive = (trail == -4);

        trailPositions = new ArrayList<>();
        trailPositions.add(new Vector2(position.x, position.y));

        dragged = false;

        iteration = 0;
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
            position.x = rightLimit - 1;
            applyForce(new Vector2(velocity.x / 10, 0));
            velocity.x *= -1;
            playSound();

            GameWorld.addDrop(position);

            if(reactive)
                trail = new Random().nextInt(361);
        }
        if (position.x <= radius) {
            position.x = radius + 1;
            applyForce(new Vector2(velocity.x / 10, 0));
            velocity.x *= -1;
            playSound();

            GameWorld.addDrop(position);

            if(reactive)
                trail = new Random().nextInt(361);
        }
        if (position.y >= bottomLimit) {
            position.y = bottomLimit - 1;
            applyForce(new Vector2(0,velocity.y / 10));
            velocity.y *= -1;

            if(Math.abs(prev_velY - velocity.y) > 50f)
            {
                playSound();

                GameWorld.addDrop(position);

                if(reactive)
                    trail = new Random().nextInt(361);
            }
            else if(Math.abs(prev_velY - velocity.y) > 10f)
                playSound();
            else if(Math.abs(prev_velY - velocity.y) < 1f)
                applyForce(new Vector2(-velocity.x / 30, 0));
        }
        if (position.y <= radius) {
            position.y = radius + 1;
            applyForce(new Vector2(0,velocity.y / 10));
            velocity.y *= -1;
            playSound();

            GameWorld.addDrop(position);

            if(reactive)
                trail = new Random().nextInt(361);
        }

        // Trail -1 --> clear (no trail)
        if(trail == -1)
            return;

        trailPositions.add(new Vector2(position.x, position.y));

        if(trailPositions.size() > 20)
            trailPositions.remove(0);
    }

    public void applyForce(Vector2 force)
    {
        acceleration.add(force);
    }

    private void playSound()
    {
        if(sound != 0)
            SoundPoolManager.getInstance().playSound();
    }

    private void drawTrail(Canvas canvas, float hue, float saturation, float value)
    {
        int size = trailPositions.size();

        Paint p = new Paint();

        for(int i = 0; i < size; i++)
        {
            int alpha = 255 - (size - i) * 10;
            float[] values = {hue, saturation, value}; // hue (0-360) ; saturation (0-1) ; value (0-1)
            p.setColor(Color.HSVToColor(alpha, values));

            canvas.drawCircle(trailPositions.get(i).x, trailPositions.get(i).y, radius - 2 * (size - i), p);
        }
    }

    public void display(Canvas canvas)
    {
        if(iteration > 360)
            iteration = 0;
        else
            iteration++;

        if(image != null)
        {
            if(trail != -1) // clear == -1
            {
                if(trail == -2) // rainbow
                {
                    int size = trailPositions.size();

                    Paint p = new Paint();

                    for(int i = 0; i < size; i++)
                    {
                        int hue = i * (360 / size);
                        int alpha = 255 - (size - i) * 10;
                        float[] values = {hue, 1, 1}; // hue (0-360) ; saturation (0-1) ; value (0-1)
                        p.setColor(Color.HSVToColor(alpha, values));

                        canvas.drawCircle(trailPositions.get(i).x, trailPositions.get(i).y, radius - 2 * (size - i), p);
                    }
                }
                else if(trail == -3) // spectrum
                    drawTrail(canvas, iteration, 1, 1);
                else if(trail == -5) // black
                    drawTrail(canvas, 0, 0, 0);
                else if(trail == -6) // gray #1
                    drawTrail(canvas, 0, 0, 0.2f);
                else if(trail == -7) // gray #2
                    drawTrail(canvas, 0, 0, 0.43f);
                else if(trail == -8) // gray #2
                    drawTrail(canvas, 0, 0, 0.68f);
                else if(trail == -9) // white
                    drawTrail(canvas, 0, 0, 1);
                else if(trail >= 0 && trail <= 360 || reactive)// color/reactive
                    drawTrail(canvas, trail, 1, 1);
            }


            canvas.save(); //Saving the canvas and later restoring it so only this image will be rotated.

            canvas.rotate(angle, position.x, position.y);

            canvas.drawBitmap(image, position.x - radius, position.y - radius, null);

            canvas.restore();
        }
    }
}
