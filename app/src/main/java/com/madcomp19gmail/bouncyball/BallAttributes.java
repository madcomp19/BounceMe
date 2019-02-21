package com.madcomp19gmail.bouncyball;

/**
 * Created by diogocruz on 21/02/2019.
 */

public class BallAttributes
{
    Vector2 gravity;
    float buoyancy;
    float restitution;
    float friction;
    float mass;
    float radius;


    BallAttributes(float aRadius, float aMass, float aFriction, float aRestitution, float aBuoyancy, Vector2 aGravity)
    {
        radius = aRadius;
        mass = aMass;
        friction = aFriction;
        restitution = aRestitution;
        buoyancy = aBuoyancy;
        gravity = aGravity;
    }
}
