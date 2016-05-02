package com.bujok.ragstoriches.people.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.bujok.ragstoriches.people.Person;

/**
 * Created by Tojoh on 02/05/2016.
 */
public class PersonAnimationController
{

    public static enum AnimationState
    {
        WALKING_RIGHT, WALKING_LEFT, REACH_RIGHT, REACH_LEFT, IDLE_LEFT, IDLE_RIGHT
    }
    private static float ANIMATION_THRESHOLD = 0.1f;
    private static final float SHOPPER_RUNNING_FRAME_DURATION = 0.18f;  //0.18f

    Person parent;
    Vector2 previousLinearVelocity;

    /** Animations **/
    private AnimationState animationState = AnimationState.IDLE_LEFT;
    private Animation runLeftAnimation;
    private Animation runRightAnimation;
    private Animation idleLeftAnimation;
    private Animation idleRightAnimation;
    private Animation reachRightAnimation;
    private Animation reachLeftAnimation;

    public PersonAnimationController(Person parent)
    {
        this.parent = parent;
        this.previousLinearVelocity = new Vector2();
    }

    public void loadTextures(TextureAtlas atlas)
    {
        TextureRegion[] runRightFrames = new TextureRegion[5];
        for (int i = 0; i < 5; i++) {
            runRightFrames[i] = atlas.findRegion("run" + i);
        }
        runRightAnimation = new Animation(SHOPPER_RUNNING_FRAME_DURATION, runRightFrames);

        TextureRegion[] runLeftFrames = new TextureRegion[5];
        for (int i = 0; i < 5; i++) {
            runLeftFrames[i] = new TextureRegion(runRightFrames[i]);
            runLeftFrames[i].flip(true, false);
        }
        runLeftAnimation = new Animation(SHOPPER_RUNNING_FRAME_DURATION, runLeftFrames);

        TextureRegion[] reachRightFrames = new TextureRegion[2];
        for (int i = 0; i < 2; i++) {
            reachRightFrames[i] = atlas.findRegion("reach" + i);
        }
        reachRightAnimation = new Animation(SHOPPER_RUNNING_FRAME_DURATION, reachRightFrames);

        TextureRegion[] reachLeftFrames = new TextureRegion[2];
        for (int i = 0; i < 2; i++) {
            reachLeftFrames[i] = new TextureRegion(reachRightFrames[i]);
            reachLeftFrames[i].flip(true, false);
        }
        reachLeftAnimation = new Animation(SHOPPER_RUNNING_FRAME_DURATION,reachLeftFrames);

        TextureRegion[] idleRightFrames = new TextureRegion[3];
        for(int i = 0; i < 3; i++){
            idleRightFrames[i] = atlas.findRegion("idle" + i);
        }
        idleRightAnimation = new Animation(SHOPPER_RUNNING_FRAME_DURATION,idleRightFrames);

        TextureRegion[] idleLeftFrames = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            idleLeftFrames[i] = new TextureRegion(idleRightFrames[i]);
            idleLeftFrames[i].flip(true, false);
        }
        idleLeftAnimation = new Animation(SHOPPER_RUNNING_FRAME_DURATION,idleLeftFrames);
    }


    public void updateAnimationStateBySpeed(Vector2 newLinearVelocity)
    {
        float threshold = 0.1f;
        if (newLinearVelocity.x > PersonAnimationController.ANIMATION_THRESHOLD)
        {
            this.setAnimationState(PersonAnimationController.AnimationState.WALKING_RIGHT);
        }
        else if (newLinearVelocity.x < -PersonAnimationController.ANIMATION_THRESHOLD)
        {
            this.setAnimationState(PersonAnimationController.AnimationState.WALKING_LEFT);
        }
        else
        {
            if (this.previousLinearVelocity.x > PersonAnimationController.ANIMATION_THRESHOLD)
            {
                this.setAnimationState(PersonAnimationController.AnimationState.IDLE_RIGHT);
            }
            else if (this.previousLinearVelocity.x < -PersonAnimationController.ANIMATION_THRESHOLD)
            {
                this.setAnimationState(PersonAnimationController.AnimationState.IDLE_LEFT);
            }
        }
        this.previousLinearVelocity.x = newLinearVelocity.x;
    }

    public TextureRegion getAnimationTextureRegion(float stateTime)
    {
        TextureRegion textureRegion = null;
        //Gdx.app.log(TAG , "animation date " + animationState.name());
        switch (animationState){
            case WALKING_LEFT:
                textureRegion = runLeftAnimation.getKeyFrame(stateTime,true);
                break;
            case WALKING_RIGHT:
                textureRegion = runRightAnimation.getKeyFrame(stateTime,true);
                break;
            case REACH_LEFT:
                textureRegion = reachLeftAnimation.getKeyFrame(stateTime,true);
                break;
            case REACH_RIGHT:
                textureRegion = reachRightAnimation.getKeyFrame(stateTime,true);
                break;
            case IDLE_LEFT:
                textureRegion = idleLeftAnimation.getKeyFrame(stateTime,true);
                break;
            case IDLE_RIGHT:
                textureRegion = idleRightAnimation.getKeyFrame(stateTime,true);
                break;
        }
        return textureRegion;
    }



    public void setAnimationState(AnimationState animationState) {
        this.animationState = animationState;
    }
}
