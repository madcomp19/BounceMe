package com.madcomp19gmail.bouncyball;

public class Vector2
{
    public float x;
    public float y;

    public Vector2(float ax, float ay)
    {
        x = ax;
        y = ay;
    }

    public void add(Vector2 vector)
    {
        x += vector.x;
        y += vector.y;
    }

    public void sub(Vector2 vector)
    {
        x -= vector.x;
        y -= vector.y;
    }

    public static Vector2 sub(Vector2 v1, Vector2 v2)
    {
        return new Vector2(v1.x - v2.x, v1.y - v2.y);
    }

    public float mag()
    {
        return (float) Math.sqrt(x * x + y * y);
    }

    public static float dist(Vector2 v1, Vector2 v2)
    {
        return (float) Math.sqrt(Math.pow(v2.x - v1.x, 2) + Math.pow(v2.y - v1.y, 2));
    }

    public float dist(Vector2 v2)
    {
        return (float) Math.sqrt(Math.pow(v2.x - x, 2) + Math.pow(v2.y - y, 2));
    }

    public void normalize()
    {
        float total = x + y;
        x /= total;
        y /= total;
    }

    public void mult(float number)
    {
        x *= number;
        y *= number;
    }

    public void limit(float max)
    {
        if (mag() > max*max)
        {
            normalize();
            mult(max);
        }
    }
}
