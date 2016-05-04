package com.bujok.ragstoriches.ai.steering;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Tojoh on 02/05/2016.
 */
public class S2DAIEntityMoving extends Scene2DAIEntity
{
    protected float maxLinearSpeed = 2;
    protected float maxLinearAcceleration = 500;
    protected float maxAngularSpeed = 5;
    protected float maxAngularAcceleration = 10;

    private Steerable<Vector2> currentTarget;

    public S2DAIEntityMoving(Actor parent, boolean independentFacing)
    {
        super(parent, false);
    }

    public void update (float delta)
    {
        position.set(this.parent.getX(Align.center), this.parent.getY(Align.center));
        if (steeringBehavior != null)
        {
            // if we are within bounding radius don't update.
            //if (position.dst(this.currentTarget.getPosition()) > this.boundingRadius)

            // Calculate steering acceleration
            steeringBehavior.calculateSteering(steeringOutput);

			/*
			 * Here you might want to add a motor control layer filtering steering accelerations.
			 *
			 * For instance, a car in a driving game has physical constraints on its movement: it cannot turn while stationary; the
			 * faster it moves, the slower it can turn (without going into a skid); it can brake much more quickly than it can
			 * accelerate; and it only moves in the direction it is facing (ignoring power slides).
			 */

            // Apply steering acceleration
            applySteering(steeringOutput, delta);
            //wrapAround(position, this.parent.getParent().getWidth(), this.parent.getParent().getHeight());
            //if (position.dst(this.parent.getX(), this.parent.getY()) > 5)
            //{
            this.parent.setPosition(position.x, position.y, Align.center);
            //}

            // if we have reached our target stop
//                if (position.dst(this.parent.getX(), this.parent.getY()) < 25)
//                {
//                    this.steeringBehavior = null;
//                }
        }
    }

    // the display area is considered to wrap around from top to bottom
    // and from left to right
    protected static void wrapAround (Vector2 pos, float maxX, float maxY) {
        if (pos.x > maxX) pos.x = 0.0f;

        if (pos.x < 0) pos.x = maxX;

        if (pos.y < 0) pos.y = maxY;

        if (pos.y > maxY) pos.y = 0.0f;
    }


    private void applySteering (SteeringAcceleration<Vector2> steering, float time)
    {
        // Update position and linear velocity. Velocity is trimmed to maximum speed
        position.mulAdd(linearVelocity, time);
        //linearVelocity = steering.linear.limit(this.getMaxLinearSpeed());
        linearVelocity.mulAdd(steering.linear, time);
        linearVelocity.limit(getMaxLinearSpeed());
        //linearVelocity = new Vector2((float)Math.floor((double)linearVelocity.x), (float)Math.floor((double)linearVelocity.y));


//        // Update orientation and angular velocity
//        if (independentFacing)
//        {
//            this.parent.setRotation(this.parent.getRotation() + (angularVelocity * time) * MathUtils.radiansToDegrees);
//            angularVelocity += steering.angular * time;
//        }
//        else
//        {
//            // If we haven't got any velocity, then we can do nothing.
        //if (!linearVelocity.isZero(getZeroLinearSpeedThreshold())) {
        //float newOrientation = vectorToAngle(linearVelocity);
//                angularVelocity = (newOrientation - this.parent.getRotation() * MathUtils.degreesToRadians) * time; // this is superfluous if independentFacing is always true
//                this.parent.setRotation(newOrientation * MathUtils.radiansToDegrees);
        //}
//        }
    }

    public void goTo(Steerable<Vector2> target)
    {
        this.currentTarget = target;
        final Arrive<Vector2> arriveSB = new Arrive<Vector2>(this, target); //
        arriveSB.setTimeToTarget(20f);
        arriveSB.setArrivalTolerance(0.1f);
        arriveSB.setDecelerationRadius(200);
        this.setSteeringBehavior(arriveSB);
    }

    @Override
    public float getMaxLinearSpeed () {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed (float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration () {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration (float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed () {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed (float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration () {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration (float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }
}
