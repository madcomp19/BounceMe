package com.madcomp19gmail.bouncyball;

/**
 * Created by diogocruz on 21/02/2019.
 */

public class BallAttributes
{
    // Velocity loss upon collision
    private final float MIN_BOUNCE = 1/2f;
    private final float MAX_BOUNCE = 1/40f;

    // Velocity loss upon contact with the ground and air
    private final float MIN_FRICTION = 1/200f;
    private final float MAX_FRICTION = 1/20f;

    // Vertical acceleration towards the ground
    private final float MIN_GRAVITY = 1/10f;
    private final float MAX_GRAVITY = 20f;

    // Ball radius
    private final float MIN_RADIUS;
    private final float MAX_RADIUS;

    float bounce;
    float friction;
    float gravity;
    float radius;


    BallAttributes(String attributes, int width)
    {
        MIN_RADIUS = Math.max(width * 0.025f, 10f);
        MAX_RADIUS = Math.min(width * 0.05f, 50f);

        String[] splits = attributes.split("_");

        bounce = map(Integer.parseInt(splits[0]), 1, 3, MIN_BOUNCE, MAX_BOUNCE);
        friction = map(Integer.parseInt(splits[1]), 1, 3, MIN_FRICTION, MAX_FRICTION);
        gravity = map(Integer.parseInt(splits[2]), 1, 3, MIN_GRAVITY, MAX_GRAVITY);
        radius = map(Integer.parseInt(splits[3]), 1, 3, MIN_RADIUS, MAX_RADIUS);
    }

    public final float map(float value, float istart, float istop, float ostart, float ostop) {
        return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
    }
}
