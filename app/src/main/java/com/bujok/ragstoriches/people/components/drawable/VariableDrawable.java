package com.bujok.ragstoriches.people.components.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.bujok.ragstoriches.utils.Vector2f;

/**
 * Created by joebu on 08/02/2016.
 */
public class VariableDrawable implements Drawable {

    Vector2f currentPosition;
    Bitmap bitmap;
    int imageHeight;
    int imageWidth;

    public VariableDrawable(Vector2f currentPosition, Bitmap bitmap) {
        this.currentPosition = currentPosition;
        this.bitmap = bitmap;
        imageHeight = bitmap.getHeight();
        imageWidth = bitmap.getWidth();
    }

    @Override
    public void draw(Canvas canvas) {
       canvas.drawBitmap(bitmap,currentPosition.getX() - (bitmap.getWidth() / 2), currentPosition.getY() - (bitmap.getHeight() / 2), null);
    }

    public Vector2f getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Vector2f currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public void setBitMap(Bitmap bitMap) {
        this.bitmap = bitMap;
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public int getImageHeight() {
        return this.imageHeight;
    }

    @Override
    public int getImageWidth() {
        return this.imageWidth;
    }
}
