package com.bujok.ragstoriches.people;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by joebu on 30/01/2016.
 */
public class Person2 extends Image {


    protected String mName;
    protected Integer mAge;
    protected String mID;
    protected Rectangle mImage;
    protected int mSpeed = 0;
    protected Vector3 mCurrentPosition;
    protected boolean mInfoShowing = false;

    // unique mID

    //Texture texture = new Texture(Gdx.files.internal("shopper.png"));


    public Person2(String name,Texture texture) {

        super(texture);
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
       TextureRegionDrawable textureRegionDrawable = (TextureRegionDrawable) getDrawable();
       textureRegionDrawable.draw(batch,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation() );
     //  batch.draw((TextureRegion) getDrawable(),getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation());


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

    public String getName() {
        return mName;
    }

    public Integer getAge() {
        return mAge;
    }
}
