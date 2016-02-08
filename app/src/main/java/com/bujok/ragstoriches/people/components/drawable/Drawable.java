package com.bujok.ragstoriches.people.components.drawable;

import com.bujok.ragstoriches.utils.Vector2f;

/**
 * Created by joebu on 08/02/2016.
 */
public interface Drawable {

    public void draw();

    public Vector2f getCurrentPosition();

    public void setCurrentPosition(Vector2f currentPosition);

}
