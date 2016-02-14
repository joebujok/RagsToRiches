package com.bujok.ragstoriches.people;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.bujok.ragstoriches.db.MyDbConnector;
import com.bujok.ragstoriches.people.components.drawable.Drawable;
import com.bujok.ragstoriches.people.components.drawable.VariableDrawable;
import com.bujok.ragstoriches.people.components.moveable.Movable;
import com.bujok.ragstoriches.people.components.moveable.Walks;
import com.bujok.ragstoriches.utils.Vector2f;

import java.util.Random;

/**
 * Created by joebu on 30/01/2016.
 */
public class Person implements Movable, Drawable {

    private final Bitmap bitmap;
   // private final int x;
   // private final int y;
    protected String mName;
    protected Integer mAge;
   // protected final Context mContext;
    protected SQLiteDatabase mDb;
    protected String id;			// unique id
    protected Drawable drawable;
    protected Movable movable;


    public Person(String name, Bitmap bitmap, int x, int y) {
       // this.mContext = context;
        this.mName = name;
        Random rand = new Random();
        this.mAge = rand.nextInt((95 - 15) + 1) + 15;
        this.bitmap = bitmap;
      //  this.x = x;
      //  this.y = y;
        this.movable = new Walks();
        this.drawable = new VariableDrawable(new Vector2f(x,y),bitmap);
        //this.drawable.setCurrentPosition(new Vector2f(x,y));
        //this.speed = new Speed();
        //MyDbConnector db = new MyDbConnector(mContext.getApplicationContext()).getWritableDatabase();
      //  mDb = new MyDbConnector(mContext.getApplicationContext()).getWritableDatabase();
    }

    public void draw(Canvas canvas) {
        this.drawable.draw(canvas);
       // canvas.drawBitmap(bitmap, this.drawable.getCurrentPosition().getX() - (bitmap.getWidth() / 2), this.drawable.getCurrentPosition().getY() - (bitmap.getHeight() / 2), null);
    }

    public Movable getMovable() {
        return movable;
    }

    public void setMovable(Movable movable) {
        this.movable = movable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Movable getMoveable() {
        return movable;
    }

    public void setMoveable(Movable moveable) {
        movable = moveable;
    }
    @Override
    public Vector2f getCurrentPosition() {
       return this.drawable.getCurrentPosition();
    }
    public void setCurrentPosition(Vector2f currentPosition) {
        this.drawable.setCurrentPosition(currentPosition);
    }

    @Override
    public void setBitMap(Bitmap bitMap) {
        this.drawable.setBitMap(bitMap);
    }

    @Override
    public Bitmap getBitmap() {
        return this.drawable.getBitmap();
    }

    @Override
    public int getImageHeight() {
        return this.drawable.getImageHeight();
    }

    @Override
    public int getImageWidth() {
        return this.drawable.getImageWidth();
    }


    @Override
    public Vector2f moveTo(Vector2f currentPosition, Vector2f targetPosition) {
        this.drawable.setCurrentPosition(this.movable.moveTo(currentPosition,targetPosition));
        return null;
    }
}
