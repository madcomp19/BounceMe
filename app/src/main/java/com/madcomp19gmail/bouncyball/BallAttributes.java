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
    private float MIN_RADIUS = 10f;
    private float MAX_RADIUS = 50f;

    float bounce;
    float friction;
    float gravity;
    float radius;
    float gravitational_force;

    // Max velocity
    float MAX_VELOCITY = -1;

    int type; //0 -> normal ; (1-3) -> special (1-3)


    BallAttributes(String attributes, int width)
    {
        // Normal Balls
        if(!attributes.contains("special"))
        {
            type = 0;

            MIN_RADIUS = Math.max(width * 0.025f, 10f);
            MAX_RADIUS = Math.min(width * 0.05f, 50f);

            String[] splits = attributes.split("_");

            bounce = map(Integer.parseInt(splits[0]), 1, 3, MIN_BOUNCE, MAX_BOUNCE);
            friction = map(Integer.parseInt(splits[1]), 1, 3, MIN_FRICTION, MAX_FRICTION);
            gravity = map(Integer.parseInt(splits[2]), 1, 3, MIN_GRAVITY, MAX_GRAVITY);
            radius = map(Integer.parseInt(splits[3]), 1, 3, MIN_RADIUS, MAX_RADIUS);
            gravitational_force = 300;
        }
        // Special Balls
        else
        {
            switch (Integer.parseInt(attributes.split("_")[1]))
            {
                // Infinite bounce ball with max velocity
                case 1:
                    type = 1;
                    MAX_VELOCITY = 100f;
                    bounce = 0f;
                    friction = 0f;
                    gravity = 5f;
                    radius = width * 0.1f;
                    gravitational_force = 300;
                    break;

                // Boosted Bounce ball with unlimited velocity
                case 2:
                    type = 2;
                    MAX_VELOCITY = 150f;
                    bounce = 0.1f;
                    friction = 0f;
                    gravity = 0f;
                    radius = width * 0.05f;
                    gravitational_force = 300;
                    break;

                // Black Hole ball
                case 3:
                    type = 3;
                    MAX_VELOCITY = 2f;
                    bounce = 0f;
                    friction = 0f;
                    gravity = 0f;
                    radius = width * 0.08f;
                    gravitational_force = Math.max(GameView.width, GameView.height);
                    break;
                default:
                    break;
            }
        }
    }

    public final float map(float value, float istart, float istop, float ostart, float ostop) {
        return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
    }
}
