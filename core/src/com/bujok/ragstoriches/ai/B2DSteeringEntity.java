package com.bujok.ragstoriches.ai;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.bujok.ragstoriches.ai.utilities.SteeringUtilities;

/**
 * Created by Tojoh on 01/05/2016.
 */
public class B2DSteeringEntity implements Steerable<Vector2>
{
    Body body;
    float boundingRadius;

    boolean tagged;
    float maxLinearSpeed, maxLinearAcceleration;
    float maxAngularSpeed, maxAngularAcceleration;

    SteeringBehavior<Vector2> behaviour;
    SteeringAcceleration<Vector2> steeringOutput;

    public B2DSteeringEntity(Body body, float boundingRadius)
    {
        this.body = body;
        this.boundingRadius = boundingRadius;
    }

    @Override
    public Vector2 getLinearVelocity()
    {
        return this.body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity()
    {
        return this.body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius()
    {
        return this.boundingRadius;
    }

    @Override
    public boolean isTagged()
    {
        return this.tagged;
    }

    @Override
    public void setTagged(boolean tagged)
    {
        this.tagged = tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {

    }

    @Override
    public float getMaxLinearSpeed()
    {
        return this.maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed)
    {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration()
    {
        return this.maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration)
    {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed()
    {
        return this.maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed)
    {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration()
    {
        return this.maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration)
    {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    @Override
    public Vector2 getPosition()
    {
        return this.body.getPosition();
    }

    @Override
    public float getOrientation()
    {
        return this.body.getAngle();
    }

    @Override
    public void setOrientation(float orientation)
    {

    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return SteeringUtilities.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return SteeringUtilities.angleToVector(outVector, angle);
    }

    @Override
    public Location<Vector2> newLocation() {
        return null;
    }
}
