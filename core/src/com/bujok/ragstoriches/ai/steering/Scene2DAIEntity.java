package com.bujok.ragstoriches.ai.steering;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.bujok.ragstoriches.ai.utilities.SteeringUtilities;
import com.bujok.ragstoriches.people.Person;


public abstract class Scene2DAIEntity implements Steerable<Vector2> {

    protected final SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
    SteeringBehavior<Vector2> steeringBehavior;

    Vector2 position;  // like scene2d centerX and centerY, but we need a vector to implement Steerable
    float boundingRadius;
    boolean tagged;

    float previousLinearSpeedX = 0;
    boolean independentFacing;
    Actor parent;

    Vector2 linearVelocity;
    float angularVelocity;

    public Scene2DAIEntity(Actor parent, boolean independentFacing)
    {
        this.parent = parent;
        this.independentFacing = independentFacing;
        this.position = new Vector2();
        this.boundingRadius = 10f; //(this.parent.getWidth() + this.parent.getHeight()) / 4f;
        this.linearVelocity = new Vector2();
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

    abstract public void update (float delta);

}