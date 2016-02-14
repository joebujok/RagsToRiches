package com.bujok.ragstoriches.people.components.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.bujok.ragstoriches.utils.Vector2f;

/**
 * Created by joebu on 08/02/2016.
 */
public interface Drawable {

    public void draw(Canvas canvas);

    public Vector2f getCurrentPosition();

    public void setCurrentPosition(Vector2f currentPosition);

    public void setBitMap(Bitmap bitMap);

    public Bitmap getBitmap();

    public int getImageHeight();

    public int getImageWidth();

}
