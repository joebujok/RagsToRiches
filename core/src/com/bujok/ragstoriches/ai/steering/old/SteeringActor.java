package com.bujok.ragstoriches.ai.steering.old;

/*******************************************************************************
 * Copyright 2014 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.math.Vector2;
        import com.badlogic.gdx.ai.steer.Steerable;
        import com.badlogic.gdx.ai.steer.SteeringAcceleration;
        import com.badlogic.gdx.ai.steer.SteeringBehavior;
        import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
        import com.badlogic.gdx.utils.Align;
import com.bujok.ragstoriches.ai.utilities.SteeringUtilities;

/** A SteeringActor is a scene2d {@link Actor} implementing the {@link Steerable} interface.
 *
 * @autor davebaol */
public class SteeringActor implements Steerable<Vector2> {

    private static final SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
    private final Actor parent;

    Vector2 position;  // like scene2d centerX and centerY, but we need a vector to implement Steerable
    Vector2 linearVelocity;
    float angularVelocity;
    float boundingRadius;
    boolean tagged;

    float maxLinearSpeed = 100;
    float maxLinearAcceleration = 200;
    float maxAngularSpeed = 5;
    float maxAngularAcceleration = 10;

    boolean independentFacing;

    SteeringBehavior<Vector2> steeringBehavior;
    private Location<Vector2> target;

    public SteeringActor (Actor parent, boolean independentFacing) {
        this.parent = parent;
        this.independentFacing = independentFacing;
        this.position = new Vector2();
        this.linearVelocity = new Vector2();

        this.boundingRadius = (this.parent.getWidth() + this.parent.getHeight()) / 4f;
    }

    @Override
    public Vector2 getPosition () {
        return position;
    }

    @Override
    public float getOrientation () {
        return this.parent.getRotation() * MathUtils.degreesToRadians;
    }

    @Override
    public void setOrientation (float orientation) {
        this.parent.setRotation(orientation * MathUtils.radiansToDegrees);
    }

    @Override
    public Vector2 getLinearVelocity () {
        return linearVelocity;
    }

    @Override
    public float getAngularVelocity () {
        return angularVelocity;
    }

    public void setAngularVelocity (float angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    @Override
    public float getBoundingRadius () {
        return boundingRadius;
    }

    @Override
    public boolean isTagged () {
        return tagged;
    }

    @Override
    public void setTagged (boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public Location<Vector2> newLocation () {
        return new Scene2DLocation();
    }

    @Override
    public float vectorToAngle (Vector2 vector) {
        return SteeringUtilities.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector (Vector2 outVector, float angle) {
        return SteeringUtilities.angleToVector(outVector, angle);
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

    @Override
    public float getZeroLinearSpeedThreshold () {
        return 0.001f;
    }

    @Override
    public void setZeroLinearSpeedThreshold (float value) {
        throw new UnsupportedOperationException();
    }

    public boolean isIndependentFacing () {
        return independentFacing;
    }

    public void setIndependentFacing (boolean independentFacing) {
        this.independentFacing = independentFacing;
    }

    public SteeringBehavior<Vector2> getSteeringBehavior () {
        return steeringBehavior;
    }

    public void setSteeringBehavior (SteeringBehavior<Vector2> steeringBehavior) {
        this.steeringBehavior = steeringBehavior;
    }

    public void update (float delta)
    {
        position.set(this.parent.getX(Align.center), this.parent.getY(Align.center));
        if (steeringBehavior != null) {

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

            wrapAround(position, 1200, 750);
            this.parent.setPosition(position.x, position.y, Align.center);
        }
    }

    // the display area is considered to wrap around from top to bottom
    // and from left to right
    protected static void wrapAround (Vector2 pos, float maxX, float maxY)
    {
        if (pos.x > maxX) pos.x = 0.0f;

        if (pos.x < 0) pos.x = maxX;

        if (pos.y < 0) pos.y = maxY;

        if (pos.y > maxY) pos.y = 0.0f;
    }

    private void applySteering (SteeringAcceleration<Vector2> steering, float time) {
        // Update position and linear velocity. Velocity is trimmed to maximum speed
        position.mulAdd(linearVelocity, time);
        linearVelocity.mulAdd(steering.linear, time).limit(getMaxLinearSpeed());

        // Update orientation and angular velocity
        if (independentFacing) {
            this.parent.setRotation(this.parent.getRotation() + (angularVelocity * time) * MathUtils.radiansToDegrees);
            angularVelocity += steering.angular * time;
        } else {
            // If we haven't got any velocity, then we can do nothing.
            if (!linearVelocity.isZero(getZeroLinearSpeedThreshold())) {
                float newOrientation = vectorToAngle(linearVelocity);
                angularVelocity = (newOrientation - this.parent.getRotation() * MathUtils.degreesToRadians) * time; // this is superfluous if independentFacing is always true
                this.parent.setRotation(newOrientation * MathUtils.radiansToDegrees);
            }
        }
    }

    public void follow(Steerable<Vector2> target)
    {
        this.target = target;
        final Arrive<Vector2> arriveSB = new Arrive<Vector2>(this, target); //
        arriveSB.setTimeToTarget(0.1f) //
                .setArrivalTolerance(0.001f) //
                .setDecelerationRadius(80);
        this.setSteeringBehavior(arriveSB);
    }

    public void moveTo(Vector2 target)
    {
        this.target = new Scene2DLocation(target);
        final Seek<Vector2> seekB = new Seek<Vector2>(this, this.target);
        this.setSteeringBehavior(seekB);
        Gdx.app.debug("Steering", "Moving to " + target);
    }

    public void followPath(Vector2 target)
    {

    }

}