package com.bujok.ragstoriches.ai;

import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bujok.ragstoriches.ai.steering.Scene2DAIEntity;
import com.bujok.ragstoriches.ai.steering.S2DAIEntityFixed;
import com.bujok.ragstoriches.ai.steering.S2DAIEntityMoving;

/**
 * Created by Tojoh on 01/05/2016.
 */
public class Scene2DAIController
{
    private float lastUpdateTime;
    private final Scene2DAIEntity steering;

    public Scene2DAIController(Actor parent, boolean isFixed)
    {
        if (isFixed)
        {
            this.steering = new S2DAIEntityFixed(parent, false);
        }
        else
        {
            this.steering = new S2DAIEntityMoving(parent, false);
        }

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
        if (this.steering instanceof S2DAIEntityMoving)
        {
            Scene2DAIController targetController = target.getController();
            ((S2DAIEntityMoving)this.steering).goTo(targetController.getSteeringEntity());
        }
    }

    public Scene2DAIEntity getSteeringEntity()
    {
        return this.steering;
    }

    public Vector2 getLinearVelocity()
    {
        return this.steering.getLinearVelocity();
    }
}
