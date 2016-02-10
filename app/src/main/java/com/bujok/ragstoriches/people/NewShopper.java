package com.bujok.ragstoriches.people;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.bujok.ragstoriches.people.components.moveable.Movable;
import com.bujok.ragstoriches.people.components.moveable.Walks;
import com.bujok.ragstoriches.utils.Vector2f;

import java.util.Random;
import java.util.jar.Attributes;
import java.util.logging.Logger;

import static com.bujok.ragstoriches.utils.Random.getRandInteger;

/**
 * Created by joebu on 30/01/2016.
 */
public class NewShopper extends NewPerson {

    private static final String TAG = "ShopperClass";
    private boolean mCanAffordShop;
    private int mMoney;
    public state currentState;
    protected Movable movable;
    private Vector2f nextTargetLocation;

     public NewShopper(Context context, String name, Bitmap bitmap, int x, int y) {
        super(context, name, bitmap, x , y);
        Random rand = new Random();
        Integer pounds = rand.nextInt((95 - 15) + 1) + 15;
        Integer pence = rand.nextInt((99 - 0) + 1) + 0;
        this.mMoney = (pounds * 100) + pence;
        this.mCanAffordShop = true;
        this.currentState = state.WAITING;
        this.movable = new Walks();


    }

    public void update(){

        switch (currentState){
            case BROWSING:
                Log.v(TAG, mName + " (shopper) is in browsing state");
                Browsing();
                break;

            case WAITING:
                if(nextTargetLocation == null){
                    nextTargetLocation =  new Vector2f(getRandInteger(0,500), getRandInteger(0,500));
                }
                this.moveTo(this.drawable.getCurrentPosition(),nextTargetLocation);
                this.currentState = state.BROWSING;
                break;


            case ENTERING_SHOP:

                break;
            case LEAVING_SHOP:

                break;
            case PURCHASING:

                break;

        }

    }

    private void Browsing(){
       // if(tthis.drawable.getCurrentPosition() != )
        Vector2f currentPos = this.drawable.getCurrentPosition();
        Log.d(TAG, mName + " (shopper) is at " + currentPos + "and trying to get to " +nextTargetLocation);

        // todo create comparitor for float....
        if(currentPos.getX() nextTargetLocation){
            this.moveTo(this.drawable.getCurrentPosition(), nextTargetLocation);
        }
        else{
            nextTargetLocation = null;
            this.currentState = state.WAITING;
        }


    }



    @Override
    public void draw() {

    }

    @Override
    public Vector2f getCurrentPosition() {
        return super.getCurrentPosition();
    }

    @Override
    public void setCurrentPosition(Vector2f currentPosition) {
        super.setCurrentPosition(currentPosition);
    }

    @Override
    public Vector2f moveTo(Vector2f currentPosition, Vector2f targetPosition) {
        this.drawable.setCurrentPosition(this.movable.moveTo(currentPosition,targetPosition));
        return null;
    }

    public enum state{
        BROWSING, PURCHASING, ENTERING_SHOP, WAITING, LEAVING_SHOP
    }
}
