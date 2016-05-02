package com.bujok.ragstoriches.ai;

import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bujok.ragstoriches.ai.steering.Scene2DSteeringEntity;

/**
 * Created by Tojoh on 01/05/2016.
 */
public class Scene2DAIController
{
    private float lastUpdateTime;
    private final Scene2DSteeringEntity steering;

    public Scene2DAIController(Actor parent)
    {
        this.steering = new Scene2DSteeringEntity(parent, false);
    }

    public void update(float actDelta)
    {
        //if we are not paused
        GdxAI.getTimepiece().update(actDelta);
        float time = GdxAI.getTimepiece().getTime();
        if (lastUpdateTime != time)
        {
            lastUpdateTime = time;
            this.steering.update(time);
        }
    }

    public void goTo(IBasicAI target)
    {
        Scene2DAIController targetController = target.getController();
        this.steering.goTo(targetController.getSteeringEntity());
    }

    public Scene2DSteeringEntity getSteeringEntity()
    {
        return this.steering;
    }
}
