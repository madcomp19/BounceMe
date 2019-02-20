package com.madcomp19gmail.bouncyball;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Ball
{

    Vector2 position;
    Vector2 velocity;
    Vector2 acceleration;
    Vector2 gravity;

    float radius;
    Paint color;

    boolean dragged;

    public Ball(float x, float y, float aRadius, Paint aColor)
    {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 0);
        gravity = new Vector2(0, 9.8f);

        radius = aRadius;
        color = aColor;

        dragged = false;
    }

    public void move()
    {
        if(dragged)
            return;

        acceleration.add(gravity);
        velocity.add(acceleration);
        position.add(velocity);

        float rightLimit = GameView.width - radius;
        float bottomLimit = GameView.height - radius;

        acceleration.mult(0);

        if (position.x >= rightLimit) {
            position.x = rightLimit;
            applyForce(new Vector2(velocity.x / 10, 0));
            velocity.x *= -1;
        }
        if (position.x <= radius) {
            position.x = radius;
            applyForce(new Vector2(velocity.x / 10, 0));
            velocity.x *= -1;
        }
        if (position.y >= bottomLimit) {
            position.y = bottomLimit;
            applyForce(new Vector2(0,velocity.y / 10));
            velocity.y *= -1;

            if(velocity.y < 1)
                applyForce(new Vector2(-velocity.x / 30,0));
        }
        if (position.y <= radius) {
            position.y = radius;
            applyForce(new Vector2(0,velocity.y / 10));
            velocity.y *= -1;
        }
    }

    public void applyForce(Vector2 force)
    {
        acceleration.add(force);
    }

    public void display(Canvas canvas)
    {
        canvas.drawCircle(position.x, position.y, radius, color);
    }
}
