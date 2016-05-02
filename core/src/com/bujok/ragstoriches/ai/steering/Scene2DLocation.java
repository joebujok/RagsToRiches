package com.bujok.ragstoriches.ai.steering;

import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.bujok.ragstoriches.ai.utilities.SteeringUtilities;

public class Scene2DLocation implements Location<Vector2> {

    Vector2 position;
    float orientation;

    public Scene2DLocation ()
    {
        this.position = new Vector2();
        this.orientation = 0;
    }

    @Override
    public Vector2 getPosition () {
        return position;
    }

    @Override
    public float getOrientation () {
        return orientation;
    }

    @Override
    public void setOrientation (float orientation) {
        this.orientation = orientation;
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

}