package com.bujok.ragstoriches.people.components.moveable;

import com.bujok.ragstoriches.people.components.Speed;
import com.bujok.ragstoriches.utils.Vector2f;

/**
 * Created by joebu on 30/01/2016.
 */
public class Walks implements Movable {

    private float XVelocity = 1;	// velocity value on the X axis
    private float YVelocity = 1;	// velocity value on the Y axis

    public Vector2f currentPos;

    private int xDirection = DIRECTION_RIGHT;
    private int yDirection = DIRECTION_DOWN;

    @Override
    public Vector2f moveTo(Vector2f currentPos, Vector2f target) {
        // complete hack
        if (currentPos == null){currentPos = new Vector2f(0,0);}

        if (currentPos.getX() < target.getX()){
            xDirection = DIRECTION_RIGHT;
        }
        else if (currentPos.getX() > target.getX()) {
            xDirection = DIRECTION_LEFT;
        }
        else xDirection = 0;

        if (currentPos.getY() < target.getY()){
            yDirection = DIRECTION_UP;
        }
        else  if (currentPos.getY() > target.getY()) {
            yDirection = DIRECTION_DOWN;
        }
        else  yDirection = 0;

        currentPos.setX( currentPos.getX() + (XVelocity * xDirection));
        currentPos.setY( currentPos.getY() + (YVelocity * yDirection));

        return currentPos;
/*        // check collision with right wall if heading right
        if (xDirection == DIRECTION_RIGHT
                && shopper.getX() + shopper.getBitmap().getWidth() / 2 >= getWidth()) {
            shopper.getSpeed().toggleXDirection();
        }
        // check collision with left wall if heading left
        if (shopper.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
                && shopper.getX() - shopper.getBitmap().getWidth() / 2 <= 0) {
            shopper.getSpeed().toggleXDirection();
        }
        // check collision with bottom wall if heading down
        if (shopper.getSpeed().getyDirection() == Speed.DIRECTION_DOWN
                && shopper.getY() + shopper.getBitmap().getHeight() / 2 >= getHeight()) {
            shopper.getSpeed().toggleYDirection();
        }
        // check collision with top wall if heading up
        if (shopper.getSpeed().getyDirection() == Speed.DIRECTION_UP
                && shopper.getY() - shopper.getBitmap().getHeight() / 2 <= 0) {
            shopper.getSpeed().toggleYDirection();
        }*/

    }


    public float getXVelocity() {
        return XVelocity;
    }


    public void setXVelocity(float xv) {
        this.XVelocity = xv;
    }

    public float getYVelocity() {
        return YVelocity;
    }

    public void setYVelocity(float yv) {
        this.YVelocity = yv;
    }

    public int getxDirection() {
        return this.xDirection;
    }

    public void setxDirection(int xDirection) {
        this.xDirection = xDirection;
    }

    public int getyDirection() {
        return this.yDirection;
    }

    public void setyDirection(int yDirection) {
        this.yDirection = yDirection;
    }


    public void toggleXDirection() {
        xDirection = xDirection * -1;
    }

    public void toggleYDirection() {
        xDirection = yDirection * -1;
    }
}
