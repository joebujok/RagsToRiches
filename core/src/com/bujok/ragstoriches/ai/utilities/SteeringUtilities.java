package com.bujok.ragstoriches.ai.utilities;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Tojoh on 01/05/2016.
 */
public class SteeringUtilities
{
    public static float vectorToAngle(Vector2 vector)
    {
        return (float)Math.atan2(-vector.x, vector.y);
    }

    public static Vector2 angleToVector(Vector2 outVector, float angle)
    {
        outVector.x = -(float)Math.sin(angle);
        outVector.y = (float)Math.cos(angle);
        return outVector;
    }
}
