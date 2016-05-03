package com.bujok.ragstoriches.ai;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Tojoh on 01/05/2016.
 */
public interface IBasicAI
{
    // Basic AI commands that can be invoked directly
    public void goTo(IBasicAI target);

    public void goTo(Vector2 poi);

    public Scene2DAIController getController();

    public Vector2 getLinearVelocity();

    public void browseTo(Vector2 poi);
}
