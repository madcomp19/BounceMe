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
    private final float MAX_GRAVITY = 15f;

    // Ball radius
    private final float MIN_RADIUS = 25f;
    private final float MAX_RADIUS = 50f;

    float bounce;
    float friction;
    float gravity;
    float radius;


    BallAttributes(String attributes)
    {
        String[] splits = attributes.split("_");

        bounce = map(Integer.parseInt(splits[0]), 1, 4, MIN_BOUNCE, MAX_BOUNCE);
        friction = map(Integer.parseInt(splits[1]), 1, 4, MIN_FRICTION, MAX_FRICTION);
        gravity = map(Integer.parseInt(splits[2]), 1, 4, MIN_GRAVITY, MAX_GRAVITY);
        radius = map(Integer.parseInt(splits[3]), 1, 4, MIN_RADIUS, MAX_RADIUS);
    }

    public final float map(float value, float istart, float istop, float ostart, float ostop) {
        return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
    }
}
