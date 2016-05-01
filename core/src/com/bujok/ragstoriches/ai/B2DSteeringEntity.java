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

        this.maxLinearSpeed = 500;
        this.maxLinearAcceleration = 5000;
        this.maxAngularSpeed = 30;
        this.maxAngularAcceleration = 5;

        this.tagged = false;

        this.steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
        this.body.setUserData(this);
    }

    public void update(long delta)
    {
        if (this.behaviour != null)
        {
            this.behaviour.calculateSteering(steeringOutput);
            this.applySteering(delta);
        }
    }

    private void applySteering(long delta)
    {
        boolean anyAccelerations = false;

        // calculate and applies a force to the entity based on delta fps
        if (!this.steeringOutput.linear.isZero())
        {
            Vector2 force = this.steeringOutput.linear.scl(delta);
            this.body.applyForceToCenter(force, true);
            anyAccelerations = true;
        }

        // confused explanation on this.
        // may be some bugs in here.
        if (this.steeringOutput.angular != 0)
        {
            this.body.applyTorque(steeringOutput.angular * delta, true);
            anyAccelerations = true;
        }
        else
        {
            // turn to face the direction of movement
            Vector2 linVel = this.getLinearVelocity();
            if (!linVel.isZero())
            {
                float newOrientation = this.vectorToAngle(linVel);
                body.setAngularVelocity((newOrientation - this.getAngularVelocity())*delta);
                body.setTransform(body.getPosition(), newOrientation);
            }
        }

        // cap any acceleration that is occuring to stop acceleration over maxspeed.
        if (anyAccelerations)
        {
            // cap linear acceleration
            Vector2 linearVelocity = this.body.getLinearVelocity();
            float currentSpeedSquare = linearVelocity.len();
            if (currentSpeedSquare > this.maxLinearSpeed * this.maxLinearSpeed)
            {
                this.body.setLinearVelocity(linearVelocity.scl(maxLinearSpeed / (float)Math.sqrt(currentSpeedSquare)));
            }

            // cap angular acceleration
            if (body.getAngularVelocity() > this.maxAngularSpeed)
            {
                this.body.setAngularVelocity(this.maxAngularSpeed);
            }
        }
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

    public Body getBody()
    {
        return this.body;
    }

    public void setBehaviour(SteeringBehavior<Vector2> behaviour)
    {
        this.behaviour = behaviour;
    }

    public SteeringBehavior<Vector2> getBehaviour() {
        return this.behaviour;
    }
}
