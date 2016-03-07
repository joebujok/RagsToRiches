package com.bujok.ragstoriches.people;



import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by joebu on 30/01/2016.
 */
public class Person   {


    protected String mName;
    protected Integer mAge;
    protected String mID;
    protected Rectangle mImage;
    protected int mSpeed = 0;
    protected Vector3 mCurrentPosition;
    protected boolean mInfoShowing = false;

    // unique mID




    public Person(String name, Rectangle image, float x, float y) {

        this.mName = name;
        this.mAge = MathUtils.random(15,95);
        this.mImage = image;
        this.mCurrentPosition = new Vector3(x, y,0);
        this.mCurrentPosition.set(x,y,0);




    }

    public void render(float delta){

        this.mCurrentPosition.set(this.mCurrentPosition.x + mSpeed * delta,this.mCurrentPosition.y,0);

    }

    public boolean checkIfTouched(Vector3 touch){
        if(touch.x >= mCurrentPosition.x && touch.x < (mCurrentPosition.x + mImage.getWidth())
                && touch.y >= mCurrentPosition.y && touch.y < (mCurrentPosition.y + mImage.getHeight())){
            mInfoShowing = !mInfoShowing;
            return true;
        }
        return false;

    }

    public boolean isInfoShowing(){
        return mInfoShowing;
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

    public float getHeight(){
        return mImage.getHeight();
    }
    public float getWidth(){
        return mImage.getWidth();
    }

    public void setCurrentPosition(Vector3 currentPosition) {
        this.mCurrentPosition = currentPosition;
    }
}
