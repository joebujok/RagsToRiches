package com.bujok.ragstoriches.people.components.drawable;

import com.bujok.ragstoriches.utils.Vector2f;

/**
 * Created by joebu on 08/02/2016.
 */
public class VariableDrawable implements Drawable {

    Vector2f currentPosition;

    @Override
    public void draw() {

    }

    public Vector2f getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Vector2f currentPosition) {
        this.currentPosition = currentPosition;
    }
}
