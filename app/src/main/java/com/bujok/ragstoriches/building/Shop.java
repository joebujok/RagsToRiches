package com.bujok.ragstoriches.building;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import com.bujok.ragstoriches.R;
import com.bujok.ragstoriches.people.Shopper;
import com.bujok.ragstoriches.utils.Globals;
import com.bujok.ragstoriches.utils.Random;

import java.util.ArrayList;

import static com.bujok.ragstoriches.utils.Random.getRandInteger;

/**
 * Created by Buje on 13/02/2016.
 */
public class Shop {

    private static final String TAG = "Shop" ;
    private final Context context;
    private int capacity;
    private int desirability;
    private ArrayList<Shopper> shopperList;
    private state shopState;
    private int shopID;
    private boolean isWaitingForShopperToArrive ;



    //Constructor
    public Shop(Context context, int shopID, int capacity, int desirability) {
        this.context = context;
        this.shopID = shopID;
        this.capacity = capacity;
        this.desirability = desirability;
        this.shopperList = Globals.getInstance().getShopperList();
        this.shopState = state.OPEN;
        this.isWaitingForShopperToArrive = false;
    }

    public void update(){
        switch (this.shopState) {
            case FULL:
                if(this.getNumberOfShopperInShop() < this.capacity) this.shopState = state.OPEN;
                break;
            case OPEN:
                //do nothing if waiting for a new shopper to spawn
                if(!isWaitingForShopperToArrive){
                    //start to initiate a customer joining
                    isWaitingForShopperToArrive = true;
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new ShopperGeneraterThread(this.shopState, Globals.getInstance()) {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            Log.d(TAG, "5 seconds have passed");
                            //add shopper/customer....
                            globals.getShopperList().add(new Shopper("ShopperName" + SystemClock.currentThreadTimeMillis(), BitmapFactory.decodeResource(context.getResources(), R.drawable.shopper), getRandInteger(0, 500),getRandInteger(0,850)));
                            //check if shop is now full and set state (open/full) accordingly
                            int shoppers = getNumberOfShopperInShop();
                            if(shoppers < capacity){
                                this.shopState = state.OPEN;
                            }
                            else if(shoppers >= capacity){
                                this.shopState = state.FULL;
                                if(shoppers> capacity){
                                    Log.e(TAG, "Error : more shopper in shop than capacity allows : " + shoppers + " shoppers and capacity is " + capacity);
                                }
                            }
                            isWaitingForShopperToArrive = false;



                        }
                    }, 5000);
                    //this.shopState =state.WAITING_FOR_CUSTOMER;
                }

                break;
            case WAITING_FOR_CUSTOMER:
                break;
            case CLOSING:
                //move all customers to the door...
                break;
            case CLOSED:
                //need to wait for something (user) to open it....
                break;
        }


    }

    // state set up
    public enum state{
        FULL, OPEN, WAITING_FOR_CUSTOMER, CLOSING, CLOSED
    }

    //getters and setters

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getDesirability() {
        return desirability;
    }

    public void setDesirability(int desirability) {
        this.desirability = desirability;
    }

    // shopper generator thread, needs a class so parameters can be passed...
    public class ShopperGeneraterThread implements Runnable {

        protected state shopState;
        protected Globals globals;

        public ShopperGeneraterThread(state shopState, Globals g) {
            // store parameter for later user
            this.shopState = shopState;
            this.globals = g;

        }

        public void run() {

        }
    }

    //private methods
    private int getNumberOfShopperInShop(){
        int i = 0;
        for (Shopper s:shopperList
             ) {
            if(s.currentShopID == this.shopID) i++;

        }
        return i ;
    }

}


