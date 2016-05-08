package com.bujok.ragstoriches.ai;

import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.bujok.ragstoriches.ai.pathfinding.FlatTiledAStarTest;
import com.bujok.ragstoriches.ai.steering.DiscreteSteeringBehaviour;
import com.bujok.ragstoriches.people.Person;

import java.util.List;

/**
 * Created by Tojoh on 01/05/2016.
 */
public class Scene2DAIController
{
    private final FlatTiledAStarTest pathFinder;
    private float lastUpdateTime;
    private final DiscreteSteeringBehaviour steering;

    public Scene2DAIController(Person parent, boolean isFixed) {
        if (isFixed) {
            this.steering = new DiscreteSteeringBehaviour(parent);
        } else {
            this.steering = new DiscreteSteeringBehaviour(parent);
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
        //
    }

    public void moveTo(Vector2 location) {
        this.steering.moveTo(location);
    }

    public void followPath(List<Vector2> waypoints)
    {
        this.steering.followPath(waypoints);
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

    public Vector2 getLinearVelocity()
    {
        return this.steering.getLinearVelocity();
    }
}
