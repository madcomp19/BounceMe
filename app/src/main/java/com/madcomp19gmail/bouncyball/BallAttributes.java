package com.madcomp19gmail.bouncyball;

/**
 * Created by diogocruz on 21/02/2019.
 */

public class BallAttributes
{
    /*Vector2 gravity;
    float buoyancy;
    float restitution;
    float friction;
    float mass;
    float radius;*/

    // Velocity loss upon collision
    private final float MIN_BOUNCE = 3/4f;
    private final float MAX_BOUNCE = 1/20f;

    // Velocity loss upon contact with the ground and air
    private final float MIN_FRICTION = 1/30f;
    private final float MAX_FRICTION = 3/4f;

    // Vertical acceleration towards the ground
    private final float MIN_GRAVITY = 5f;
    private final float MAX_GRAVITY = 20f;

    // Ball radius
    private final float MIN_RADIUS = 12.5f;
    private final float MAX_RADIUS = 50f;

    float bounce;
    float friction;
    float gravity;
    float radius;


    BallAttributes(String attributes)
    {
        /*radius = aRadius;
        mass = aMass;
        friction = aFriction;
        restitution = aRestitution;
        buoyancy = aBuoyancy;
        gravity = aGravity;*/

        String[] splits = attributes.split("_");

        bounce = map(Integer.parseInt(splits[0]), 1, 4, MIN_BOUNCE, MAX_BOUNCE);
        friction = map(Integer.parseInt(splits[0]), 1, 4, MIN_FRICTION, MAX_FRICTION);
        gravity = map(Integer.parseInt(splits[0]), 1, 4, MIN_GRAVITY, MAX_GRAVITY);
        radius = map(Integer.parseInt(splits[0]), 1, 4, MIN_RADIUS, MAX_RADIUS);
    }

    public final float map(float value, float istart, float istop, float ostart, float ostop) {
        return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
    }
}
