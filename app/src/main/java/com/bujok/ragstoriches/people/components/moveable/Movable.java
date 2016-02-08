package com.bujok.ragstoriches.people.components.moveable;

import com.bujok.ragstoriches.utils.Vector2f;

/**
 * Created by joebu on 30/01/2016.
 */
public interface Movable {
    /**
     * Delegates the movement to the supporting chassis and
     * tries to move the unit to <code>vector2f</code>
     * * @param vector2f
     */
    int DIRECTION_RIGHT	= 1;
    int DIRECTION_LEFT	= -1;
    int DIRECTION_UP	= 1;
    int DIRECTION_DOWN	= -1;

    public Vector2f moveTo(Vector2f currentPosition, Vector2f targetPosition);
}
