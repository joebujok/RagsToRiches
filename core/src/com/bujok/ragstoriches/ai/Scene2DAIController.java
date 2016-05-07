package com.bujok.ragstoriches.ai;

import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bujok.ragstoriches.ai.pathfinding.FlatTiledAStarTest;
import com.bujok.ragstoriches.ai.steering.Scene2DAIEntity;
import com.bujok.ragstoriches.ai.steering.S2DAIEntityFixed;
import com.bujok.ragstoriches.ai.steering.S2DAIEntityMoving;

import java.util.List;

/**
 * Created by Tojoh on 01/05/2016.
 */
public class Scene2DAIController {
    private final FlatTiledAStarTest pathFinder;
    private float lastUpdateTime;
    private final Scene2DAIEntity steering;

    public Scene2DAIController(Actor parent, boolean isFixed) {
        if (isFixed) {
            this.steering = new S2DAIEntityFixed(parent, false);
        } else {
            this.steering = new S2DAIEntityMoving(parent, false);
        }

        this.pathFinder = new FlatTiledAStarTest(parent.getStage());
        this.pathFinder.create();
    }

    public void update(float actDelta) {
        //if we are not paused
        GdxAI.getTimepiece().update(actDelta);
        float time = GdxAI.getTimepiece().getTime();
        if (lastUpdateTime != time) {
            lastUpdateTime = time;
            this.steering.update(time);
        }
    }

    public void follow(IBasicAI target)
    {
        if (this.steering instanceof S2DAIEntityMoving) {
            Scene2DAIController targetController = target.getController();
            ((S2DAIEntityMoving) this.steering).follow(targetController.getSteeringEntity());
        }
    }

    public void moveTo(Vector2 location)
    {
        if (this.steering instanceof S2DAIEntityMoving)
        {
            ((S2DAIEntityMoving) this.steering).moveTo(location);
        }
    }

    public Scene2DAIEntity getSteeringEntity() {
        return this.steering;
    }

    public Vector2 getLinearVelocity() {
        return this.steering.getLinearVelocity();
    }

    public void draw(Batch batch, boolean visible)
    {
        this.pathFinder.render(batch, visible);
    }

    public void updatePath()
    {
        this.pathFinder.updatePath();
    }

    public List<Vector2> getPath(int pathIndex)
    {
        return this.pathFinder.getPath(pathIndex);
    }
}
