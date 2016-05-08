package com.bujok.ragstoriches.ai.steering.old;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Tojoh on 02/05/2016.
 */
public class S2DAIEntityFixed extends Scene2DAIEntity
{
    protected float maxLinearSpeed = 2;
    protected float maxLinearAcceleration = 500;
    protected float maxAngularSpeed = 5;
    protected float maxAngularAcceleration = 10;

    private Steerable<Vector2> currentTarget;

    public S2DAIEntityFixed(Actor parent, boolean independentFacing)
    {
        super(parent, false);

        // set the initial fixed position
        position.set(this.parent.getX(Align.center), this.parent.getY(Align.center));
    }

    public void update (float delta)
    {
        // we are fixed so we don't need to update position.
        position.set(this.parent.getX(Align.center), this.parent.getY(Align.center));
    }

    @Override
    public float getMaxLinearSpeed () {
        return 0f;
    }

    @Override
    public void setMaxLinearSpeed (float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration () {
        return 0f;
    }

    @Override
    public void setMaxLinearAcceleration (float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed () {
        return 0f;
    }

    @Override
    public void setMaxAngularSpeed (float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration () {
        return 0f;
    }

    @Override
    public void setMaxAngularAcceleration (float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }
}
