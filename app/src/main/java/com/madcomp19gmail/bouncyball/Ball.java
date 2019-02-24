package com.madcomp19gmail.bouncyball;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Ball
{

    Vector2 position;
    Vector2 velocity;
    Vector2 acceleration;
    //Vector2 gravity;
    float angle;
    BallAttributes attributes;

    float radius;
    Paint color;
    Bitmap image;

    boolean dragged;


    public Ball(float x, float y, BallAttributes ba, Paint aColor, Bitmap img)
    {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 0);
        //gravity = new Vector2(0, 9.8f);
        angle = 0;
        attributes = ba;

        radius = attributes.radius;

        if(color != null)
            color = aColor;
        else if(img != null)
            image = img;


        dragged = false;
    }

    public void move()
    {
        if(dragged)
            return;

        float prev_velY = velocity.y;

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

        float rightLimit = GameView.width - radius;
        float bottomLimit = GameView.height - radius;

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

    public void display(Canvas canvas)
    {
        if(color != null)
            canvas.drawCircle(position.x, position.y, radius, color);
        else if(image != null)
        {
            //canvas.drawBitmap(image, position.x - radius, position.y - radius, null);

            canvas.save(); //Saving the canvas and later restoring it so only this image will be rotated.
            canvas.rotate(angle, position.x, position.y);
            /*Matrix matrix = new Matrix();
            matrix.postRotate(70);
            image = Bitmap.createBitmap(image, 0, 0,
                    image.getWidth(), image.getHeight(),
                    matrix, true);*/

            canvas.drawBitmap(image, position.x - radius, position.y - radius, null);

            /*matrix = new Matrix();
            matrix.postRotate(-1);
            image = Bitmap.createBitmap(image, 0, 0,
                    image.getWidth(), image.getHeight(),
                    matrix, true);*/
            canvas.restore();
            //canvas.rotate(-10);
        }
    }


}
