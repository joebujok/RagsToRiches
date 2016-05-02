package com.bujok.ragstoriches.people;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Created by joebu on 30/01/2016.
 */
public class Person extends Image {

    final String TAG = "Person";

    protected String mName;
    protected Integer mAge;
    protected String mID;
    protected Rectangle mImage;
    protected int mSpeed = 0;
    protected Vector3 mCurrentPosition;
    protected boolean mInfoShowing = false;
    protected Table infoBoxTable;

    private static final float SHOPPER_RUNNING_FRAME_DURATION = 0.18f;  //0.18f



    /** Animations **/
    private AnimationState animationState = AnimationState.IDLE_LEFT;
    private Animation runLeftAnimation;
    private Animation runRightAnimation;
    private Animation idleLeftAnimation;
    private Animation idleRightAnimation;
    private Animation reachRightAnimation;
    private Animation reachLeftAnimation;
    private float stateTime;
    private float lastStateTime = 0;
    private static final AnimationState[] animationStates = AnimationState.values();


    // unique mID

    //Texture texture = new Texture(Gdx.files.internal("shopper.png"));


    public Person(String name, Texture texture) {

        super(texture);
        loadTextures();

        this.mName = name;
        this.mAge = MathUtils.random(15,95);
        this.setTouchable(Touchable.enabled);
        this.setBounds(getX(),getY(),getWidth(),getHeight());
        this.scaleBy(2f);


    }

   @Override
    public void draw(Batch batch, float parentAlpha) {
       // ((TextureRegionDrawable)getDrawable()).draw(batch,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation());
       //TextureRegion textureRegion = (TextureRegion) getDrawable();
       //TextureRegionDrawable textureRegionDrawable = (TextureRegionDrawable) getDrawable();



       stateTime += Gdx.graphics.getDeltaTime();
       float interval = 3;
     if (stateTime - lastStateTime > interval){
           animationState = animationStates[MathUtils.random(0, animationStates.length-1)];
            lastStateTime += interval;
       }



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

       //TextureRegion textureRegion = runLeftAnimation.getKeyFrame(stateTime,true);
       ((TextureRegionDrawable)getDrawable()).setRegion(textureRegion);
       //TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(textureRegion);
       //textureRegionDrawable.draw(batch,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation() );
     //  batch.draw((TextureRegion) getDrawable(),getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation());
       if(infoBoxTable != null){
           infoBoxTable.setPosition(((this.getWidth() / 2 )+ this.getX()) - (infoBoxTable.getWidth() / 2), this.getY() + this.getHeight()  + infoBoxTable.getHeight());
       }


      //  batch.draw(texture,this.getX(),getY());
        super.draw(batch, parentAlpha);
    }

    public String getId() {
        return mID;
    }

    public void setId(String id) {
        this.mID = id;
    }

    public Vector3 getCurrentPosition() {
        return mCurrentPosition;
    }

    public void setCurrentPosition(Vector3 currentPosition) {
        this.mCurrentPosition = currentPosition;
    }

    public void toggleInfoBox(Stage stage, Skin skin){
        if(infoBoxTable != null){
            infoBoxTable.remove();
            infoBoxTable = null;
        }else{
            infoBoxTable = new Table();
            //set background color of table
            Pixmap pm1 = new Pixmap(1, 1, Pixmap.Format.RGB565);
            pm1.setColor(new Color(0x0190C3D4));
            pm1.fill();
            infoBoxTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));

            Label nameLabel = new Label("Name : " + mName,skin);
            nameLabel.setAlignment(Align.left);
            final Label moneyLabel = new Label("Money : £123.45" ,skin);
            moneyLabel.setAlignment(Align.left);

            TextButton closeTextButton = new TextButton("Close",skin,"default");
            closeTextButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    infoBoxTable.remove();
                }
            });
            closeTextButton.align(Align.right);
            infoBoxTable.add(closeTextButton);
            infoBoxTable.row();
            infoBoxTable.add(nameLabel);
            infoBoxTable.row();
            infoBoxTable.add(moneyLabel);
            Array<Cell> cells = infoBoxTable.getCells();
            for (Cell c :cells) {
                if(c != cells.get(0)){
                    c.pad(0,20,0,20);
                    c.align(Align.left);
                }

            }

            //table.setBackground("red");

            //infoBoxTable.debug();
            infoBoxTable.pack();
            infoBoxTable.setPosition(((this.getWidth() / 2 )+ this.getX()) - (infoBoxTable.getWidth() / 2), this.getY() + this.getHeight()  + infoBoxTable.getHeight());
            stage.addActor(infoBoxTable);
            Label.LabelStyle labelStyle = new Label.LabelStyle();

        }

    }
    private void loadTextures() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/shopper.pack"));

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

    public String getName() {
        return mName;
    }

    public Integer getAge() {
        return mAge;
    }

    public void setAnimationState(AnimationState animationState) {
        this.animationState = animationState;
    }

    public enum AnimationState{
        WALKING_RIGHT, WALKING_LEFT, REACH_RIGHT, REACH_LEFT, IDLE_LEFT, IDLE_RIGHT
    }
}
