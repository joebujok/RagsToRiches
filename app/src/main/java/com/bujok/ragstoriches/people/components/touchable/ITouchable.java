package com.bujok.ragstoriches.people.components.touchable;

import com.bujok.ragstoriches.utils.Vector2f;

/**
 * Created by joe.bujok on 15/02/2016.
 */
public interface ITouchable {

    public boolean isTouched();

    public void setTouched(boolean touched);

    public void setCurrentPosition(Vector2f position);
}
