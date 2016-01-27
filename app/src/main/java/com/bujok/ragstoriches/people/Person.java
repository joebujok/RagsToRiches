package com.bujok.ragstoriches.people;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.bujok.ragstoriches.db.MyDbConnector;
import com.bujok.ragstoriches.people.components.Speed;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by joebu on 07/01/2016.
 */
public class Person {

    protected String mName;
    protected Integer mAge;
    protected final Context mContext;
    protected SQLiteDatabase mDb;

    protected Bitmap bitmap;	// the actual bitmap
    protected int x;			// the X coordinate
    protected int y;			// the Y coordinate
    protected boolean touched;	// if droid is touched/picked up
    protected Speed speed;	// the speed with its directions

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public Integer getAge() {
        return mAge;
    }

    public void setAge(Integer mAge) {
        this.mAge = mAge;
    }


    public Person(Context context, String name, Bitmap bitmap, int x, int y){
        this.mContext = context;
        this.mName = name;
        Random rand = new Random();
        this.mAge = rand.nextInt((95 - 15) + 1) + 15;
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.speed = new Speed();
        //MyDbConnector db = new MyDbConnector(mContext.getApplicationContext()).getWritableDatabase();
        mDb = new MyDbConnector(mContext.getApplicationContext()).getWritableDatabase();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    }

    /**
     * Method which updates the droid's internal state every tick
     */
    public void update() {
        if (!touched) {
            x += (speed.getXv() * speed.getxDirection());
            y += (speed.getYv() * speed.getyDirection());
        }
    }


    /**
     * Handles the {@link MotionEvent.ACTION_DOWN} event. If the event happens on the
     * bitmap surface then the touched state is set to <code>true</code> otherwise to <code>false</code>
     * @param eventX - the event's X coordinate
     * @param eventY - the event's Y coordinate
     */
    public void handleActionDown(int eventX, int eventY) {
        if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2))) {
            if (eventY >= (y - bitmap.getHeight() / 2) && (y <= (y + bitmap.getHeight() / 2))) {
                // droid touched
                setTouched(true);
            } else {
                setTouched(false);
            }
        } else {
            setTouched(false);
        }

    }

}
