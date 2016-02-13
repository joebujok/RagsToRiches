package com.bujok.ragstoriches;
/**
 *
 */

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.bujok.ragstoriches.building.Shop;
import com.bujok.ragstoriches.people.Shopper;
import com.bujok.ragstoriches.utils.Globals;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author impaler
 * This is the main surface that handles the ontouch events and draws
 * the image to the screen.
 */
public class MainGamePanel extends SurfaceView implements
        SurfaceHolder.Callback {

    private static final String TAG = MainGamePanel.class.getSimpleName();

    private MainThread thread;
    private Shopper shopper;
    private ArrayList<Shopper> mShopperList ;
    private ArrayList<Shop> mShopList ;
    public int w;
    public int h;
    private Globals globals;


    public MainGamePanel(Context context) {
        super(context);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        // create shopper and load bitmap
        this.globals = Globals.getInstance();
        this.globals.createShopperList();
        this.globals.createShopList();

        mShopperList = globals.getShopperList();
        mShopList = globals.getShopList();

        mShopList.add(new Shop(context,1, 5,100));

        Shopper shopper = new Shopper( "Hubert Winkledonk",BitmapFactory.decodeResource(getResources(), R.drawable.shopper), 400, 400);
        Shopper shopper2 = new Shopper( "Wilbur Winkledonk",BitmapFactory.decodeResource(getResources(), R.drawable.shopper), 250,250);

        // create the game loop thread
        thread = new MainThread(getHolder(), this);

        // make the GamePanel focusable so it can handle events
        setFocusable(true);


        mShopperList.add(shopper);
        mShopperList.add(shopper2);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        this.w = w;
        this.h = h;

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // at this point the surface is created and
        // we can safely start the game loop

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Surface is being destroyed");
        // tell the thread to shut down and wait for it to finish
        // this is a clean shutdown
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // try again shutting down the thread
            }
        }
        Log.d(TAG, "Thread was shut down cleanly");
    }

/*    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // delegating event handling to the shopper
            shopper.handleActionDown((int)event.getX(), (int)event.getY());

            // check if in the lower part of the screen we exit
            if (event.getY() > getHeight() - 50) {
                thread.setRunning(false);
                ((Activity)getContext()).finish();
            } else {
                Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
            }
        } if (event.getAction() == MotionEvent.ACTION_MOVE) {
            // the gestures
            if (shopper.isTouched()) {
                // the shopper was picked up and is being dragged
                shopper.setX((int)event.getX());
                shopper.setY((int)event.getY());
            }
        } if (event.getAction() == MotionEvent.ACTION_UP) {
            // touch was released
            if (shopper.isTouched()) {
                shopper.setTouched(false);
            }
        }
        return true;
    }*/

    public void render(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        for (Shopper shopper : mShopperList) {
            shopper.draw(canvas);
        }

    }

    /**
     * This is the game update method. It iterates through all the objects
     * and calls their update method if they have one or calls specific
     * engine's update method.
     */
    public void update() {

        // Update the lone shopper
        for (Shopper shopper : mShopperList) {
            shopper.update();

        }
        for (Shop shop:mShopList
             ) {
            shop.update();
        }
/*
        //Update shopper numbers
        if(System.currentTimeMillis() - updateEngineLastRuntimes.get("ShopperMovement") > 10000){
            Log.d(TAG, "10 seconds has passed since last time shooper logic engine last run so starting again..");
            updateEngineLastRuntimes.put("ShopperMovement", System.currentTimeMillis());
        }

        return updateEngineLastRuntimes;*/
    }

}
