package com.bujok.ragstoriches.ai.steering;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.bujok.ragstoriches.ai.utilities.SteeringUtilities;
import com.bujok.ragstoriches.people.Person;

import java.util.List;

/**
 *
 */
public class DiscreteSteeringBehaviour {

    private static final SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
    private final Person parent;

    Vector2 position;  // like scene2d centerX and centerY, but we need a vector to implement Steerable
    float speed;
    float walkSpeed = 100f;

    private Vector2 target;
    private List<Vector2> path;
    private int currentPointIndex =0;



    public enum Behaviour{IDLE, FOLLOW_PATH, GOTO};

    private Behaviour state = Behaviour.IDLE;

    public DiscreteSteeringBehaviour(Person parent)
    {
        this.position = new Vector2();
        this.parent = parent;
        //this.setOrigin(region.getRegionWidth() * .5f, region.getRegionHeight() * .5f);
    }

    public Vector2 getPosition ()
    {
        return position;
    }


    public void update (float delta)
    {
        position.set(this.parent.getX(Align.right), this.parent.getY(Align.bottom));
        if (position != null && this.target != null && position.dst(this.target) < 0.001)
        {
            if (this.state == Behaviour.GOTO ||
                    (this.state == Behaviour.FOLLOW_PATH && this.currentPointIndex == this.path.size() - 1))
            {
                // we have arrived lets notify
                this.idle();
                this.parent.notifyArrivalAtTarget();
            }
            else if (this.state == Behaviour.FOLLOW_PATH)
            {
                this.currentPointIndex++;
                this.moveToTarget(this.path.get(this.currentPointIndex));
            }
        }
    }

    public Vector2 getLinearVelocity()
    {
        // this doesnt need to be exact as we are just using it for the prevalent direction
        if (target != null && position != null && this.speed > 0) {
            Vector2 moveDelta = new Vector2(target.x - position.x, target.y - position.y);
            final float xSpeed = moveDelta.x / Math.abs(moveDelta.x + moveDelta.y) * this.speed;
            final float ySpeed = moveDelta.y / Math.abs(moveDelta.x + moveDelta.y) * this.speed;
            return new Vector2(xSpeed, ySpeed);
        }
        return new Vector2();
    }

    private void moveToTarget(Vector2 target)
    {
        this.target = target;
        this.speed = this.walkSpeed;
        this.move();
    }

    private void idle()
    {
        this.state = Behaviour.IDLE;
        this.speed = 0;
        this.target = null;
        this.path = null;
        this.parent.clearActions();
    }

    private void move()
    {
        if (this.state != Behaviour.IDLE) {
            MoveByAction mba = new MoveByAction();
            Vector2 moveDelta = new Vector2(target.x - position.x, target.y - position.y);
            mba.setAmount(moveDelta.x, moveDelta.y);
            float dst = target.dst(this.position);
            float duration = dst / this.speed;
            mba.setDuration(duration);
            SequenceAction sa = new SequenceAction(mba);
            this.parent.addAction(sa);
        }
    }


    public void follow(Vector2 target)
    {
        this.moveTo(target);
    }

    public void moveTo(Vector2 target)
    {
        this.path = null;
        this.state = Behaviour.GOTO;
        this.moveToTarget(target);
    }

    public void followPath(List<Vector2> waypoints)
    {
        this.path = waypoints;
        this.currentPointIndex = 0;
        this.state = Behaviour.FOLLOW_PATH;
        this.moveToTarget(this.path.get(this.currentPointIndex));
    }
}