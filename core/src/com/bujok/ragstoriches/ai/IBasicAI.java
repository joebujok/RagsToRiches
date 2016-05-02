package com.bujok.ragstoriches.ai;

/**
 * Created by Tojoh on 01/05/2016.
 */
public interface IBasicAI
{
    // Basic AI commands that can be invoked directly
    public void goTo(IBasicAI target);

    public Scene2DAIController getController();
}
